package com.strait.ivblanc.src.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.strait.ivblanc.R
import com.strait.ivblanc.adapter.StyleSelectRecyclerViewAdapter
import com.strait.ivblanc.data.model.dto.Style
import com.strait.ivblanc.data.model.viewmodel.StyleViewModel
import com.strait.ivblanc.databinding.FragmentHistoryStyleSelectBinding

class HistoryStyleSelectFragment(val styleSelectedListener: StyleSelectedListener) : DialogFragment() {
    private lateinit var binding: FragmentHistoryStyleSelectBinding
    private lateinit var styleSelectRVA: StyleSelectRecyclerViewAdapter
    private val styleViewModel: StyleViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHistoryStyleSelectBinding.bind(inflater.inflate(R.layout.fragment_history_style_select, container, false))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        styleViewModel.styleListLiveData.observe(this) {
            styleSelectRVA.data = it
            styleSelectRVA.notifyDataSetChanged()
        }

        init()
    }

    private fun init() {
        isCancelable = true
        setRecyclerView()
    }

    private fun setRecyclerView() {
        styleSelectRVA = StyleSelectRecyclerViewAdapter()

        styleViewModel.getAllStyles()

        binding.recyclerViewStyleList.apply {
            adapter = styleSelectRVA
            layoutManager = GridLayoutManager(requireContext(), 3, RecyclerView.VERTICAL, false)
        }

        styleSelectRVA.itemClickListener = object : StyleSelectRecyclerViewAdapter.ItemClickListener{
            override fun onClick(position: Int) {
                val style = styleSelectRVA.data[position]
                styleSelectedListener.getResult(style)
                dismiss()
            }
        }
    }

    interface StyleSelectedListener {
        fun getResult(style: Style)
    }
}