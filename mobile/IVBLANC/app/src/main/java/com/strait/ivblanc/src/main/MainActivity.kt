package com.strait.ivblanc.src.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.strait.ivblanc.R
import com.strait.ivblanc.config.BaseActivity
import com.strait.ivblanc.data.model.viewmodel.MainViewModel
import com.strait.ivblanc.databinding.ActivityMainBinding
import com.strait.ivblanc.ui.PhotoListFragment

class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {
    val mainViewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: 2022/01/26 모든 옷 받기 테스트 
        mainViewModel.getAllClothes(0)
        mainViewModel.updateClothesByCategory(20)

        supportFragmentManager.beginTransaction().replace(R.id.frame, PhotoListFragment()).commit()
    }
}