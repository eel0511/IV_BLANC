package com.strait.ivblanc.src.photoSelect

import android.Manifest
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.strait.ivblanc.config.BaseActivity
import com.strait.ivblanc.databinding.ActivityPhotoSelectBinding
import com.strait.ivblanc.util.PermissionUtil

private const val TAG = "PhotoSelectActivity_해협"
class PhotoSelectActivity : BaseActivity<ActivityPhotoSelectBinding>(ActivityPhotoSelectBinding::inflate) {
    lateinit var viewPager: ViewPager2
    lateinit var permissionUtil: PermissionUtil

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    override fun onStart() {
        super.onStart()
        checkPermissions()
    }

    private fun init() {
        permissionUtil = PermissionUtil(this)
        permissionUtil.permissionListener = object : PermissionUtil.PermissionListener {
            override fun run() {
                initView()
            }
        }
    }

    private fun initView() {
        viewPager = binding.viewpagerPhotoSelectA
        val viewPagerAdapter = ScreenSlidePagerAdapter(this)
        viewPager.adapter = viewPagerAdapter
    }

    private fun checkPermissions() {
        if(!permissionUtil.checkPermissions(listOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA))) {
            permissionUtil.requestPermissions()
        } else {
            initView()
        }
    }

    private inner class ScreenSlidePagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int = 2

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> AlbumFragment()
                else -> CameraFragment()
            }
        }
    }
}