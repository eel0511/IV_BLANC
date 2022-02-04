package com.strait.ivblanc.src.photoSelect

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout.TAB_LABEL_VISIBILITY_LABELED
import com.google.android.material.tabs.TabLayoutMediator
import com.strait.ivblanc.R
import com.strait.ivblanc.config.BaseActivity
import com.strait.ivblanc.data.model.viewmodel.PhotoSelectViewModel
import com.strait.ivblanc.databinding.ActivityPhotoSelectBinding
import com.strait.ivblanc.src.process.ProcessActivity
import com.strait.ivblanc.util.PermissionUtil
import java.lang.Exception

private const val TAG = "PhotoSelectActivity_해협"
class PhotoSelectActivity : BaseActivity<ActivityPhotoSelectBinding>(ActivityPhotoSelectBinding::inflate) {
    companion object {
        val PURE = 0
        val HISTORY = 1
        val CLOTHES = 2
    }
    lateinit var viewPager: ViewPager2
    lateinit var permissionUtil: PermissionUtil
    private val photoSelectViewModel: PhotoSelectViewModel by viewModels()
    private var intend = PURE
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        intend = intent.getIntExtra("intend", PURE)
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
            R.drawable.ic_checked -> object: View.OnClickListener {
                // Activity를 호출 하는 의도에 따라 checked 버튼 동작 분기
                override fun onClick(v: View?) {
                    when(intend) {
                        CLOTHES -> {
                            photoSelectViewModel.selectedImgUri?.let{
                                val intent = Intent(this@PhotoSelectActivity, ProcessActivity::class.java).apply {
                                    putExtra("uri", photoSelectViewModel.selectedImgUri)
                                }
                                startActivity(intent)
                            }

                        }
                        HISTORY -> {}
                    }
                }
            }
            else -> View.OnClickListener { }
        }
    }

    private fun setToolbarTitle(title: String) {
        binding.toolbarPhotoSelect.textViewToolbar.text = title
    }

    private fun setLeadingIcon(resId: Int, clickListener: View.OnClickListener) {
        try {
            binding.toolbarPhotoSelect.imageViewToolbarLeadingIcon.background = ResourcesCompat.getDrawable(resources, resId, null)
        } catch (e: Exception) {
            binding.toolbarPhotoSelect.imageViewToolbarLeadingIcon.background = null
        }
        binding.toolbarPhotoSelect.imageViewToolbarLeadingIcon.setOnClickListener(clickListener)
    }

    private fun setTrailingIcon(resId: Int, clickListener: View.OnClickListener) {
        try {
            binding.toolbarPhotoSelect.imageViewToolbarTrailingIcon.background = ResourcesCompat.getDrawable(resources, resId, null)
        } catch (e: Exception) {
            binding.toolbarPhotoSelect.imageViewToolbarTrailingIcon.background = null
        }
        binding.toolbarPhotoSelect.imageViewToolbarTrailingIcon.setOnClickListener(clickListener)
    }

   // tablayout과 뷰페이저 세팅 및 연결
    private fun setViewPager() {
        viewPager = binding.viewpagerPhotoSelectA
        val viewPagerAdapter = ScreenSlidePagerAdapter(this)
        viewPager.adapter = viewPagerAdapter
        val tabLayout = binding.tabLayoutPhotoSelectA
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when(position) {
                0 -> tab.text = resources.getString(R.string.photo)
                else -> tab.text = resources.getString(R.string.camera)
            }
        }.attach()
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