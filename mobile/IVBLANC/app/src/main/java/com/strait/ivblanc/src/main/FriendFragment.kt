package com.strait.ivblanc.src.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewFragment
import android.widget.TextView
import androidx.core.view.GravityCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.strait.ivblanc.R
import com.strait.ivblanc.adapter.FriendRecyclerViewAdapter
import com.strait.ivblanc.config.BaseFragment
import com.strait.ivblanc.data.model.dto.FriendViewdata
import com.strait.ivblanc.data.model.viewmodel.FriendViewModel
import com.strait.ivblanc.data.model.viewmodel.MainViewModel
import com.strait.ivblanc.data.repository.FriendRepository
import com.strait.ivblanc.databinding.FragmentFriendBinding
import com.strait.ivblanc.databinding.FragmentHistoryBinding
import com.strait.ivblanc.src.friend.FriendCloset
import com.strait.ivblanc.src.login.LoginActivity
import com.strait.ivblanc.util.LoginUtil
import kotlinx.coroutines.*


class FriendFragment :
    BaseFragment<FragmentFriendBinding>(FragmentFriendBinding::bind, R.layout.fragment_friend) {
    companion object {
        val FRIEND_INFO = "friendInfo"
    }
    lateinit var friendRecyclerViewAdapter: FriendRecyclerViewAdapter
    private val friendViewModel: FriendViewModel by activityViewModels()
    private val loginUtil = LoginUtil

    var list = arrayListOf<FriendViewdata>()

    private val itemClickListener = object : FriendRecyclerViewAdapter.ItemClickListener {
        override fun onClick(friendViewdata: FriendViewdata) {
            val intent = Intent(requireActivity(), FriendCloset::class.java)
            intent.putExtra(FRIEND_INFO, friendViewdata)
            startActivity(intent)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        initDrawerLayout(view)
    }

    override fun onResume() {
        super.onResume()
        reloadImages()
    }

    fun reloadImages() {
        LoginUtil.getUserInfo()?.let {
            list.clear()
            friendViewModel.getAllFriends(it.email)
        }
    }

    fun init() {
        friendRecyclerViewAdapter = FriendRecyclerViewAdapter().apply {
            itemClickListener = this@FriendFragment.itemClickListener
        }
        binding.friendRecyclerview.apply {
            adapter = friendRecyclerViewAdapter
            layoutManager =
                LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        }

        friendViewModel.friendListLiveData.observe(viewLifecycleOwner) {
            if(it.isEmpty()){
                binding.nonFriendImg.visibility=View.VISIBLE
            }else{
                Log.d("zczczc", ": "+friendViewModel.friendListLiveData.value)
                binding.nonFriendImg.visibility=View.GONE
                list.clear()
                Log.d("list", "reloadImages: " + it + it.size)
                it.forEach {
                    Log.d("friendlist", "reloadImages: " + it)
                    list.add(it)
                }
                friendRecyclerViewAdapter.friendViewdata = list
                friendRecyclerViewAdapter.notifyDataSetChanged()
            }
        }
    }

    private fun initDrawerLayout(view: View) {
        val userInfo = loginUtil.getUserInfo()
        // drawerHeader
        val navigationView = binding.navigationView
        val headerView = navigationView.getHeaderView(0)
        val navUsername = headerView.findViewById<View>(R.id.drawerHeaderUserName) as TextView
        navUsername.text = userInfo!!.name + " 님의 이블랑"

        // drawerLayout menu event
        navigationView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.item_logout ->{
                    loginUtil.signOut()

                    val intent = Intent(requireActivity(), LoginActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

                    startActivity(intent)
                }
            }
            binding.myPageDrawer.closeDrawer(GravityCompat.START)
            false
        }
    }
}