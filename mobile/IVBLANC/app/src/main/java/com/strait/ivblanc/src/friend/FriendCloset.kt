package com.strait.ivblanc.src.friend

import android.content.Intent
import android.icu.util.ULocale
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.activityViewModels
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.strait.ivblanc.R
import com.strait.ivblanc.config.BaseActivity
import com.strait.ivblanc.data.model.dto.Clothes
import com.strait.ivblanc.data.model.dto.FriendViewdata
import com.strait.ivblanc.data.model.dto.Style
import com.strait.ivblanc.data.model.viewmodel.ClothesViewModel
import com.strait.ivblanc.data.model.viewmodel.FriendViewModel
import com.strait.ivblanc.data.model.viewmodel.MainViewModel
import com.strait.ivblanc.databinding.ActivityFriendClosetBinding
import com.strait.ivblanc.src.main.FriendFragment
import com.strait.ivblanc.src.photoSelect.AlbumFragment
import com.strait.ivblanc.src.photoSelect.CameraFragment
import com.strait.ivblanc.src.photoSelect.PhotoSelectActivity
import com.strait.ivblanc.ui.PhotoListFragment
import com.strait.ivblanc.util.CategoryCode

class FriendCloset :
    BaseActivity<ActivityFriendClosetBinding>(ActivityFriendClosetBinding::inflate) {
    val friendViewModel: FriendViewModel by viewModels()
    val clothesViewModel: ClothesViewModel by viewModels()
    lateinit var viewPager: ViewPager2
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        var test = intent.getParcelableExtra<FriendViewdata>("test")
        friendViewModel.setToolbarTitle("친구 " + test!!.name + "의 옷장")
        friendViewModel.setLeadingIcon(R.drawable.ic_back)
        clothesViewModel.getAllFriendClothesWithCategory(test.email,CategoryCode.TOTAL)
        setToolbar()
        setViewPager()
    }

    override fun onResume() {
        super.onResume()
        setToolbar()
    }

    private fun setToolbar() {
        setSupportActionBar(binding.toolbarFriend.friendToolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        friendViewModel.toolbarTitle.observe(this) {
            Log.d("FriendCloset", "setToolbar: " + it)
            setToolbarTitle(it)
        }
        friendViewModel.leadingIconDrawable.observe(this) {
            setLeadingIcon(it, getListener(it))
        }

        friendViewModel.trailingIconDrawable.observe(this) {
            setTrailingIcon(it, getListener(it))
        }
    }

    private fun setViewPager() {
        viewPager = binding.viewpagerFriend
        val viewPagerAdapter = ScreenSlidePagerAdapter(this)
        viewPager.adapter = viewPagerAdapter
        val tabLayout = binding.tabLayoutFriend
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "clothes"
                else -> tab.text = "style"
            }
        }.attach()
    }

    private inner class ScreenSlidePagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int = 2

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> PhotoListFragment<Clothes>()
                else -> PhotoListFragment<Style>()
            }
        }
    }

    // imageView의 background drawable Id에 따라 버튼 리스너 반환
    private fun getListener(resId: Int): View.OnClickListener {
        return when (resId) {
            R.drawable.ic_close -> object : View.OnClickListener {
                override fun onClick(v: View?) {
                    this@FriendCloset.onBackPressed()
                }
            }
            // 같은 add Drawable 일때, 현재 fragment의 tag로 리스너 설정
            R.drawable.ic_back -> {
                object : View.OnClickListener {
                    override fun onClick(v: View?) {
                        finish()
                    }
                }
            }
            else -> View.OnClickListener { }
        }
    }

    private fun getCurrentFragmentTag(): String? =
        supportFragmentManager.findFragmentById(R.id.tabLayout_friend)?.tag

    private fun setToolbarTitle(title: String) {
        binding.toolbarFriend.textViewFriendToolbar.text = title
    }

    private fun setLeadingIcon(resId: Int, clickListener: View.OnClickListener) {
        try {
            binding.toolbarFriend.imageViewFriendToolbarLeadingIcon.background =
                ResourcesCompat.getDrawable(resources, resId, null)
        } catch (e: Exception) {
        }
        binding.toolbarFriend.imageViewFriendToolbarLeadingIcon.setOnClickListener(clickListener)
    }

    private fun setTrailingIcon(resId: Int, clickListener: View.OnClickListener) {
        try {
            binding.toolbarFriend.imageViewFriendToolbarTrailingIcon.background =
                ResourcesCompat.getDrawable(resources, resId, null)
        } catch (e: Exception) {
        }
        binding.toolbarFriend.imageViewFriendToolbarTrailingIcon.setOnClickListener(clickListener)
    }

}