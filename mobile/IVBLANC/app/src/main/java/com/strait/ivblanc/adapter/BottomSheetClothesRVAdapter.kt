package com.strait.ivblanc.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.strait.ivblanc.R
import com.strait.ivblanc.data.model.dto.Clothes

class BottomSheetClothesRVAdapter: RecyclerView.Adapter<BottomSheetClothesRVAdapter.ViewHolder>() {
    private var data = listOf<Clothes>()
    lateinit var itemClickListener: ItemClickListener

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(clothes: Clothes) {
            itemView.setOnClickListener { if(this@BottomSheetClothesRVAdapter::itemClickListener.isInitialized) itemClickListener.onClick(clothes) }
            val imageView = itemView.findViewById<ImageView>(R.id.imageView_photoItem_clothes)
            Glide.with(itemView.context).load(clothes.url).into(imageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_photo_item_clothes, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size

    fun setData(list: List<Clothes>) {
        data = list
        notifyDataSetChanged()
    }

    interface ItemClickListener {
        fun onClick(clothes: Clothes)
    }
}