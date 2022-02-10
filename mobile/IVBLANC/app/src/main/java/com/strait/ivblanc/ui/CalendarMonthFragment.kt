package com.strait.ivblanc.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.strait.ivblanc.R
import com.strait.ivblanc.adapter.CalendarAdapter
import com.strait.ivblanc.config.BaseFragment
import com.strait.ivblanc.data.model.dto.DateWithHistory
import com.strait.ivblanc.data.model.dto.History
import com.strait.ivblanc.data.model.viewmodel.HistoryViewModel
import com.strait.ivblanc.databinding.FragmentCalendarMonthBinding
import com.strait.ivblanc.src.history.HistoryDetailActivity
import org.joda.time.DateTime

private const val TAG = "History_debuk"
class CalendarMonthFragment(val date: DateTime) : BaseFragment<FragmentCalendarMonthBinding>(FragmentCalendarMonthBinding::bind, R.layout.fragment_calendar_month) {
    // viewpager
    private var isInit = false
    private lateinit var firstDate: DateTime
    private lateinit var lastDate: DateTime
    private lateinit var calendarAdapter: CalendarAdapter
    val historyViewModel: HistoryViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(!isInit) {
            init()
        }
    }

    override fun onResume() {
        super.onResume()
        if(!isInit) {
            init()
        }
    }

    private fun init() {
        isInit = true
        historyViewModel.getAllHistorySelectedMonth(date.year, date.monthOfYear)
        setFirstDate()
        setLastDate()
        calendarAdapter = CalendarAdapter()
        binding.recyclerViewCalendarMonthF.apply {
            layoutManager = GridLayoutManager(requireActivity(), 7, RecyclerView.VERTICAL, false)
            adapter = calendarAdapter
            addItemDecoration(DividerItemDecoration(requireActivity(), RecyclerView.VERTICAL))
        }

        historyViewModel.historyListLiveData.observe(requireActivity()){
            calendarAdapter.data = getDatesOfMonthWithHistory(it)
            calendarAdapter.notifyDataSetChanged()
        }

        calendarAdapter.itemClickListener = object : CalendarAdapter.ItemClickListener {
            override fun onClick(position: Int) {
                val item = calendarAdapter.data[position].history
                val intent = Intent(requireActivity(), HistoryDetailActivity::class.java).putExtra("history", item)
                startActivity(intent)
            }
        }
    }

    private fun setFirstDate() {
        firstDate = date.dayOfMonth().withMinimumValue()
    }

    private fun setLastDate() {
        lastDate = date.dayOfMonth().withMaximumValue()
    }

    private fun getDatesOfMonthWithHistory(historyList: List<History>): List<DateWithHistory> {
        val result = mutableListOf<DateWithHistory>()
        val day = lastDate.dayOfMonth().get()
        if(firstDate.dayOfWeek != 7) {
            for(i in 1..firstDate.dayOfWeek) {
                result.add(DateWithHistory(null))
            }
        }

        for(i in 1..day) {
            result.add(DateWithHistory(DateTime(firstDate.year, firstDate.monthOfYear, firstDate.dayOfMonth.plus(i).minus(1), 0, 0)))
        }

        for(history in historyList){
            val date = Integer.parseInt(history.date.split("-")[2])
            result[date+1].history = history
        }

        return result
    }
}