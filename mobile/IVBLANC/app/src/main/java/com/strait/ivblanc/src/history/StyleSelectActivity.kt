package com.strait.ivblanc.src.history

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.strait.ivblanc.adapter.StyleSelectRecyclerViewAdapter
import com.strait.ivblanc.config.BaseActivity
import com.strait.ivblanc.data.model.dto.History
import com.strait.ivblanc.data.model.dto.Style
import com.strait.ivblanc.data.model.viewmodel.StyleViewModel
import com.strait.ivblanc.databinding.ActivityStyleSelectBinding

private const val TAG = "StyleSelect"
class StyleSelectActivity : BaseActivity<ActivityStyleSelectBinding>(ActivityStyleSelectBinding::inflate) {
    private lateinit var styleSelectRVA: StyleSelectRecyclerViewAdapter
    private val styleViewModel: StyleViewModel by viewModels()
    lateinit var history: History
    lateinit var location: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        intent.getParcelableExtra<History>("history")?.let {
            history = it
        } ?: finish()
        location = intent.getStringExtra("location").toString()

        setClickListeners()
        setRecyclerView()

        if(history.historyId == 0){
            binding.textViewHistoryEditTitle.text = "Make History"
        }

        styleViewModel.styleListLiveData.observe(this) {
            styleSelectRVA.data = it
            Log.d(TAG, "getItemCount: ${styleSelectRVA.getItemCount()}")
            if(styleSelectRVA.getItemCount() == 0){
                binding.recyclerViewStyleList.visibility = View.GONE
                binding.textViewNoStyle.visibility = View.VISIBLE
            } else {
                binding.recyclerViewStyleList.visibility = View.VISIBLE
                binding.textViewNoStyle.visibility = View.GONE
            }
            styleSelectRVA.notifyDataSetChanged()
        }
    }

    private fun setClickListeners() {
        binding.imageViewStyleSelectClose.setOnClickListener {
            finish()
        }
    }

    private fun setRecyclerView(){
        styleSelectRVA = StyleSelectRecyclerViewAdapter()

        styleViewModel.getAllStyles()

        binding.recyclerViewStyleList.apply {
            adapter = styleSelectRVA
            layoutManager = GridLayoutManager(this@StyleSelectActivity, 3, RecyclerView.VERTICAL, false)
        }

        styleSelectRVA.itemClickListener = object : StyleSelectRecyclerViewAdapter.ItemClickListener{
            override fun onClick(position: Int) {
                val style = styleSelectRVA.data[position]
                Log.d(TAG, "onClick: $style, $history")
                history.styleUrl = style.url
                Log.d(TAG, "afterClick: $history")

                if(style != null) {
                    val intent = Intent(this@StyleSelectActivity, HistoryEditActivity::class.java)
                        .putExtra("history", history)
                        .putExtra("location", location)
                        .putExtra("styleId", style.styleId)
                    startActivity(intent)

                }
                finish()
            }
        }

    }
}