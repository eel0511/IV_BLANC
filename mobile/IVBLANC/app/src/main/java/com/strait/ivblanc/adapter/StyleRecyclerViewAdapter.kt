package com.strait.ivblanc.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.strait.ivblanc.R
import com.strait.ivblanc.data.model.dto.Clothes
import com.strait.ivblanc.component.ItemTouchHelperListener

class StyleRecyclerViewAdapter(val editorAdapter: StyleEditorAdapter): RecyclerView.Adapter<StyleRecyclerViewAdapter.ViewHolder>(), ItemTouchHelperListener {
    var data = mutableListOf<Clothes>()

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(clothes: Clothes) {
            val imageView = itemView.findViewById<ImageView>(R.id.imageView_styleMaking_item)
            Glide.with(itemView).load(clothes.url).into(imageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_style_making_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size

    override fun onItemMove(startPosition: Int, endPosition: Int): Boolean {
        val item = data[startPosition]
        data.removeAt(startPosition)
        data.add(endPosition, item)
        notifyItemMoved(startPosition, endPosition)
        notifyToStyleEditor()
        return true
    }

    // 스와이프로 삭제 시 editorAdapter에 삭제 요청
    override fun onItemSwipe(position: Int) {
        val clothes = data[position]
        data.removeAt(position)
        notifyItemRemoved(position)
        editorAdapter.deleteClothes(clothes)
        notifyToStyleEditor()
    }

    fun addOrUpdateClothes(clothes: Clothes) {
        val largeCategory = getLargeCategory(clothes)
        val item = data.find {
            largeCategory == getLargeCategory(it)
        }
        if(item != null) {
            val index = data.indexOf(item)
            data.set(index, clothes)
            notifyItemChanged(index)
        } else {
            data.add(clothes)
            notifyItemInserted(data.indexOf(clothes))
        }
        notifyToStyleEditor()
    }

    fun notifyToStyleEditor() {
        val largeCategories = mutableListOf<Int>()
        data.forEach {
            largeCategories.add(getLargeCategory(it))
        }
        editorAdapter.changeImageZorder(largeCategories)
    }

    private fun getLargeCategory(clothes: Clothes): Int {
        return clothes.category.toString()[0].digitToInt()
    }

}