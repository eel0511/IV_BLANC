package com.strait.ivblanc.src.friend

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.strait.ivblanc.R
import com.strait.ivblanc.adapter.FriendRecyclerViewAdapter
import com.strait.ivblanc.adapter.MyrequestRecyclerViewAdapter
import com.strait.ivblanc.config.BaseActivity
import com.strait.ivblanc.data.model.dto.Friend
import com.strait.ivblanc.data.model.dto.FriendViewdata
import com.strait.ivblanc.data.model.viewmodel.FriendViewModel
import com.strait.ivblanc.databinding.ActivityFriendNotiBinding
import com.strait.ivblanc.databinding.ActivityMainBinding

class FriendNoti : BaseActivity<ActivityFriendNotiBinding>(ActivityFriendNotiBinding::inflate) {
    private val friendViewModel: FriendViewModel by viewModels()
    lateinit var myrequestRecyclerViewAdapter: MyrequestRecyclerViewAdapter
    var list = arrayListOf<String>()
    private val itemClickListener = object : MyrequestRecyclerViewAdapter.ItemClickListener {
        override fun onClick() {

        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        friendViewModel.getmyrequestFriend("aaa@a.com")
        init()
    }

    fun init(){
        myrequestRecyclerViewAdapter = MyrequestRecyclerViewAdapter().apply {
            itemClickListener = this@FriendNoti.itemClickListener
        }
        binding.myrequestRecyclerView.apply {
            adapter = myrequestRecyclerViewAdapter
            layoutManager= LinearLayoutManager(this@FriendNoti, LinearLayoutManager.VERTICAL, false)

        }
        friendViewModel.friendRequestListLiveData.observe(this){
            list.clear()
            Log.d("ssss", "init: "+it)
            it.forEach {
                list.add(it.friendName)
            }
            myrequestRecyclerViewAdapter.myrequestList=list
            myrequestRecyclerViewAdapter.notifyDataSetChanged()
        }
    }
}