package com.strait.ivblanc.src.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import android.view.View
import androidx.activity.viewModels
import androidx.core.content.res.ResourcesCompat
import com.strait.ivblanc.R
import com.strait.ivblanc.config.BaseActivity
import com.strait.ivblanc.data.model.dto.Clothes
import com.strait.ivblanc.data.model.viewmodel.MainViewModel
import com.strait.ivblanc.databinding.ActivityMainBinding
import com.strait.ivblanc.src.photoSelect.AlbumFragment
import com.strait.ivblanc.src.photoSelect.CameraFragment
import com.strait.ivblanc.ui.PhotoListFragment
import com.strait.ivblanc.util.CategoryCode
import java.lang.Exception

class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {
    val mainViewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // TODO: 2022/01/26 모든 옷 받기 테스트
        mainViewModel.getAllClothesWithCategory(CategoryCode.TOTAL)
        init()
    }

    // TODO: 2022/02/03 nav fragment 추가 및 style generic 추가
    private fun init() {
        setToolbar()

        binding.bottomNavMain.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.nav_clothes -> {
                    supportFragmentManager.beginTransaction().replace(R.id.frameLayout_main_container, PhotoListFragment<Clothes>()).commit()
                    true
                }
                R.id.nav_style -> {
                    supportFragmentManager.beginTransaction().replace(R.id.frameLayout_main_container, PhotoListFragment<Clothes>()).commit()
                    true
                }
                R.id.nav_history -> {
                    true
                }
                R.id.nav_friend -> {
                    true
                }
                else -> false
            }
        }
    }

    // TODO: 2022/02/04 툴바가 있는 액티비티 마다 반복되는 코드
    private fun setToolbar() {
        setSupportActionBar(binding.toolbarMain.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        mainViewModel.toolbarTitle.observe(this) {
            setToolbarTitle(it)
        }
        mainViewModel.leadingIconDrawable.observe(this) {
            setLeadingIcon(it, getListener(it))
        }

        mainViewModel.trailingIconDrawable.observe(this) {
            setTrailingIcon(it, getListener(it))
        }
    }

    // imageView의 background drawable Id에 따라 버튼 리스너 반환
    private fun getListener(resId: Int): View.OnClickListener {
        return when(resId) {
            R.drawable.ic_close -> object: View.OnClickListener {
                override fun onClick(v: View?) {
                    this@MainActivity.onBackPressed()
                }
            }
            else -> View.OnClickListener { }
        }
    }

    private fun setToolbarTitle(title: String) {
        binding.toolbarMain.textViewToolbar.text = title
    }

    private fun setLeadingIcon(resId: Int, clickListener: View.OnClickListener) {
        try {
            binding.toolbarMain.imageViewToolbarLeadingIcon.background = ResourcesCompat.getDrawable(resources, resId, null)
        } catch (e: Exception) {}
        binding.toolbarMain.imageViewToolbarLeadingIcon.setOnClickListener(clickListener)
    }

    private fun setTrailingIcon(resId: Int, clickListener: View.OnClickListener) {
        try {
            binding.toolbarMain.imageViewToolbarTrailingIcon.background = ResourcesCompat.getDrawable(resources, resId, null)
        } catch (e: Exception) {}
        binding.toolbarMain.imageViewToolbarTrailingIcon.setOnClickListener(clickListener)
    }


}