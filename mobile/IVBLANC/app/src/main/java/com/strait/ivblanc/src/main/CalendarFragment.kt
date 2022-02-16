package com.strait.ivblanc.src.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.strait.ivblanc.R
import com.strait.ivblanc.config.BaseFragment
import com.strait.ivblanc.data.model.dto.History
import com.strait.ivblanc.databinding.FragmentCalendarBinding
import com.strait.ivblanc.src.history.StyleSelectActivity
import com.strait.ivblanc.ui.CalendarMonthFragment
import org.joda.time.DateTime

private const val TAG = "CalendarFragment_debuk"
class CalendarFragment : BaseFragment<FragmentCalendarBinding>(FragmentCalendarBinding::bind, R.layout.fragment_calendar) {
    lateinit var viewPager: ViewPager2
    private val now = DateTime.now()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        setViewPager()
        setUiAfterSetViewPager()
        setClickListener()
    }

    private fun setClickListener() {
        binding.floatingButtonCalendarF.setOnClickListener {
            startActivity(
                Intent(
                    requireActivity(),
                    StyleSelectActivity::class.java
                ).apply {
                    putExtra("history", History("", "", 0.0,0.0, 0, emptyList(), "", "", 0, 0, "", "", 0, "맑음"))
                }
            )
        }
    }

    private fun setViewPager() {
        viewPager = binding.viewpagerCalendarF
        viewPager.adapter = ViewPagerAdapter(requireActivity())
        viewPager.setCurrentItem(Int.MAX_VALUE, true)
        viewPager.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                val date = now.minusMonths(Int.MAX_VALUE - position - 1)
                binding.textViewCalendarFDate.text = "${date.year().get()}.${date.monthOfYear}"
            }
        })
    }

    private fun setUiAfterSetViewPager() {
        binding.textViewCalendarFDate.text = "${now.year().get()}.${now.monthOfYear}"
        binding.imageViewCalendarFBack.setOnClickListener {
            if(viewPager.currentItem > 0) {
                viewPager.setCurrentItem(viewPager.currentItem - 1)
            }
        }
        binding.imageViewCalendarFNext.setOnClickListener {
            if(viewPager.currentItem < Int.MAX_VALUE - 1) {
                viewPager.setCurrentItem(viewPager.currentItem + 1)
            }
        }
    }

    // TODO: 2022/02/07 아이템 개수를 Int.Max_value로 하는 것은 꺼림칙하다. 로직 개선 필요 
    inner class ViewPagerAdapter(fa: FragmentActivity): FragmentStateAdapter(fa) {
        override fun getItemCount(): Int = Int.MAX_VALUE

        override fun createFragment(position: Int): Fragment {
            return CalendarMonthFragment(now.minusMonths(Int.MAX_VALUE - position - 1))
        }
    }
}