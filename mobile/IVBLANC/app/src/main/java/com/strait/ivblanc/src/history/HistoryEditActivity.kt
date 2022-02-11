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
import android.view.Window

import android.app.Dialog
import android.widget.Button
import android.widget.TextView
import com.strait.ivblanc.R


class HistoryEditActivity : BaseActivity<ActivityHistoryEditBinding>(
    ActivityHistoryEditBinding::inflate) {

    lateinit var history: History
    lateinit var location: String
    lateinit var historyDetailRecyclerViewAdapter: HistoryDetailRecyclerViewAdapter
    lateinit var locationDialog: Dialog

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
            val dialog = DatePickerDialog(this, com.strait.ivblanc.R.style.MySpinnerDatePickerStyle, datePickerListener, date[0].toInt(), date[1].toInt()-1, date[2].toInt())
            dialog.show()
        }

        binding.textViewHistoryEditSelectLocation.setOnClickListener {
            locationDialog = Dialog(this@HistoryEditActivity) // Dialog 초기화
            locationDialog.requestWindowFeature(Window.FEATURE_NO_TITLE) // 타이틀 제거
            locationDialog.setContentView(R.layout.dialog_select_location) // xml 레이아웃 파일과 연결

            showLocationDialog()

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

    private fun showLocationDialog(){
        locationDialog.show() // 다이얼로그 띄우기

        val noBtn: TextView = locationDialog.findViewById(R.id.textView_btn_cancel)
        noBtn.setOnClickListener(View.OnClickListener {
            locationDialog.dismiss()
        })

        val saveBtn: TextView = locationDialog.findViewById(R.id.textView_btn_save)
        saveBtn.setOnClickListener(View.OnClickListener {
            // TODO: 선택된 주소 정보 세팅
           finish() // 앱 종료
        })

    }
}