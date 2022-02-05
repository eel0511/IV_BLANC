package com.strait.ivblanc.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.strait.ivblanc.R

class MaterialPickRecyclerViewAdapter:
    RecyclerView.Adapter<MaterialPickRecyclerViewAdapter.ViewHolder>() {
    var data = listOf<Triple<Int, Int, Int>>()
    lateinit var itemClickListener: ItemClickListener

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(materialCode: Triple<Int, Int, Int>) {
            val imageView = itemView.findViewById<ImageView>(R.id.imageView_material_list)
            val textView = itemView.findViewById<TextView>(R.id.textView_material_list_name)
            setClickListener(itemView, itemClickListener)
            imageView.setImageDrawable(ResourcesCompat.getDrawable(itemView.resources, materialCode.third, null))
            textView.text = itemView.resources.getText(materialCode.second)
        }

        private fun setClickListener(itemView: View, itemClickListener: ItemClickListener) {
            itemView.setOnClickListener {
                if(this@MaterialPickRecyclerViewAdapter::itemClickListener.isInitialized) {
                    itemClickListener.onClick(adapterPosition)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_material_item, parent, false)
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