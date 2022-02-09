package com.strait.ivblanc.src.historyDetail

import android.os.Bundle
import com.bumptech.glide.Glide
import com.strait.ivblanc.R
import com.strait.ivblanc.config.BaseActivity
import com.strait.ivblanc.data.model.dto.History
import com.strait.ivblanc.databinding.ActivityHistoryDetailBinding

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
        binding.imageViewHistoryDetailClose.setOnClickListener {
            finish()
        }
        binding.imageViewHistoryDetailEdit.setOnClickListener {
            // TODO: 히스토리 수정 화면으로 이동
        }
    }

    private fun setHistoryInfo() {
        Glide.with(this).load(history.styleUrl).into(binding.imageViewHistoryDetailStyle)

        binding.textViewHistoryDetailDate.text = history.date
        binding.textViewHistoryDetailSubject.text = history.subject
        binding.textViewHistoryDetailLocation.text = "" // TODO: google map api로 위.경도에 따른 지역명 출력
        binding.textViewHistoryDetailTemperature.text = history.temperature_high.toString() + "/" + history.temperature_low.toString()
        binding.textViewHistoryDetailText.text = history.text

        if(history.weather == "맑음"){
            binding.imageViewHistoryDetailWeather.setImageResource(R.drawable.icon_weather_sunny);
        } else if(history.weather == "흐림"){
            binding.imageViewHistoryDetailWeather.setImageResource(R.drawable.icon_weather_cloudy);
        } else if(history.weather == "눈"){
            binding.imageViewHistoryDetailWeather.setImageResource(R.drawable.icon_weather_snowy);
        } else if(history.weather == "비"){
            binding.imageViewHistoryDetailWeather.setImageResource(R.drawable.icon_weather_rainy);
        }
    }

    private fun setRecyclerView(){

    }
}