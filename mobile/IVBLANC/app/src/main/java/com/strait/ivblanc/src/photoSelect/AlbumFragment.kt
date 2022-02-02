package com.strait.ivblanc.src.photoSelect

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.strait.ivblanc.R
import com.strait.ivblanc.adapter.PhotoRecyclerViewAdapter
import com.strait.ivblanc.config.BaseFragment
import com.strait.ivblanc.databinding.FragmentAlbumBinding
import com.strait.ivblanc.ui.PermissionDialog
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Environment
import android.text.format.DateFormat
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.util.*


private const val TAG = "AlbumFragment_해협"
class AlbumFragment : BaseFragment<FragmentAlbumBinding>(FragmentAlbumBinding::bind, R.layout.fragment_album) {
    private var scaleFactor = 1.0F
    lateinit var scaleGestureDetector: ScaleGestureDetector
    private val itemClickListener = object: PhotoRecyclerViewAdapter.ItemClickListener {
        override fun onClick(uri: Uri) {
            Glide.with(requireActivity()).load(uri).into(binding.imageViewAlbumF)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    fun init() {
        // 사진 pinch 제스처 감지 후 이미지 사이즈 스케일 적용
        scaleGestureDetector = ScaleGestureDetector(requireActivity(), object: ScaleGestureDetector.SimpleOnScaleGestureListener() {
            override fun onScale(detector: ScaleGestureDetector?): Boolean {
                scaleFactor *= detector!!.scaleFactor
                binding.imageViewAlbumF.scaleY = scaleFactor
                binding.imageViewAlbumF.scaleX = scaleFactor
                return true
            }
        })

        val uris = setImageUrisFromCursor(getPhotoCursor())
        binding.recyclerViewAlbumF.apply {
            adapter = PhotoRecyclerViewAdapter(uris).apply { itemClickListener = this@AlbumFragment.itemClickListener }
            layoutManager = GridLayoutManager(requireActivity(), 4, RecyclerView.VERTICAL, false)
        }
        //선택 이미지 pinch zoom in, out 동작 중, viewpager 작동 멈추기
        binding.constraintLayoutAlbumFContainer.setOnTouchListener(object: View.OnTouchListener {
            override fun onTouch(p0: View?, p1: MotionEvent?): Boolean {
                (requireActivity() as PhotoSelectActivity).viewPager.isUserInputEnabled = false
                scaleGestureDetector.onTouchEvent(p1)
                p1?.let {
                    if(it.action == MotionEvent.ACTION_UP || it.action == MotionEvent.ACTION_POINTER_UP) {
                        (requireActivity() as PhotoSelectActivity).viewPager.isUserInputEnabled = true
                    }
                }
                return true
            }
        })
    }

    // 이미지 커서에서 image 경로 리스트로 받기
    fun setImageUrisFromCursor(cursor: Cursor):List<Uri> {
        val list = mutableListOf<Uri>()
        cursor.use {
            val idColumn = it.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
            while(cursor.moveToNext()) {
                val id = cursor.getLong(idColumn)
                list.add(Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id.toString()))
            }
        }
        return list
    }

    // content resolver로 이미지 커서 가져오기
    fun getPhotoCursor(): Cursor {
        val resolver = requireActivity().contentResolver
        var queryUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI

        val img = arrayOf(
            MediaStore.Images.ImageColumns._ID,
            MediaStore.Images.ImageColumns.TITLE,
            MediaStore.Images.ImageColumns.DATE_TAKEN
        )

        val orderBy = MediaStore.Images.ImageColumns.DATE_TAKEN + " DESC"

        queryUri = queryUri.buildUpon().build()
        return resolver.query(queryUri, img, null, null, orderBy)!!
    }

    // 확인 버튼 누르면 photoBox만큼 screen capture
    fun screenshot(): File? {
        val date = Date()
        // 이미지 파일 이름을 시간으로 초기화
        val format: CharSequence = DateFormat.format("yyyy-MM-dd_hh:mm:ss", date)
        try {
            // 저장소 directory 경로 초기화
            val dirpath: String = Environment.getExternalStorageDirectory().toString()
            val file = File(dirpath)
            if (!file.exists()) {
                val mkdir: Boolean = file.mkdir()
            }

            val path = "$dirpath/temp-$format.jpeg" // 파일 이름 설정
            val view = binding.constraintLayoutAlbumFPhotoBox //포토박스 만큼만 비트맵 생성
            val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
            var canvas = Canvas(bitmap)
            view.draw(canvas)

            val imageurl = File(path)
            val outputStream = FileOutputStream(imageurl)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, outputStream) // Jpeg로 압축
            outputStream.flush()
            outputStream.close()
            return imageurl // 이미지 외부 저장소에 저장
        } catch (io: FileNotFoundException) {
            io.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return null
    }
}