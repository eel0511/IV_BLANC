package com.strait.ivblanc.src.process

import android.graphics.Color
import android.graphics.Rect
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.strait.ivblanc.R
import com.strait.ivblanc.adapter.ColorPickRecyclerViewAdapter
import com.strait.ivblanc.config.BaseFragment
import com.strait.ivblanc.data.model.viewmodel.ProcessViewModel
import com.strait.ivblanc.databinding.FragmentColorBinding
import com.strait.ivblanc.util.ColorCode

class ColorFragment : BaseFragment<FragmentColorBinding>(FragmentColorBinding::bind, R.layout.fragment_color) {
    lateinit var recyclerViewAdapter: ColorPickRecyclerViewAdapter
    private val processViewModel: ProcessViewModel by activityViewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        recyclerViewAdapter = ColorPickRecyclerViewAdapter()
        binding.recyclerViewColorF.adapter = recyclerViewAdapter.apply {
            data = ColorCode.LIST

            itemClickListener =
                object: ColorPickRecyclerViewAdapter.ItemClickListener {
                override fun onClick(position: Int) {
                    val colorCode = data[position]
                    changeColor(binding.imageViewColorFRepresent, colorCode)
                    processViewModel.setColor(colorCode)
                }
            }
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

    private fun changeColor(imageView: ImageView, colorCode: String) {
        initImageView(imageView)
        if(colorCode.startsWith("#")) {
            val colorId = Color.parseColor(colorCode)
            if(colorCode == "#FFFFFF") {
                imageView.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.circle_stroke, null))
            }
            imageView.background.setTint(colorId)
        } else if(colorCode == "etc") {
            imageView.background = null
            imageView.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.ic_etc, null))
        } else if(colorCode == "variety") {
            imageView.background = ResourcesCompat.getDrawable(resources, R.drawable.color_wheel, null)
            imageView.setImageDrawable(null)
        }
    }
    private fun initImageView(imageView: ImageView) {
        imageView.background = ResourcesCompat.getDrawable(resources, R.drawable.circle, null)
        imageView.setImageDrawable(null)
    }
}