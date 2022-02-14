package com.strait.ivblanc.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.strait.ivblanc.R
import com.strait.ivblanc.data.model.dto.Friend

class WaitRecyclerViewAdapter() : RecyclerView.Adapter<WaitRecyclerViewAdapter.ViewHolder>() {
    lateinit var itemClickListener: ItemClickListener
    var mywaitList = listOf<Friend>()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val img = itemView.findViewById<ImageView>(R.id.myrequest_img)
        private val text = itemView.findViewById<TextView>(R.id.myreques_text)
        private val button = itemView.findViewById<ImageButton>(R.id.my_request_button)
        fun bind(item: Friend) {
            img.setImageResource(R.drawable.newlogo)
            text.text = item.friendName + "님의 수락을 기다리는 중입니다."
            button.setImageResource(R.drawable.ic_close_white)
            button.setOnClickListener {
                itemClickListener.onClick(item)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): WaitRecyclerViewAdapter.ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.list_myrequest_item, parent, false)
        )

    override fun onBindViewHolder(holder: WaitRecyclerViewAdapter.ViewHolder, position: Int) =
        holder.bind(mywaitList[position])

    override fun getItemCount(): Int = mywaitList.size
    interface ItemClickListener {
        fun onClick(friend: Friend)
    }

}