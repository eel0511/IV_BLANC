package com.strait.ivblanc.src.photoSelect

import android.Manifest
import android.content.ContentValues
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
import android.os.Build
import android.os.Environment
import android.text.format.DateFormat
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.strait.ivblanc.data.model.viewmodel.PhotoSelectViewModel
import java.io.*
import java.text.SimpleDateFormat
import java.util.*


private const val TAG = "AlbumFragment_debuk"
class AlbumFragment : BaseFragment<FragmentAlbumBinding>(FragmentAlbumBinding::bind, R.layout.fragment_album) {
    private val photoSelectViewModel: PhotoSelectViewModel by activityViewModels()
    private var scaleFactor = 1.0F
    lateinit var scaleGestureDetector: ScaleGestureDetector
    lateinit var photoRecyclerViewAdapter: PhotoRecyclerViewAdapter
    private val itemClickListener = object: PhotoRecyclerViewAdapter.ItemClickListener {
        override fun onClick(uri: Uri) {
            Glide.with(requireActivity()).load(uri).into(binding.imageViewAlbumF)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume: ")
        reloadImages()
        photoSelectViewModel.setToolbarTitle("사진 선택")
        photoSelectViewModel.setLeadingIcon(R.drawable.ic_close)
    }

    fun reloadImages() {
        photoRecyclerViewAdapter.uris = setImageUrisFromCursor(getPhotoCursor())
        photoRecyclerViewAdapter.notifyDataSetChanged()
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

        photoRecyclerViewAdapter = PhotoRecyclerViewAdapter().apply { itemClickListener = this@AlbumFragment.itemClickListener }
        binding.recyclerViewAlbumF.apply {
            adapter = photoRecyclerViewAdapter
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
                val uri = Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id.toString())
                Log.d(TAG, "setImageUrisFromCursor: $uri")
                Log.d(TAG, "setImageUrisFromCursor: ${cursor.getString(3)}")
                list.add(uri)
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
            MediaStore.Images.ImageColumns.DISPLAY_NAME,
            MediaStore.Images.ImageColumns.DATE_TAKEN,
            MediaStore.Images.ImageColumns.DATA
        )

        val orderBy = MediaStore.Images.ImageColumns.DATE_TAKEN + " DESC"

        queryUri = queryUri.buildUpon().build()
        return resolver.query(queryUri, img, null, null, orderBy)!!
    }

    // 확인 버튼 누르면 photoBox만큼 screen capture
    fun screenshot() {
        val view = binding.constraintLayoutAlbumFPhotoBox //포토박스 만큼만 비트맵 생성
        val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        var canvas = Canvas(bitmap)
        view.draw(canvas)

        val name = "ivblanc${SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.KOREA).format(System.currentTimeMillis())}.jpg"
        var fos : OutputStream?

            requireActivity().contentResolver.also { resolver ->
                val contentValues = ContentValues().apply {
                    put(MediaStore.MediaColumns.DISPLAY_NAME, name)
                    put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
                    put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis())
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
                    }
                }

                val imageUri: Uri? = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

                fos = imageUri?.let {
                    resolver.openOutputStream(it)
                }
            }
            fos?.use {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, it)
                toast("이미지 저장 완료", Toast.LENGTH_SHORT)
            }
    }
}