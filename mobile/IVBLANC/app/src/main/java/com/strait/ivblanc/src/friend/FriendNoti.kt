package com.strait.ivblanc.src.friend

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.kakao.sdk.common.util.SdkLogLevel
import com.strait.ivblanc.R
import com.strait.ivblanc.adapter.MyrequestRecyclerViewAdapter
import com.strait.ivblanc.adapter.WaitRecyclerViewAdapter
import com.strait.ivblanc.config.BaseActivity
import com.strait.ivblanc.data.model.dto.Friend
import com.strait.ivblanc.data.model.viewmodel.FriendViewModel
import com.strait.ivblanc.databinding.ActivityFriendNotiBinding

class FriendNoti : BaseActivity<ActivityFriendNotiBinding>(ActivityFriendNotiBinding::inflate) {
    private val friendViewModel: FriendViewModel by viewModels()
    lateinit var myrequestRecyclerViewAdapter: MyrequestRecyclerViewAdapter
    lateinit var waitRecyclerViewAdapter: WaitRecyclerViewAdapter
    var requestlist = arrayListOf<Friend>()
    var waitlist = arrayListOf<Friend>()
    private val myrequestitemClickListener =
        object : MyrequestRecyclerViewAdapter.ItemClickListener {
            override fun onClick(friend: Friend) {
                friendViewModel.myacceptFriend(friend.friendEmail, "aaa@a.com")
                AcceptDialog(friend.friendName + "님의 요청을 수락했습니다")
            }
        }
    private val waititemClickListener = object : WaitRecyclerViewAdapter.ItemClickListener {
        override fun onClick(friend: Friend) {
            friendViewModel.cancelFriend("aaa@a.com", friend.friendEmail)
            AcceptDialog(friend.friendName + "님께의 요청을 취소했습니다.")
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        friendViewModel.setToolbarTitle("알림")
        friendViewModel.setLeadingIcon(R.drawable.ic_back)
        friendViewModel.getmyrequestFriend("aaa@a.com")
        friendViewModel.getmyWaitFriend("aaa@a.com")
        initMyRequestRecycler()
        initWaitRecycler()
        setToolbar()
    }

    private fun setToolbar() {
        setSupportActionBar(binding.toolbarFriend.friendToolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        friendViewModel.toolbarTitle.observe(this) {
            setToolbarTitle(it)
        }
        friendViewModel.leadingIconDrawable.observe(this) {
            setLeadingIcon(it, getListener(it))
        }

        friendViewModel.trailingIconDrawable.observe(this) {
            setTrailingIcon(it, getListener(it))
        }
    }

    // imageView의 background drawable Id에 따라 버튼 리스너 반환
    private fun getListener(resId: Int): View.OnClickListener {
        return when (resId) {
            R.drawable.ic_close -> object : View.OnClickListener {
                override fun onClick(v: View?) {
                    this@FriendNoti.onBackPressed()
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

    fun initMyRequestRecycler() {
        myrequestRecyclerViewAdapter = MyrequestRecyclerViewAdapter().apply {
            itemClickListener = this@FriendNoti.myrequestitemClickListener
        }
        binding.myrequestRecyclerView.apply {
            adapter = myrequestRecyclerViewAdapter
            layoutManager =
                LinearLayoutManager(this@FriendNoti, LinearLayoutManager.VERTICAL, false)

        }
        friendViewModel.friendRequestListLiveData.observe(this) {
            requestlist.clear()
            Log.d("ssss", "init: " + it)
            it.forEach {
                requestlist.add(it)
            }
            myrequestRecyclerViewAdapter.myrequestList = requestlist
            myrequestRecyclerViewAdapter.notifyDataSetChanged()
        }
    }

    fun initWaitRecycler() {
        waitRecyclerViewAdapter = WaitRecyclerViewAdapter().apply {
            itemClickListener = this@FriendNoti.waititemClickListener
        }
        binding.waitRecycleView.apply {
            adapter = waitRecyclerViewAdapter
            layoutManager =
                LinearLayoutManager(this@FriendNoti, LinearLayoutManager.VERTICAL, false)
        }
        friendViewModel.friendWaitListLiveData.observe(this) {
            waitlist.clear()
            it.forEach {
                waitlist.add(it)
            }
            waitRecyclerViewAdapter.mywaitList = waitlist
            waitRecyclerViewAdapter.notifyDataSetChanged()
        }
    }

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

    fun AcceptDialog(title: String) {
        MaterialAlertDialogBuilder(this, R.style.MyDialogTheme)
            .setTitle(title)
            .setPositiveButton("확인") { dialog, which ->
                // Respond to positive button press
                object : View.OnClickListener {
                    override fun onClick(v: View?) {
                        onBackPressed()
                    }
                }
            }
            .show()
    }

}