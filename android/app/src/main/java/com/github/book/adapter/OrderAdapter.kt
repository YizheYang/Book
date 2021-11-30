package com.github.book.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.github.book.R
import com.github.book.entity.OrderBean

/**
 * description none
 * author ez_yang@qq.com
 * date 2021.11.25 下午 11:27
 * version 1.0
 * update none
 **/
class OrderAdapter(private var list: MutableList<OrderBean>) : RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {

    private var mOnChildrenClickListener: OnChildrenClickListener? = null

    inner class OrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val no: TextView = itemView.findViewById(R.id.tv_item_order_index)
        val time: TextView = itemView.findViewById(R.id.tv_item_order_time)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_order, parent, false)
        val holder = OrderViewHolder(view)
        holder.itemView.setOnClickListener {
            mOnChildrenClickListener?.onItemClick(holder.adapterPosition)
        }
        return holder
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        holder.no.text = (position + 1).toString()
        holder.time.text = "${list[position].sDate}——${list[position].dDate}"
    }

    override fun getItemCount() = list.size

    interface OnChildrenClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnChildrenClickListener(onChildrenClickListener: OnChildrenClickListener) {
        this.mOnChildrenClickListener = onChildrenClickListener
    }
}
