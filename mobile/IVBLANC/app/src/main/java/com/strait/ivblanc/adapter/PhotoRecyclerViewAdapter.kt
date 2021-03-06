package com.strait.ivblanc.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.strait.ivblanc.R

class PhotoRecyclerViewAdapter(): RecyclerView.Adapter<PhotoRecyclerViewAdapter.ViewHolder>() {
    lateinit var itemClickListener: ItemClickListener
    var uris = listOf<Uri>()

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        init {
            itemView.tag = false
        }
        fun bind(item: Uri) {
            val imageView = itemView.findViewById<ImageView>(R.id.imageView_photoItem_clothes)
            Glide.with(imageView).load(item).diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).centerCrop().into(imageView)
            itemView.setOnClickListener{
                itemClickListener.onClick(item, itemView, adapterPosition)
                itemView.tag = itemView.tag == false
                if(itemView.tag == true) {
                    itemView.background = ResourcesCompat.getDrawable(itemView.resources, R.drawable.list_box_select, null)
                } else {
                    itemView.background = ResourcesCompat.getDrawable(itemView.resources, R.drawable.list_box, null)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_photo_item_clothes, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(uris[position])

    override fun getItemCount(): Int = uris.size

    interface ItemClickListener {
        fun onClick(uri: Uri, view: View, position: Int)
    }
}