package com.strait.ivblanc.src.photoSelect

import android.Manifest
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import com.strait.ivblanc.R
import com.strait.ivblanc.config.BaseFragment
import com.strait.ivblanc.data.model.viewmodel.PhotoSelectViewModel
import com.strait.ivblanc.databinding.FragmentCameraBinding
import com.strait.ivblanc.ui.PermissionDialog
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

private const val TAG = "CameraFragment_해협"
class CameraFragment : BaseFragment<FragmentCameraBinding>(FragmentCameraBinding::bind, R.layout.fragment_camera) {
    private val photoSelectViewModel: PhotoSelectViewModel by activityViewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        binding.buttonCameraFShot.setOnClickListener {
            takePicture()
        }
    }

    override fun onResume() {
        super.onResume()
        startCamera()
        photoSelectViewModel.setToolbarTitle("카메라")
        photoSelectViewModel.setLeadingIcon(R.drawable.ic_close)
        photoSelectViewModel.setTrailingIcon(-1)
    }

    //카메라 관련 함수 시작
    private var imageCapture: ImageCapture? = null
    private fun startCamera() {

        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireActivity())

        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            //Preview
            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(binding.previewViewCameraF.surfaceProvider)
                }

            imageCapture = ImageCapture.Builder()
                .build()

            //후면 카메라 기본으로 세팅
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                // 카메라와 라이프사이클 바인딩 전 모든 바인딩 해제
                cameraProvider.unbindAll()

                // 카메라와 라이프사이클 바인딩
                cameraProvider.bindToLifecycle(requireActivity(), cameraSelector, preview, imageCapture)
            } catch (e: Exception) {
                Log.d(TAG, "Use case binding failed: ", e)
            }
        }, ContextCompat.getMainExecutor(requireActivity()))
    }

    private fun takePicture() {
        val imageCapture = this.imageCapture?: return

        //MediaStore에 저장할 파일 이름 생성
        val name = SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.KOREA).format(System.currentTimeMillis())
        val contentValues = ContentValues().apply {
            put(MediaStore.Images.ImageColumns.DISPLAY_NAME, "ivblanc$name.jpg")
            put(MediaStore.Images.ImageColumns.TITLE, "ivblanc$name.jpg")
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
            put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis())
            if(Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
                put(MediaStore.Images.Media.RELATIVE_PATH, "${Environment.DIRECTORY_PICTURES}/ivblanc")
            }
        }

        // 파일과 메타데이터를 포함하는 아웃풋 옵션 설정
        val outputOptions = ImageCapture.OutputFileOptions
            .Builder(requireActivity().contentResolver, MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
            .build()

        //캡처 리스너 세팅, 이벤트 발생하면 위에서 지정한 경로로 이미지 저장
        imageCapture.takePicture(
            outputOptions, ContextCompat.getMainExecutor(requireActivity()), object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    toast("이미지가 저장되었습니다.", Toast.LENGTH_SHORT)
                }

                override fun onError(exception: ImageCaptureException) {
                    Log.d(TAG, "onCaptureSavedError: ${exception.message}")
                }
            }
        )
    }
}