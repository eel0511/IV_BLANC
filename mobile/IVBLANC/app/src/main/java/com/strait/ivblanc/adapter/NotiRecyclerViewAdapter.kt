package com.strait.ivblanc.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.gson.Gson
import com.strait.ivblanc.R
import com.strait.ivblanc.databinding.ListMyrequestItemBinding
import com.strait.ivblanc.src.fcm.FcmService

class NotiRecyclerViewAdapter() : RecyclerView.Adapter<NotiRecyclerViewAdapter.ViewHolder>() {
    lateinit var itemClickListener: ItemClickListener
    val SP_NAME = "fcm_message"
    var notilist = FcmService().fcmList

    inner class ViewHolder(val binding: ListMyrequestItemBinding) : RecyclerView.ViewHolder(binding.root) {
        private val img = itemView.findViewById<ImageView>(R.id.myrequest_img)
        private val text = itemView.findViewById<TextView>(R.id.myreques_text)
        private val button = itemView.findViewById<ImageButton>(R.id.my_request_button)
        fun bind(item: String) {
            img.setImageResource(R.drawable.newlogo)
            text.text = item
            button.setImageResource(R.drawable.ic_close_white)

            text.setOnClickListener {
                itemClickListener.onClick(text.text as String)
                if(text.text.contains("만들었습니다")){
                    notilist.remove(item)
                    writeSharedPreference("fcm", notilist)
                    notifyDataSetChanged()
                }
            }
            button.setOnClickListener {
                notilist.remove(item)
                writeSharedPreference("fcm", notilist)
                notifyDataSetChanged()
            }
        }
        private fun writeSharedPreference(key:String, value:ArrayList<String>){
            val sp = binding.root.context.getSharedPreferences(SP_NAME, FirebaseMessagingService.MODE_PRIVATE)
            val editor = sp.edit()
            val gson = Gson()
            val json: String = gson.toJson(value)
            editor.putString(key, json)
            editor.apply()
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NotiRecyclerViewAdapter.ViewHolder{
            val binding = ListMyrequestItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
            return ViewHolder(binding)
        }

    override fun onBindViewHolder(holder: NotiRecyclerViewAdapter.ViewHolder, position: Int) =
        holder.bind(notilist[position])

    override fun getItemCount(): Int = notilist.size

    interface ItemClickListener {
        fun onClick(text:String)
    }
}