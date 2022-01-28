package com.strait.ivblanc.src.photoSelect

import android.Manifest
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
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
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import com.strait.ivblanc.R
import com.strait.ivblanc.config.BaseFragment
import com.strait.ivblanc.databinding.FragmentCameraBinding
import com.strait.ivblanc.ui.PermissionDialog
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

private const val TAG = "CameraFragment_해협"
class CameraFragment : BaseFragment<FragmentCameraBinding>(FragmentCameraBinding::bind, R.layout.fragment_camera) {
    lateinit var requestPermissionLauncher: ActivityResultLauncher<String>
    private var isGranted = false

    override fun onCreate(savedInstanceState: Bundle?) {
        requestPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                this.isGranted = true
            } else {
                showReasonForPermission()
            }
        }
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkReadStoragePermission()
        binding.buttonCameraFShot.setOnClickListener {
            takePicture()
        }
    }

    //권한 체크가 되어있지 않다면 권한 체크 실행
    override fun onResume() {
        if(isGranted) startCamera()
        else checkReadStoragePermission()
        super.onResume()
    }

    // 권한관련 함수 시작
    private fun checkReadStoragePermission() {
        when {
            // 권한이 있을 때 카메라 실행
            ContextCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED -> {
                this.isGranted = true
                return
            }
            //사용자가 명시적으로 권한을 거부했을 때 -> true
            shouldShowRequestPermissionRationale(Manifest.permission.CAMERA) -> {
                showReasonForPermission()
            }
            //사용자가 권한요청을 처음 보거나, 다시 묻지 않음, 권한요청을 허용한 경우 -> false
            // requestPermissionLauncher는 수 회 이상 권한을 거부했을 경우 launch 되지 않음
            else -> {
                requestPermissionLauncher.launch(Manifest.permission.CAMERA)
            }
        }
    }

    // 권한이 필요한 이유를 설명하는 다이얼로그 제공
    // positive button에 권한 설정으로 이동하는 클릭리스너 세팅
    private fun showReasonForPermission() {
        PermissionDialog(requireActivity())
            .setContent("사진을 찍기 위해 필요한 권한입니다.")
            .setPositiveButtonText("권한 설정하기")
            .setOnPositiveClickListener(object : View.OnClickListener {
                override fun onClick(p0: View?) {
                    val intent = Intent(
                        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                        Uri.parse("package:${requireActivity().packageName}")
                    ).apply {
                        this.addCategory(Intent.CATEGORY_DEFAULT)
                        this.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    }
                    requireActivity().startActivity(intent)
                }
            }).build().show()
    }
    //권한 관련 함수 끝

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
            put(MediaStore.MediaColumns.DISPLAY_NAME, name)
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
            if(Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
                put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis())
                put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/IVBLANC")
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