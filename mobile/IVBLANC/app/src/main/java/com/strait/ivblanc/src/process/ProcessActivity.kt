package com.strait.ivblanc.src.process

import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.strait.ivblanc.config.BaseActivity
import com.strait.ivblanc.data.model.viewmodel.ProcessViewModel
import com.strait.ivblanc.databinding.ActivityProcessBinding

class ProcessActivity : BaseActivity<ActivityProcessBinding>(ActivityProcessBinding::inflate) {
    lateinit var viewPager: ViewPager2
    lateinit var viewPagerAdapter: FragmentStateAdapter
    private val FRAGMENT_NUMBER = 4
    private val processViewModel: ProcessViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    private fun init() {
        viewPager = binding.viewpagerProcess
        viewPagerAdapter = SimpleFragmentStateAdapter(this)
        viewPager.adapter = viewPagerAdapter
    }

    inner class SimpleFragmentStateAdapter(fa: FragmentActivity): FragmentStateAdapter(fa) {
        override fun getItemCount(): Int = FRAGMENT_NUMBER

        override fun createFragment(position: Int): Fragment {
            return when(position) {
                0 -> CategoryFragment()
                1 -> ColorFragment()
                2 -> MaterialFragment()
                else -> SeasonFragment()
            }
        }
    }
}