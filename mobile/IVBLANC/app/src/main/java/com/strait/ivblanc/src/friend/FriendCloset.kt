package com.strait.ivblanc.src.friend

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.strait.ivblanc.R
import com.strait.ivblanc.config.BaseActivity
import com.strait.ivblanc.data.model.dto.Clothes
import com.strait.ivblanc.data.model.viewmodel.FriendViewModel
import com.strait.ivblanc.data.model.viewmodel.MainViewModel
import com.strait.ivblanc.databinding.ActivityFriendClosetBinding
import com.strait.ivblanc.src.main.FriendFragment
import com.strait.ivblanc.src.photoSelect.PhotoSelectActivity
import com.strait.ivblanc.ui.PhotoListFragment
import com.strait.ivblanc.util.CategoryCode

class FriendCloset :
    BaseActivity<ActivityFriendClosetBinding>(ActivityFriendClosetBinding::inflate) {
    val friendViewModel: FriendViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_friend_closet)
        friendViewModel.getAllClothesWithCategory(CategoryCode.TOTAL)
        init()
    }

    private fun init() {
        setToolbar()
        // 첫 화면 세팅
        setFragment(FriendFragment(), "friend_clothes")

        // nav click 시 fragment 변경
        binding.bottomNavFriend.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_friend_clothes -> {
                    Log.d("nav_c", "init: ")
                    setFragment(PhotoListFragment<Clothes>(), "friend_clothes")
                    true
                }
                // Style로 변경
                R.id.nav_friend_style -> {
                    Log.d("nav_s", "init: ")
                    setFragment(FriendFragment(), "friend_style")
                    true
                }
                else -> false
            }
        }
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

    private fun setFragment(fragment: Fragment, tag: String) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.frameLayout_friend_container, fragment, tag).commit()
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
            R.drawable.ic_add -> {
                when (getCurrentFragmentTag()) {
                    "friend_clothes" -> {
                        object : View.OnClickListener {
                            override fun onClick(v: View?) {
                                startActivity(
                                    Intent(
                                        this@FriendCloset,
                                        PhotoSelectActivity::class.java
                                    ).apply {
                                        putExtra("intend", PhotoSelectActivity.CLOTHES)
                                    })
                            }
                        }
                    }
                    "friend_style" -> {
                        object : View.OnClickListener {
                            override fun onClick(v: View?) {
                                toast("스타일 생성 Activity로 이동", Toast.LENGTH_SHORT)
                            }
                        }
                    }
                    else -> View.OnClickListener {}
                }


            }
            else -> View.OnClickListener { }
        }
    }

    private fun getCurrentFragmentTag(): String? =
        supportFragmentManager.findFragmentById(R.id.frameLayout_friend_container)?.tag

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