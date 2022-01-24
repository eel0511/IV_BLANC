package com.strait.ivblanc.src.photoSelect

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.strait.ivblanc.R
import com.strait.ivblanc.config.BaseFragment
import com.strait.ivblanc.databinding.FragmentAlbumBinding
import com.strait.ivblanc.ui.PermissionDialog

class AlbumFragment : BaseFragment<FragmentAlbumBinding>(FragmentAlbumBinding::bind, R.layout.fragment_album) {
    lateinit var requestPermissionLauncher: ActivityResultLauncher<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        requestPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                getPhoto()
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
                getPhoto()
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

    fun getPhoto() {
        Toast.makeText(requireActivity(), "사진 요청", Toast.LENGTH_SHORT).show()
    }


}