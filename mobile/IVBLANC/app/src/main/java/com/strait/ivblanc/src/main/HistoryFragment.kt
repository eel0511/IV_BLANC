package com.strait.ivblanc.src.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableLayout
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.activityViewModels
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.strait.ivblanc.R
import com.strait.ivblanc.config.BaseFragment
import com.strait.ivblanc.data.model.viewmodel.MainViewModel
import com.strait.ivblanc.databinding.FragmentHistoryBinding
import com.strait.ivblanc.src.photoSelect.AlbumFragment
import com.strait.ivblanc.src.photoSelect.CameraFragment

class HistoryFragment : BaseFragment<FragmentHistoryBinding>(FragmentHistoryBinding::bind, R.layout.fragment_history) {
    private val mainViewModel: MainViewModel by activityViewModels()
    private lateinit var viewPager: ViewPager2

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        viewPager = binding.viewpagerHistoryF
        viewPager.adapter = ViewPagerAdapter(requireActivity())
        // 스와이핑 입력 무시
        viewPager.isUserInputEnabled = false
        TabLayoutMediator(binding.tabLayoutHistoryF, viewPager) { tab, position ->
            when(position) {
                0 -> tab.text = resources.getString(R.string.calendar)
                else -> tab.text = resources.getString(R.string.map)
            }
        }.attach()
    }

    override fun onResume() {
        super.onResume()
        mainViewModel.setToolbarTitle(resources.getString(R.string.history))
        mainViewModel.setLeadingIcon(R.drawable.ic_add)
    }

    private inner class ViewPagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int = 2

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> {
                    CalendarFragment()
                }
                else -> {
                    MapFragment()
                }
            }
        }
    }

}