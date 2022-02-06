package com.strait.ivblanc.src.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
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
import kotlinx.coroutines.*


class FriendFragment : BaseFragment<FragmentFriendBinding>(FragmentFriendBinding::bind,R.layout.fragment_friend){

    lateinit var friendRecyclerViewAdapter:FriendRecyclerViewAdapter
    val friendRepository = FriendRepository()
    val scope = CoroutineScope(Dispatchers.Main)
    private val viewModel: MainViewModel by activityViewModels()
    private val friendViewModel:FriendViewModel by activityViewModels()
    private val itemClickListener = object :FriendRecyclerViewAdapter.ItemClickListener{
        override fun onClick(friendViewdata: FriendViewdata) {
            val intent = Intent(requireActivity(),FriendCloset::class.java)
            intent.putExtra("test",friendViewdata)
            startActivity(intent)
            friendViewModel.setFriendName(friendViewdata.name)


        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    override fun onResume() {
        super.onResume()
        viewModel.setToolbarTitle("친구")
        scope.launch {
            reloadImages()
        }
    }
    suspend fun reloadImages() {
        var response = friendRepository.getAllFriends("ssu@a.com")
        var u = Uri.parse("https://storage.googleapis.com/iv-blanc.appspot.com/00e3e841-0ec1-4261-909a-52ff448af69a.jpeg")
        var list = arrayListOf<FriendViewdata>()
        response.data!!.dataSet!!.forEach {
            list.add(FriendViewdata(it.friendName,u,u,u,u,u,u,u,u))
        }
        friendRecyclerViewAdapter.friendViewdata = list
        friendRecyclerViewAdapter.notifyDataSetChanged()
    }
    fun init(){
        friendRecyclerViewAdapter = FriendRecyclerViewAdapter().apply {
            itemClickListener = this@FriendFragment.itemClickListener
        }
        binding.friendRecyclerview.apply {
            adapter=friendRecyclerViewAdapter
            layoutManager = LinearLayoutManager(requireActivity(),LinearLayoutManager.VERTICAL,false)
        }
    }
}