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

class StyleRecyclerViewAdapter: RecyclerView.Adapter<StyleRecyclerViewAdapter.ViewHolder>(), ItemTouchHelperListener {
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
        // TODO: 2022/02/08 Editor View의 이미지 z 값 바꾸기 
        return true
    }

}