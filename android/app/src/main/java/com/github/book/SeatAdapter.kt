package com.github.book

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/**
 * description none
 * author ez_yang@qq.com
 * date 2021.11.2 上午 11:03
 * version 1.0
 * update none
 **/
class SeatAdapter(private val list: MutableList<SeatBean>) : RecyclerView.Adapter<SeatAdapter.SeatViewHolder>() {
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
        if (list[position].isBook) {
            holder.itemView.setBackgroundColor(Color.argb(127, 199, 84, 80))
            holder.itemView.isEnabled = false
        }
    }

    override fun getItemCount() = list.size

    interface OnChildrenClickListener {
        fun onSeatClickListener(holder: SeatViewHolder, position: Int)
    }

    fun setOnChildrenClickListener(onChildrenClickListener: OnChildrenClickListener) {
        mOnChildrenClickListener = onChildrenClickListener
    }
}