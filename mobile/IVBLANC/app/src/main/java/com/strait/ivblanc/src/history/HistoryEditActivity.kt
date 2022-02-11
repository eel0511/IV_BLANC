package com.strait.ivblanc.src.history

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.strait.ivblanc.adapter.HistoryDetailRecyclerViewAdapter
import com.strait.ivblanc.config.BaseActivity
import com.strait.ivblanc.data.model.dto.History
import com.strait.ivblanc.databinding.ActivityHistoryEditBinding
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener

import com.strait.ivblanc.R


class HistoryEditActivity : BaseActivity<ActivityHistoryEditBinding>(
    ActivityHistoryEditBinding::inflate) {

    lateinit var history: History
    lateinit var location: String
    lateinit var historyDetailRecyclerViewAdapter: HistoryDetailRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        intent.getParcelableExtra<History>("history")?.let {
            history = it
        } ?: finish()
        location = intent.getStringExtra("location").toString()
        setClickListeners()
        setHistoryEditInfo()
        setRecyclerView()
    }

    private fun setClickListeners() {
        binding.imageViewHistoryEditClose.setOnClickListener {
            // TODO: 정말 나갈건지 다이얼로그 띄워서 물어보기
            finish()
        }
        binding.textViewHistoryEditSave.setOnClickListener {
            // TODO: 수정된 히스토리 저장
        }
        binding.imageViewHistoryEditAddPhoto.setOnClickListener {
            // TODO: 앨범 사진 선택 화면으로 이동
        }

        binding.textViewHistoryEditSelectDate.setOnClickListener {
            val date = history.date.split("-")
            val dialog = DatePickerDialog(this, R.style.MySpinnerDatePickerStyle, datePickerListener, date[0].toInt(), date[1].toInt()-1, date[2].toInt())
            dialog.show()
        }
    }

    private fun setHistoryEditInfo() {
        if(history.styleUrl != null) {
            Glide.with(this).load(history.styleUrl).into(binding.imageViewHistoryEditStyle)
        }

        binding.textViewHistoryEditSelectDate.text = history.date
        binding.editTextHistoryEditSubject.setText(history.subject)
        binding.textViewHistoryEditSelectLocation.text = location
        binding.textViewHistoryEditTemperature.text = history.temperature_high.toString() + "°C/" + history.temperature_low.toString() + "°C"
        binding.editTextHistoryEditText.setText(history.text)

        if(history.photos.isEmpty())
            binding.recyclerViewHistoryEditPhoto.visibility = View.GONE
    }

    private fun setRecyclerView(){
        historyDetailRecyclerViewAdapter = HistoryDetailRecyclerViewAdapter()
        historyDetailRecyclerViewAdapter.apply {
            data = history.photos
        }
        binding.recyclerViewHistoryEditPhoto.apply {
            adapter = historyDetailRecyclerViewAdapter
            layoutManager = LinearLayoutManager(this@HistoryEditActivity, RecyclerView.HORIZONTAL, false)
        }
    }

    private val datePickerListener =
        OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            val newDate = "$year-${monthOfYear+1}-$dayOfMonth"
            binding.textViewHistoryEditSelectDate.text = newDate
        }
}