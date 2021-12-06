package com.github.book.ui

import android.app.AlertDialog
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.github.book.Constant
import com.github.book.Constant.format12date
import com.github.book.R
import com.github.book.adapter.OrderAdapter
import com.github.book.base.BaseFragment
import com.github.book.entity.BookResponse
import com.github.book.entity.OrderBean
import com.github.book.entity.OrderRequest
import com.github.book.entity.OrderResponse
import com.github.book.network.RequestByOkhttp
import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Response
import java.text.SimpleDateFormat
import java.util.*

/**
 * description none
 * author ez_yang@qq.com
 * date 2021.11.25 下午 11:24
 * version 1.0
 * update none
 **/
class OrderFragment : BaseFragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: OrderAdapter
    private lateinit var background: View

    private var orderList = mutableListOf<OrderBean>()

    private val handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            when (msg.what) {
                0 -> adapter.notifyDataSetChanged()
                1 -> {
                    Toast.makeText(requireContext(), "退订成功", Toast.LENGTH_SHORT).show()
                    (requireActivity() as SettingActivity).unSubscribe = true
                }
                2 -> Toast.makeText(requireContext(), "找不到该订单", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun getLayoutId() = R.layout.fragment_order

    override fun initView(view: View, savedInstanceState: Bundle?) {
        recyclerView = view.findViewById(R.id.rv_list)
        background = view.findViewById(R.id.view_order_background)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapter = OrderAdapter(requireContext(), orderList)
        recyclerView.adapter = adapter
        refreshOrder()
        setListener()
        Toast.makeText(requireContext(), "单击订单即可唤起操作页", Toast.LENGTH_SHORT).show()
    }

    private fun setListener() {
        adapter.setOnChildrenClickListener(object : OrderAdapter.OnChildrenClickListener {
            override fun onItemClick(position: Int) {
                val seat = orderList[position]
                if (seat.sDate < SimpleDateFormat("yyyyMMddHHmm").format(Date(System.currentTimeMillis())).toLong()) {
                    Toast.makeText(requireContext(), "该预订已过期", Toast.LENGTH_SHORT).show()
                } else {
                    val builder = AlertDialog.Builder(requireContext())
                    builder.setTitle("退订")
                        .setMessage(
                            "楼层：${seat.floor}\n" +
                                    "区域：${seat.area}\n" +
                                    "座位号：${seat.no}\n" +
                                    "开始时间：${seat.sDate.toString().format12date()}\n" +
                                    "结束时间：${seat.dDate.toString().format12date()}\n" +
                                    "是否退订该座位"
                        )
                        .setNegativeButton("取消") { dialog, which -> dialog.dismiss() }
                        .setPositiveButton("确认") { dialog, which ->
                            loading()
                            val json =
                                Gson().toJson(OrderRequest(seat.id, (requireActivity() as SettingActivity).user.id))
                            RequestByOkhttp().post(
                                Constant.unsubscribe,
                                json,
                                object : RequestByOkhttp.MyCallBack(requireContext()) {
                                    override fun onResponse(call: Call, response: Response) {
                                        super.onResponse(call, response)
                                        val myResponse =
                                            Gson().fromJson(response.body()?.string(), BookResponse::class.java)
                                        handler.sendEmptyMessage(
                                            if (myResponse.success) {
                                                1
                                            } else {
                                                2
                                            }
                                        )
                                        refreshOrder()
                                    }
                                })
                        }
                    builder.create().show()
                }
            }
        })

        background.setOnClickListener {
            remove()
        }
    }

    private fun refreshOrder() {
        loading()
        RequestByOkhttp().get(
            "${Constant.searchWithUser}/${(requireActivity() as SettingActivity).user.id}",
            object : RequestByOkhttp.MyCallBack(requireContext()) {
                override fun onResponse(call: Call, response: Response) {
                    super.onResponse(call, response)
                    val myResponse = Gson().fromJson(response.body()?.string(), OrderResponse::class.java)
                    if (myResponse.success && myResponse.data.isNotEmpty()) {
                        orderList.clear()
                        orderList.addAll(myResponse.data.sortedBy { it.sDate }.toMutableList())
                        handler.sendEmptyMessage(0)
                    }
                }
            })
    }

    private fun remove() {
        requireActivity().supportFragmentManager.beginTransaction().remove(this).commit()
        this.onDestroy()
    }
}