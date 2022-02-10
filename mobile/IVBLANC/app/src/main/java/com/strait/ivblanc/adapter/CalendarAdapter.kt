package com.strait.ivblanc.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.strait.ivblanc.R
import com.strait.ivblanc.data.model.dto.DateWithHistory
import com.strait.ivblanc.databinding.ListHistoryCalendarItemBinding

class CalendarAdapter: RecyclerView.Adapter<CalendarAdapter.ViewHolder>() {
    var data: List<DateWithHistory> = emptyList()
    lateinit var binding: ListHistoryCalendarItemBinding
    lateinit var itemClickListener: ItemClickListener

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(item: DateWithHistory) {
            val textView = itemView.findViewById<TextView>(R.id.textView_historyCalendar_list_date)
            val imageView = itemView.findViewById<ImageView>(R.id.imageView_historyCalendar_list)
            when(item.date) {
                null -> textView.text = itemView.resources.getText(R.string.empty)
                else -> textView.text = item.date.dayOfMonth.toString()
            }
            // TODO: 2022/02/07 히스토리가 존재할 때 이미지 세팅
            if(item.history != null){
                Glide.with(imageView).load(item.history!!.styleUrl).into(imageView)
            }
            // TODO: 2022/02/07 히스토리가 존재할 때 이미지 세팅 
            // 히스토리가 존재할 때만 클릭리스너 동작
            item.history?.let {
                if(this@CalendarAdapter::itemClickListener.isInitialized) {
                    itemView.setOnClickListener {
                        itemClickListener.onClick(adapterPosition)
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_history_calendar_item, parent, false)
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