package com.strait.ivblanc.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.strait.ivblanc.R
import com.strait.ivblanc.data.model.dto.Clothes
import com.strait.ivblanc.data.model.dto.PhotoItem
import java.lang.Exception

class ExpandableRecyclerViewAdapter<T>(val context: Context): RecyclerView.Adapter<ExpandableRecyclerViewAdapter<T>.ViewHolder>() {
    companion object {
        const val HEADER = 0
        const val CHILD = 1
    }

    val data = arrayListOf<PhotoItem<T>>()
    lateinit var itemClickListener: ItemClickListener
    lateinit var itemLongClickListener: ItemLongClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when(viewType) {
            HEADER -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.list_photo_header, parent, false)
                ViewHolder(view)
            }
            // TODO: 2022/01/24 item content 타입에 따른 분기 추가
            CHILD -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.list_photo_item_clothes, parent, false)
                ViewHolder(view)
            }
            else -> throw Exception("정의되지 않은 타입")
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        when(item.type) {
            HEADER -> {
                holder.bindHeader(item, position)
            }
            CHILD -> {
                holder.bindChild(item, position)
            }
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    interface ItemClickListener {
        fun onClick(position: Int, viewType: Int)
    }

    interface ItemLongClickListener {
        fun onLongClick(position: Int)
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bindHeader(item: PhotoItem<T>, position: Int) {
            itemView.findViewById<TextView>(R.id.textView_photo_header).also{
                it.text = item.text
                it.setOnClickListener {
                    itemClickListener.onClick(position, HEADER)
                }
            }
        }

        // TODO: 2022/01/24 item content 타입에 따른 분기 추가
        fun bindChild(item: PhotoItem<T>, position: Int) {
            when(item.content) {
                item.content is Clothes -> {
                    val imageView = itemView.findViewById<ImageView>(R.id.imageView_photoItem_clothes).also {
                        it.setOnClickListener{
                            itemClickListener.onClick(position, CHILD)
                        }
                    }
                    Glide.with(context).load((item.content as Clothes).url).centerCrop().into(imageView)
                }
            }
        }
    }
}

