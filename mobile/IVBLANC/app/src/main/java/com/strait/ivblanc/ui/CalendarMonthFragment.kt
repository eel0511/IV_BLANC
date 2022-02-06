package com.strait.ivblanc.ui

import android.icu.util.Calendar
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import com.strait.ivblanc.R
import com.strait.ivblanc.adapter.CalendarAdapter
import com.strait.ivblanc.config.BaseFragment
import com.strait.ivblanc.data.model.dto.DateWithHistory
import com.strait.ivblanc.databinding.FragmentCalendarMonthBinding
import org.joda.time.DateTime
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.YearMonth
import java.util.*

class CalendarMonthFragment(val date: DateTime) : BaseFragment<FragmentCalendarMonthBinding>(FragmentCalendarMonthBinding::bind, R.layout.fragment_calendar_month) {
    private var isInit = false
    private val DAY_FORMAT = "dd"
    private lateinit var firstDate: DateTime
    private lateinit var lastDate: DateTime
    private lateinit var calendarAdapter: CalendarAdapter
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
        setFirstDate()
        setLastDate()
        calendarAdapter = CalendarAdapter()
        calendarAdapter.data = getDatesOfMonthWithHistory()
    }

    private fun setFirstDate() {
        firstDate = date.dayOfMonth().withMinimumValue()
    }

    private fun setLastDate() {
        lastDate = date.dayOfMonth().withMaximumValue()
    }

    private fun getDatesOfMonthWithHistory(): List<DateWithHistory> {
        val result = mutableListOf<DateWithHistory>()
        val day = SimpleDateFormat(DAY_FORMAT, Locale.KOREA).format(lastDate).toInt()
        for(i in 1..day) {
            result.add(DateWithHistory(DateTime(firstDate.year, firstDate.monthOfYear, firstDate.dayOfMonth.plus(i).minus(1), 0, 0)))
        }
        return result
    }
}