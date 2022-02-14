package com.strait.ivblanc.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.strait.ivblanc.R
import com.strait.ivblanc.data.model.dto.Clothes
import com.strait.ivblanc.data.model.dto.Style

class PhotoListRVAdapter<T>: RecyclerView.Adapter<PhotoListRVAdapter<T>.ViewHolder>() {
    companion object {
        const val CLOTHES = 0
        const val STYLE = 1
    }
    var data = mutableListOf<T>()
    lateinit var itemClickListener: ItemClickListener
    lateinit var itemLongClickListener: ItemLongClickListener

    fun setDatas(dataList: List<T>) {
        data = dataList.toMutableList()
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener { if(this@PhotoListRVAdapter::itemClickListener.isInitialized) {
                itemClickListener.onClick(adapterPosition)
            }}
        }

        fun bind(data: T) {
            when(data) {
                is Clothes -> {
                    val imageView = itemView.findViewById<ImageView>(R.id.imageView_photoItem_clothes)
                    Glide.with(itemView).load(data.url).centerCrop().into(imageView)
                    val favoriteImageView = itemView.findViewById<ImageView>(R.id.imageView_photoItem_favorite)
                    if(data.favorite == 1) favoriteImageView.visibility = View.VISIBLE
                    else favoriteImageView.visibility = View.GONE
                }
                is Style -> {

                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        when(viewType) {
            STYLE -> {

            }
        }
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_photo_item_clothes, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size

    override fun getItemViewType(position: Int): Int {
        return when(data[position]) {
            is Clothes -> CLOTHES
            else -> STYLE
        }
    }

    interface ItemClickListener {
        fun onClick(position: Int)
    }

    interface ItemLongClickListener {
        fun onLongClick(position: Int)
    }
}