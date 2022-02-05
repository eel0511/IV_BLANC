package com.strait.ivblanc.src.main

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.strait.ivblanc.R
import com.strait.ivblanc.adapter.FriendRecyclerViewAdapter
import com.strait.ivblanc.config.BaseFragment
import com.strait.ivblanc.data.model.dto.FriendViewdata
import com.strait.ivblanc.databinding.FragmentFriendBinding
import com.strait.ivblanc.databinding.FragmentHistoryBinding


class FriendFragment : BaseFragment<FragmentFriendBinding>(FragmentFriendBinding::bind,R.layout.fragment_friend){

    lateinit var friendRecyclerViewAdapter:FriendRecyclerViewAdapter
    private val itemClickListener = object :FriendRecyclerViewAdapter.ItemClickListener{
        override fun onClick(friendViewdata: FriendViewdata) {
            TODO("Not yet implemented")
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    override fun onResume() {
        super.onResume()
        reloadImages()
    }
    fun reloadImages() {
        var u = Uri.parse("https://storage.googleapis.com/iv-blanc.appspot.com/00e3e841-0ec1-4261-909a-52ff448af69a.jpeg")
        friendRecyclerViewAdapter.friendViewdata = listOf(FriendViewdata("",u,u,u,u,u,u,u,u))
        friendRecyclerViewAdapter.notifyDataSetChanged()
    }
    fun init(){
        friendRecyclerViewAdapter = FriendRecyclerViewAdapter().apply {
            itemClickListener = this@FriendFragment.itemClickListener
        }
        binding.friendRecyclerview.apply {
            adapter=friendRecyclerViewAdapter
            layoutManager = LinearLayoutManager(requireActivity(),LinearLayoutManager.HORIZONTAL,false)
        }
    }
}