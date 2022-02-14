package com.strait.ivblanc.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.strait.ivblanc.R
import com.strait.ivblanc.data.model.dto.FriendViewdata

class FriendRecyclerViewAdapter() : RecyclerView.Adapter<FriendRecyclerViewAdapter.ViewHolder>() {
    lateinit var itemClickListener: ItemClickListener
    var friendViewdata = listOf<FriendViewdata>()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val Name = itemView.findViewById<TextView>(R.id.list_friend_name)
        private val cloth1 = itemView.findViewById<ImageView>(R.id.list_friend_cloth1)
        private val cloth2 = itemView.findViewById<ImageView>(R.id.list_friend_cloth2)
        private val cloth3 = itemView.findViewById<ImageView>(R.id.list_friend_cloth3)
        private val cloth4 = itemView.findViewById<ImageView>(R.id.list_friend_cloth4)
        private val style1 = itemView.findViewById<ImageView>(R.id.list_friend_style1)
        private val style2 = itemView.findViewById<ImageView>(R.id.list_friend_style2)
        private val style3 = itemView.findViewById<ImageView>(R.id.list_friend_style3)
        private val style4 = itemView.findViewById<ImageView>(R.id.list_friend_style4)
        private val non_text = itemView.findViewById<TextView>(R.id.no_item_text)

        fun bind(item: FriendViewdata) {
            Name.text = item.name+"님의 옷장"
            if(item.style1==item.style2&&item.style2==item.style3&&item.style3==item.style4){
                Glide.with(cloth1).load(item.cloth1).diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).centerCrop().into(cloth1)
                Glide.with(cloth2).load(item.cloth2).diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).centerCrop().into(cloth2)
                Glide.with(cloth3).load(item.cloth3).diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).centerCrop().into(cloth3)
                Glide.with(cloth4).load(item.cloth4).diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).centerCrop().into(cloth4)
                non_text.visibility=View.VISIBLE
                style1.visibility=View.GONE
                style2.visibility=View.GONE
                style3.visibility=View.GONE
                style4.visibility=View.GONE
            }else{
                non_text.visibility=View.GONE
                style1.visibility=View.VISIBLE
                style2.visibility=View.VISIBLE
                style3.visibility=View.VISIBLE
                style4.visibility=View.VISIBLE
                Glide.with(cloth1).load(item.cloth1).diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).centerCrop().into(cloth1)
                Glide.with(cloth2).load(item.cloth2).diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).centerCrop().into(cloth2)
                Glide.with(cloth3).load(item.cloth3).diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).centerCrop().into(cloth3)
                Glide.with(cloth4).load(item.cloth4).diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).centerCrop().into(cloth4)
                Glide.with(style1).load(item.style1).diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).centerCrop().into(style1)
                Glide.with(style2).load(item.style2).diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).centerCrop().into(style2)
                Glide.with(style3).load(item.style3).diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).centerCrop().into(style3)
                Glide.with(style4).load(item.style4).diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).centerCrop().into(style4)
            }

            itemView.setOnClickListener{
                itemClickListener.onClick(item)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FriendRecyclerViewAdapter.ViewHolder =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_friend, parent, false))

    override fun onBindViewHolder(holder: FriendRecyclerViewAdapter.ViewHolder, position: Int) =
        holder.bind(friendViewdata[position])

    override fun getItemCount(): Int = friendViewdata.size

    interface ItemClickListener {
        fun onClick(friendViewdata: FriendViewdata)
    }
}