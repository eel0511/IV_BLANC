package com.strait.ivblanc.src.photoSelect

import android.Manifest
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.strait.ivblanc.R
import com.strait.ivblanc.config.BaseActivity
import com.strait.ivblanc.data.model.viewmodel.PhotoSelectViewModel
import com.strait.ivblanc.databinding.ActivityPhotoSelectBinding
import com.strait.ivblanc.util.PermissionUtil

private const val TAG = "PhotoSelectActivity_해협"
class PhotoSelectActivity : BaseActivity<ActivityPhotoSelectBinding>(ActivityPhotoSelectBinding::inflate) {
    lateinit var viewPager: ViewPager2
    lateinit var permissionUtil: PermissionUtil
    private val photoSelectViewModel: PhotoSelectViewModel by viewModels()
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
        setViewPager()
        setToolbar()
    }

    private fun setToolbar() {
        setSupportActionBar(binding.toolbarPhotoSelect.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        photoSelectViewModel.toolbarTitle.observe(this) {
            setToolbarTitle(it)
        }
        photoSelectViewModel.leadingIconDrawable.observe(this) {
            setLeadingIcon(it, getListener(it))
        }

        photoSelectViewModel.trailingIconDrawable.observe(this) {
            setTrailingIcon(it, getListener(it))
        }
    }

    // imageView의 background drawable Id에 따라 버튼 리스너 반환
    private fun getListener(resId: Int): View.OnClickListener {
        return when(resId) {
            R.drawable.ic_close -> object: View.OnClickListener {
                override fun onClick(v: View?) {
                    this@PhotoSelectActivity.onBackPressed()
                }
            }
            else -> View.OnClickListener { }
        }
    }

    private fun setToolbarTitle(title: String) {
        binding.toolbarPhotoSelect.textViewToolbar.text = title
    }

    private fun setLeadingIcon(resId: Int, clickListener: View.OnClickListener) {
        binding.toolbarPhotoSelect.imageViewToolbarLeadingIcon.background = ResourcesCompat.getDrawable(resources, resId, null)
        binding.toolbarPhotoSelect.imageViewToolbarLeadingIcon.setOnClickListener(clickListener)
    }

    private fun setTrailingIcon(resId: Int, clickListener: View.OnClickListener) {
        binding.toolbarPhotoSelect.imageViewToolbarTrailingIcon.background = ResourcesCompat.getDrawable(resources, resId, null)
        binding.toolbarPhotoSelect.imageViewToolbarTrailingIcon.setOnClickListener(clickListener)
    }

    private fun setViewPager() {
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