package com.github.book.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.github.book.R
import com.github.book.entity.SeatBean
import java.text.SimpleDateFormat
import java.util.*

/**
 * description none
 * author ez_yang@qq.com
 * date 2021.11.2 上午 11:03
 * version 1.0
 * update none
 **/
class SeatAdapter(private var list: MutableList<SeatBean>) : RecyclerView.Adapter<SeatAdapter.SeatViewHolder>() {
    private var mOnChildrenClickListener: OnChildrenClickListener? = null

    inner class SeatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val no: TextView = itemView.findViewById(R.id.tv_item_no)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeatViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_seat, parent, false)
        val holder = SeatViewHolder(view)
        holder.itemView.setOnClickListener {
            mOnChildrenClickListener?.onSeatClickListener(holder, holder.adapterPosition)
        }
        return holder
    }

    override fun onBindViewHolder(holder: SeatViewHolder, position: Int) {
        holder.no.text = list[position].no.toString()
        val date = SimpleDateFormat("yyyyMMddHHmm").format(Date(System.currentTimeMillis())).toLong()
        for (d in list[position].statusList) {
            if (date in d.sDate..d.dDate) {
                holder.no.setBackgroundColor(Color.argb(127, 199, 84, 80))
                holder.itemView.isEnabled = false
                return
            }
        }
    }

    override fun getItemCount() = list.size

    interface OnChildrenClickListener {
        fun onSeatClickListener(holder: SeatViewHolder, position: Int)
    }

    fun setOnChildrenClickListener(onChildrenClickListener: OnChildrenClickListener) {
        mOnChildrenClickListener = onChildrenClickListener
    }

    fun setList(list: MutableList<SeatBean>) {
        this.list = list
    }
}