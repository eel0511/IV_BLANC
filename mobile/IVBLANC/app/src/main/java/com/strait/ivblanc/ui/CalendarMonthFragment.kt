package com.strait.ivblanc.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.strait.ivblanc.R
import com.strait.ivblanc.config.BaseFragment
import com.strait.ivblanc.databinding.FragmentCalendarMonthBinding
import java.time.LocalDate

class CalendarMonthFragment(val date: LocalDate) : BaseFragment<FragmentCalendarMonthBinding>(FragmentCalendarMonthBinding::bind, R.layout.fragment_calendar_month) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
    }
}