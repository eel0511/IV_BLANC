package com.strait.ivblanc.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.ColorFilter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.strait.ivblanc.R

class ColorPickRecyclerViewAdapter:
    RecyclerView.Adapter<ColorPickRecyclerViewAdapter.ViewHolder>() {
    var data = listOf<String>()
    lateinit var itemClickListener: ItemClickListener

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        @SuppressLint( "ResourceType")
        fun bind(colorCode: String) {
            val imageView = itemView.findViewById<ImageView>(R.id.imageView_color_list).apply {
                setOnClickListener {
                    itemClickListener.onClick(adapterPosition)
                }
            }

            if(colorCode.startsWith("#")) {
                val colorId = Color.parseColor(colorCode)
                if(colorCode == "#FFFFFF") {
                    imageView.setImageDrawable(ResourcesCompat.getDrawable(itemView.resources, R.drawable.circle_stroke, null))
                }
                imageView.background.setTint(colorId)
            } else if(colorCode == "etc") {
                imageView.background = null
                imageView.setImageDrawable(ResourcesCompat.getDrawable(itemView.resources, R.drawable.ic_etc, null))
            } else if(colorCode == "variety") {
                imageView.background = ResourcesCompat.getDrawable(itemView.resources, R.drawable.color_wheel, null)
                imageView.setImageDrawable(null)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_color_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size

    interface ItemClickListener {
        fun onClick(position: Int)
    }
}