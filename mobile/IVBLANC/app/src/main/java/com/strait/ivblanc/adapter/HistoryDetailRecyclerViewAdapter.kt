package com.strait.ivblanc.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.strait.ivblanc.R
import com.strait.ivblanc.data.model.dto.HistoryPhoto

class HistoryDetailRecyclerViewAdapter(): RecyclerView.Adapter<HistoryDetailRecyclerViewAdapter.ViewHolder>() {
    lateinit var itemClickListener: ItemClickListener
    var data = listOf<HistoryPhoto>()

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(photo: HistoryPhoto) {
            val imageView = itemView.findViewById<ImageView>(R.id.imageView_photoItem_history)
            Glide.with(imageView).load(photo.url).into(imageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_photo_item_history, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(data[position])

    override fun getItemCount(): Int = data.size

    interface ItemClickListener {
        fun onClick(photo: HistoryPhoto)
    }
}