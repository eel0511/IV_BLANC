package com.strait.ivblanc.src.photoSelect

import android.Manifest
import android.content.ContentUris
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import android.view.MenuInflater
import android.view.View
import android.widget.Toast
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

private const val TAG = "AlbumFragment_해협"
class AlbumFragment : BaseFragment<FragmentAlbumBinding>(FragmentAlbumBinding::bind, R.layout.fragment_album) {
    lateinit var requestPermissionLauncher: ActivityResultLauncher<String>
    private val itemClickListener = object: PhotoRecyclerViewAdapter.ItemClickListener {
        override fun onClick(uri: Uri) {
            Glide.with(requireActivity()).load(uri).into(binding.imageViewAlbumF)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        requestPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                init()
            } else {
                showReasonForPermission()
            }
        }
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkReadStoragePermission()
    }

    private fun checkReadStoragePermission() {
        when {
            // 권한이 있을 때 사진 정보 요청
            ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED -> {
                init()
                return
            }
            //사용자가 명시적으로 권한을 거부했을 때 -> true
            shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE) -> {
                showReasonForPermission()
            }
            //사용자가 권한요청을 처음 보거나, 다시 묻지 않음, 권한요청을 허용한 경우 -> false
            // requestPermissionLauncher는 수 회 이상 권한을 거부했을 경우 launch 되지 않음
            else -> {
                requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            }
        }
    }

    fun init() {
        val uris = setImageUrisFromCursor(getPhotoCursor())
        binding.recyclerViewAlbumF.apply {
            adapter = PhotoRecyclerViewAdapter(uris).apply { itemClickListener = this@AlbumFragment.itemClickListener }
            layoutManager = GridLayoutManager(requireActivity(), 4, RecyclerView.VERTICAL, false)
        }
    }

    // 권한이 필요한 이유를 설명하는 다이얼로그 제공
    // positive button에 권한 설정으로 이동하는 클릭리스너 세팅
    private fun showReasonForPermission() {
        PermissionDialog(requireActivity())
            .setContent("사진을 읽기 위해 필요한 권한입니다.")
            .setPositiveButtonText("권한 설정하기")
            .setOnPositiveClickListener(object : View.OnClickListener {
                override fun onClick(p0: View?) {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:${requireActivity().packageName}")).apply {
                        this.addCategory(Intent.CATEGORY_DEFAULT)
                        this.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    }
                    requireActivity().startActivity(intent)
                }
            }).build().show()
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
        Log.d(TAG, "setImageUrisFromCursor: ${list.toString()}")
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
}