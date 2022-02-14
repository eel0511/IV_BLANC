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

class HorizontalRVAdapter<T>: RecyclerView.Adapter<HorizontalRVAdapter<T>.ViewHolder>() {
    companion object {
        const val CLOTHES = 0
        const val STYLE = 1
    }
    private var data = mutableListOf<T>()
    lateinit var itemClickListener: ItemClickListener

    fun setData(dataList: List<T>) {
        data = dataList.toMutableList()
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener { if(this@HorizontalRVAdapter::itemClickListener.isInitialized) {
                itemClickListener.onClick(adapterPosition)
            }}
        }

        fun bind(data: T) {
            when(data) {
                is Clothes -> {
                    val imageView = itemView.findViewById<ImageView>(R.id.imageView_horizontalItem_clothes)
                    Glide.with(itemView).load(data.url).centerCrop().into(imageView)
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
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_horizontal_clothes_item, parent, false)
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
}