package com.strait.ivblanc.src.PhotoSelect

import android.Manifest
import android.app.Dialog
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.strait.ivblanc.R
import com.strait.ivblanc.config.BaseFragment
import com.strait.ivblanc.databinding.FragmentAlbumBinding
import com.strait.ivblanc.ui.PermissionDialog

class AlbumFragment : BaseFragment<FragmentAlbumBinding>(FragmentAlbumBinding::bind, R.layout.fragment_album) {
    lateinit var requestPermissionLauncher: ActivityResultLauncher<String>

    override fun onResume() {
        super.onResume()

        checkReadStoragePermission()
    }

    private fun checkReadStoragePermission() {
        when {
            // 권한이 있을 때 사진 정보 요청
            ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED -> {
                getPhoto()
            }
            //사용자가 명시적으로 권한을 거부했을 때 -> true
            shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE) -> {
                showReasonForPermission()
            }
            //사용자가 권한요청을 처음 보거나, 다시 묻지 않음, 권한요청을 허용한 경우 -> false
            else -> {
                ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),1)
            }
        }
    }

    fun showReasonForPermission() {

        val dial = Dialog(requireActivity())

        val dialog = PermissionDialog(requireActivity())
            .setContent("사진을 읽기 위해 필요한 권한입니다.")
            .setPositiveButtonText("권한 허가하기")
            .setOnPositiveClickListener(object : View.OnClickListener {
                override fun onClick(p0: View?) {
                    ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),1)
                }
            }).build().show()
        Toast.makeText(requireActivity(), "권한 필요 다이얼로그", Toast.LENGTH_SHORT).show()
    }

    fun getPhoto() {
        Toast.makeText(requireActivity(), "사진 요청", Toast.LENGTH_SHORT).show()
    }


}