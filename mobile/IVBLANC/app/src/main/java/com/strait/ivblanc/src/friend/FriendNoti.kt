package com.strait.ivblanc.src.friend

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
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
            }
        }
    private val waititemClickListener = object : WaitRecyclerViewAdapter.ItemClickListener {
        override fun onClick(friend: Friend) {

        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        friendViewModel.getmyrequestFriend("aaa@a.com")
        friendViewModel.getmyWaitFriend("aaa@a.com")
        initMyRequestRecycler()
        initWaitRecycler()
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
}