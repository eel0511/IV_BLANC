package com.strait.ivblanc.src.process

import android.graphics.Rect
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.strait.ivblanc.R
import com.strait.ivblanc.adapter.MaterialPickRecyclerViewAdapter
import com.strait.ivblanc.config.BaseFragment
import com.strait.ivblanc.data.model.viewmodel.ProcessViewModel
import com.strait.ivblanc.databinding.FragmentMaterialBinding
import com.strait.ivblanc.util.MaterialCode

class MaterialFragment : BaseFragment<FragmentMaterialBinding>(FragmentMaterialBinding::bind, R.layout.fragment_material) {
    lateinit var recyclerViewAdapter: MaterialPickRecyclerViewAdapter
    private val processViewModel: ProcessViewModel by activityViewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        recyclerViewAdapter = MaterialPickRecyclerViewAdapter().apply {
            data = MaterialCode.LIST
            // 아이템 클릭 리스너
            // 클릭 시 viewModel에 materialCode 반영, TextView에 이름 세팅
            itemClickListener = object: MaterialPickRecyclerViewAdapter.ItemClickListener {
                override fun onClick(position: Int) {
                    processViewModel.setMaterial(data[position].first)
                    binding.textViewMaterialFName.text = resources.getText(data[position].second)
                }
            }
        }
        binding.recyclerViewMaterialF.apply {
            adapter = recyclerViewAdapter
            layoutManager = GridLayoutManager(requireActivity(), 4, RecyclerView.VERTICAL, false)
            addItemDecoration(object: RecyclerView.ItemDecoration() {
                override fun getItemOffsets(
                    outRect: Rect,
                    view: View,
                    parent: RecyclerView,
                    state: RecyclerView.State
                ) {
                    super.getItemOffsets(outRect, view, parent, state)
                    outRect.top = 10
                }
            })
        }
    }

}