package com.strait.ivblanc.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.strait.ivblanc.R
import com.strait.ivblanc.data.model.dto.FriendViewdata

class MyrequestRecyclerViewAdapter():RecyclerView.Adapter<MyrequestRecyclerViewAdapter.ViewHolder>(){
    lateinit var itemClickListener: ItemClickListener
    var myrequestList = listOf<String>()

    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        private val text = itemView.findViewById<TextView>(R.id.myreques_text)
        private val button = itemView.findViewById<ImageButton>(R.id.my_request_button)
        fun bind(item:String){
            text.text = item+"님이 친구 요청을 보내셨습니다."
            button.setOnClickListener {
                itemClickListener.onClick()
            }
        }
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyrequestRecyclerViewAdapter.ViewHolder =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_myrequest_item, parent, false))

    override fun onBindViewHolder(holder: MyrequestRecyclerViewAdapter.ViewHolder, position: Int) =
        holder.bind(myrequestList[position])

    override fun getItemCount(): Int = myrequestList.size

    interface ItemClickListener {
        fun onClick()
    }
}