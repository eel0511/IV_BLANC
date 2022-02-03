package com.strait.ivblanc.src.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.strait.ivblanc.R
import com.strait.ivblanc.config.BaseActivity
import com.strait.ivblanc.data.model.dto.Clothes
import com.strait.ivblanc.data.model.viewmodel.MainViewModel
import com.strait.ivblanc.databinding.ActivityMainBinding
import com.strait.ivblanc.src.photoSelect.AlbumFragment
import com.strait.ivblanc.src.photoSelect.CameraFragment
import com.strait.ivblanc.ui.PhotoListFragment
import com.strait.ivblanc.util.CategoryCode

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
        binding.bottomNavMain.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.nav_clothes -> {
                    supportFragmentManager.beginTransaction().replace(R.id.nav_host_fragment, PhotoListFragment<Clothes>()).commit()
                    true
                }
                R.id.nav_style -> {
                    supportFragmentManager.beginTransaction().replace(R.id.nav_host_fragment, PhotoListFragment<Clothes>()).commit()
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


}