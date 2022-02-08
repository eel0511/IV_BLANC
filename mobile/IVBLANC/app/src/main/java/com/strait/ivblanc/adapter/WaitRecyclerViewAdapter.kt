package com.strait.ivblanc.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.strait.ivblanc.R

class WaitRecyclerViewAdapter() : RecyclerView.Adapter<WaitRecyclerViewAdapter.ViewHolder>() {
    lateinit var itemClickListener: ItemClickListener
    var mywaitList = listOf<String>()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val img = itemView.findViewById<ImageView>(R.id.myrequest_img)
        private val text = itemView.findViewById<TextView>(R.id.myreques_text)
        private val button = itemView.findViewById<ImageButton>(R.id.my_request_button)
        fun bind(item: String) {
            img.setImageURI(Uri.parse("https://storage.googleapis.com/iv-blanc.appspot.com/00e3e841-0ec1-4261-909a-52ff448af69a.jpeg"))
            text.text = item + "님 의 친구 수락을 기다리는 중입니다."
            button.visibility = View.GONE
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
        fun onClick()
    }

}