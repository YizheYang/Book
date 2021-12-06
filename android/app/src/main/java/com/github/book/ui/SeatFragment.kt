package com.github.book.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.text.method.ScrollingMovementMethod
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.github.book.Constant
import com.github.book.Constant.format12date
import com.github.book.MainVM
import com.github.book.R
import com.github.book.base.BaseFragment
import com.github.book.entity.BookRequest
import com.github.book.entity.BookResponse
import com.github.book.entity.SeatBean
import com.github.book.network.RequestByOkhttp
import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Response
import java.text.SimpleDateFormat
import java.util.*

/**
 * description none
 * author ez_yang@qq.com
 * date 2021.11.25 下午 2:18
 * version 1.0
 * update none
 **/
class SeatFragment(private val seat: SeatBean) : BaseFragment() {
    private lateinit var background: View
    private lateinit var id: TextView
    private lateinit var status: TextView
    private lateinit var timeTitle: TextView
    private lateinit var time: TextView
    private lateinit var btn_cancel: Button
    private lateinit var btn_book: Button
    private lateinit var btn_bookNow: Button

    private lateinit var viewModel: MainVM

    private val handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                1 -> {
                    val date = SimpleDateFormat("yyyyMMddHH").format(Date(System.currentTimeMillis())).toLong()
                    val start = (date.toString() + "00").format12date()
                    val end = ((date + 1).toString() + "00").format12date()
                    Toast.makeText(requireContext(), "预订成功，时间为$start--$end", Toast.LENGTH_SHORT).show()
                    remove()
                }
                2 -> Toast.makeText(requireContext(), "预订失败，当前时间段已有预订座位", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun getLayoutId() = R.layout.fragment_seat

    override fun initView(view: View, savedInstanceState: Bundle?) {
        background = view.findViewById(R.id.view_seat_background)
        id = view.findViewById(R.id.tv_seat_id)
        status = view.findViewById(R.id.tv_seat_status)
        timeTitle = view.findViewById(R.id.tv_seat_bookedTimeTitle)
        time = view.findViewById(R.id.tv_seat_bookedTime)
        btn_cancel = view.findViewById(R.id.btn_seat_cancel)
        btn_book = view.findViewById(R.id.btn_seat_book)
        btn_bookNow = view.findViewById(R.id.btn_seat_bookNow)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(requireActivity())[MainVM::class.java]
        id.text = "座位编号：${seat.no}"
        time.movementMethod = ScrollingMovementMethod.getInstance()
        if (seat.isBook()) {
            status.text = "状态：无空位"
            time.text = seat.getBookedDate()
        } else {
            status.text = "状态：有空位"
            timeTitle.visibility = View.GONE
            time.visibility = View.GONE
        }
        setListener()
    }

    private fun setListener() {
        background.setOnClickListener {
            remove()
        }

        btn_cancel.setOnClickListener {
            remove()
        }

        btn_book.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container_main, BookFragment(seat)).commit()
            remove()
        }

        btn_bookNow.setOnClickListener {
            val date = SimpleDateFormat("yyyyMMddHH").format(Date(System.currentTimeMillis())).toLong()
//                .replace(Regex("(?<=[0-9]{8})[0-9]{2}(?=[0-9]{2}\$)"), "00")
            if (date.toString().substring(8, 10).toInt() in 8..22) {
                loading()
                val json = Gson().toJson(
                    BookRequest(
                        seat.id,
                        viewModel.user.id,
                        (date.toString() + "00").toLong(),
                        ((date + 1).toString() + "00").toLong()
                    )
                )
                RequestByOkhttp().post(Constant.reserve, json, object : RequestByOkhttp.MyCallBack(requireContext()) {
                    override fun onResponse(call: Call, response: Response) {
                        super.onResponse(call, response)
                        val myResponse = Gson().fromJson(response.body()?.string(), BookResponse::class.java)
                        if (myResponse.success) {
                            handler.sendEmptyMessage(1)
                        } else {
                            handler.sendEmptyMessage(2)
                        }
                    }
                })
                viewModel.loadData()
            }
        }
    }

    private fun remove() {
        requireActivity().supportFragmentManager.beginTransaction().remove(this).commit()
        this.onDestroy()
    }
}