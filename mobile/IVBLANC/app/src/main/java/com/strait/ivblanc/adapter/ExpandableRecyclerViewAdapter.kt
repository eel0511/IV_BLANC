package com.strait.ivblanc.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView

class ExpandableRecyclerViewAdapter: RecyclerView.Adapter<ViewHolder>() {
    lateinit var itemClickListener: ItemClickListener
    lateinit var itemLongClickListener: ItemLongClickListener
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    }

    override fun getItemCount(): Int {
    }

    interface ItemClickListener {
        fun onClick(position: Int, viewType: Int)
    }

    interface ItemLongClickListener {
        fun onLongClick(position: Int)
    }
}

class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

}