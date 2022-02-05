package com.strait.ivblanc.src.process

import android.graphics.Rect
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.strait.ivblanc.R
import com.strait.ivblanc.adapter.ColorPickRecyclerViewAdapter
import com.strait.ivblanc.config.BaseFragment
import com.strait.ivblanc.databinding.FragmentColorBinding
import com.strait.ivblanc.util.ColorCode

class ColorFragment : BaseFragment<FragmentColorBinding>(FragmentColorBinding::bind, R.layout.fragment_color) {
    lateinit var recyclerViewAdapter: ColorPickRecyclerViewAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        recyclerViewAdapter = ColorPickRecyclerViewAdapter()
        binding.recyclerViewColorF.adapter = recyclerViewAdapter.apply {
            data = ColorCode.LIST
        }
        binding.recyclerViewColorF.layoutManager = GridLayoutManager(requireActivity(), 4, RecyclerView.VERTICAL, false)
        binding.recyclerViewColorF.addItemDecoration(object: RecyclerView.ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
                super.getItemOffsets(outRect, view, parent, state)
                outRect.top = 50
            }
        })
    }
}