package com.strait.ivblanc.src.historyDetail

import android.content.res.ColorStateList
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.strait.ivblanc.R
import com.strait.ivblanc.config.BaseActivity
import com.strait.ivblanc.data.model.dto.Clothes
import com.strait.ivblanc.data.model.dto.History
import com.strait.ivblanc.databinding.ActivityClothesDetailBinding
import com.strait.ivblanc.databinding.ActivityHistoryDetailBinding
import com.strait.ivblanc.util.CategoryCode
import com.strait.ivblanc.util.MaterialCode

class HistoryDetailActivity : BaseActivity<ActivityHistoryDetailBinding>(
    ActivityHistoryDetailBinding::inflate) {
    lateinit var history: History
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        intent.getParcelableExtra<History>("history")?.let {
            history = it
        } ?: finish()
        setHistoryInfo()
        setClickListeners()
    }

    private fun setClickListeners() {

    }

    private fun setHistoryInfo() {
        Glide.with(this).load(history.styleUrl).into(binding.imageViewHistoryDetailStyle)

    }
}