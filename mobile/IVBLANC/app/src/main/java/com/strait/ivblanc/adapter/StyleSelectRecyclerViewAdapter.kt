package com.strait.ivblanc.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.strait.ivblanc.R
import com.strait.ivblanc.data.model.dto.*

private const val TAG = "StyleSelect_RVA"
class StyleSelectRecyclerViewAdapter(): RecyclerView.Adapter<StyleSelectRecyclerViewAdapter.ViewHolder>() {
    lateinit var itemClickListener: ItemClickListener
    var data :List<Style> = listOf()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Style) {
            val imageView = itemView.findViewById<ImageView>(R.id.imageView_photoItem_clothes)
            Glide.with(imageView).load(item.url).centerCrop().error(R.drawable.circle).into(imageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.list_photo_item_clothes, parent, false)
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(data[position])

    override fun getItemCount(): Int {
        Log.d(TAG, "getItemCount: ${data.size}")
        return data.size
    }

    interface ItemClickListener {
        fun onClick(style: Style)
    }
}