package com.strait.ivblanc.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.strait.ivblanc.R

class NotiRecyclerViewAdapter() : RecyclerView.Adapter<NotiRecyclerViewAdapter.ViewHolder>() {
    lateinit var itemClickListener: ItemClickListener
    var notilist = listOf<String>()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val img = itemView.findViewById<ImageView>(R.id.myrequest_img)
        private val text = itemView.findViewById<TextView>(R.id.myreques_text)
        private val button = itemView.findViewById<ImageButton>(R.id.my_request_button)
        fun bind(item: String) {
            img.setImageResource(R.drawable.kakao)
            text.text = item
            button.visibility = View.GONE
            button.setOnClickListener {
                itemClickListener.onClick()
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NotiRecyclerViewAdapter.ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.list_myrequest_item, parent, false)
        )

    override fun onBindViewHolder(holder: NotiRecyclerViewAdapter.ViewHolder, position: Int) =
        holder.bind(notilist[position])

    override fun getItemCount(): Int = notilist.size

    interface ItemClickListener {
        fun onClick()
    }
}