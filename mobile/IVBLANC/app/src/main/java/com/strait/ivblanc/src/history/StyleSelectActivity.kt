package com.strait.ivblanc.src.history

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.strait.ivblanc.R
import com.strait.ivblanc.adapter.ExpandableRecyclerViewAdapter
import com.strait.ivblanc.adapter.StyleSelectRecyclerViewAdapter
import com.strait.ivblanc.config.BaseActivity
import com.strait.ivblanc.data.model.dto.PhotoItem
import com.strait.ivblanc.data.model.dto.Style
import com.strait.ivblanc.data.model.viewmodel.StyleViewModel
import com.strait.ivblanc.databinding.ActivityStyleSelectBinding

private const val TAG = "StyleSelect"
class StyleSelectActivity : BaseActivity<ActivityStyleSelectBinding>(ActivityStyleSelectBinding::inflate) {
//    lateinit var exAdapter: ExpandableRecyclerViewAdapter<Style>
    private lateinit var styleSelectRVA: StyleSelectRecyclerViewAdapter
    private val styleViewModel: StyleViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setRecyclerView()

        styleViewModel.styleListLiveData.observe(this) {
            styleSelectRVA.data = it
            Log.d(TAG, "getItemCount: ${styleSelectRVA.getItemCount()}")
            styleSelectRVA.notifyDataSetChanged()
        }
    }

    private fun setRecyclerView(){


        styleSelectRVA = StyleSelectRecyclerViewAdapter()

        styleViewModel.getAllStyles()

        binding.recyclerViewStyleList.adapter = styleSelectRVA
        binding.recyclerViewStyleList.apply {
            adapter = styleSelectRVA
            layoutManager = GridLayoutManager(this@StyleSelectActivity, 3, RecyclerView.VERTICAL, false)

        }

        styleSelectRVA.itemClickListener = object : StyleSelectRecyclerViewAdapter.ItemClickListener{
            override fun onClick(style: Style) {
                TODO("Not yet implemented")
            }
        }

    }
}