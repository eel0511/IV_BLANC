package com.strait.ivblanc.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.strait.ivblanc.R

class PhotoRecyclerViewAdapter(private var data: List<Uri>): RecyclerView.Adapter<PhotoRecyclerViewAdapter.ViewHolder>() {
    lateinit var itemClickListener: ItemClickListener

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(item: Uri) {
            val imageView = itemView.findViewById<ImageView>(R.id.imageView_photoItem_clothes)
            Glide.with(imageView).load(item).centerCrop().into(imageView)
            itemView.setOnClickListener{
                itemClickListener.onClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_photo_item_clothes, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(data[position])

    override fun getItemCount(): Int = data.size

    interface ItemClickListener {
        fun onClick(position: Int)
    }
}