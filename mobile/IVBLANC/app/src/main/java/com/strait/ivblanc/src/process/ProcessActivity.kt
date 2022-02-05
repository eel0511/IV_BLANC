package com.strait.ivblanc.src.process

import android.net.Uri
import android.os.Bundle
import android.widget.Toast
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
    lateinit var imgUri: Uri
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        intent.getStringExtra("uri")?.let {
            imgUri = Uri.parse(it.toString())
        }
        if(!this::imgUri.isInitialized) {
            toast("선택한 이미지가 없습니다.", Toast.LENGTH_SHORT)
            finish()
        } else {
            init()
        }
    }

    private fun init() {
        viewPager = binding.viewpagerProcess
        viewPagerAdapter = SimpleFragmentStateAdapter(this)
        viewPager.adapter = viewPagerAdapter
        binding.imageViewProcess.setImageURI(imgUri)
        processViewModel.imgUri = imgUri
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

    fun goNext() {
        if(viewPager.currentItem < 4) {
            viewPager.currentItem += 1
        }
    }

    fun goBefore() {
        if(viewPager.currentItem > 0) {
            viewPager.currentItem -= 1
        }
    }
}