package com.github.book.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.github.book.Constant
import com.github.book.MainVM
import com.github.book.R
import com.github.book.base.BaseFragment
import com.github.book.entity.BookRequest
import com.github.book.entity.BookResponse
import com.github.book.entity.SeatBean
import com.github.book.network.RequestByOkhttp
import com.github.book.widget.ComboBox
import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Response
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

/**
 * description none
 * author ez_yang@qq.com
 * date 2021.11.25 下午 3:45
 * version 1.0
 * update none
 **/
class BookFragment(private val seat: SeatBean) : BaseFragment() {
    private lateinit var background: View
    private lateinit var title: TextView
    private lateinit var comboBox: ComboBox
    private lateinit var btn_cancel: Button
    private lateinit var btn_confirm: Button

    private lateinit var viewModel: MainVM
    private var date = 0L
    private var time = 0

    private val handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            when (msg.what) {
                1 -> Toast.makeText(requireContext(), "预订成功", Toast.LENGTH_SHORT).show()
                2 -> Toast.makeText(requireContext(), "请求失败，可能是没位置了", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun getLayoutId() = R.layout.fragment_book

    override fun initView(view: View, savedInstanceState: Bundle?) {
        background = view.findViewById(R.id.view_book_background)
        title = view.findViewById(R.id.tv_book_range)
        comboBox = view.findViewById(R.id.cbb_book_choose)
        btn_cancel = view.findViewById(R.id.btn_book_cancel)
        btn_confirm = view.findViewById(R.id.btn_book_confirm)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(requireActivity())[MainVM::class.java]
        date = SimpleDateFormat("yyyyMMdd").format(Date(System.currentTimeMillis())).toLong()
        title.text = "预订的范围是${date + 1L}"
        comboBox.setList(getFreeTime().map { it.toString() })
        comboBox.setItem("")
        comboBox.setDescription("时")
        setListener()
    }

    private fun setListener() {
        comboBox.setMyClick(object : ComboBox.OnMyClick {
            override fun onClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long, list: List<String>) {
                time = list[position].toInt()
            }

        })

        btn_confirm.setOnClickListener {
            if (time in 8..22) {
                loading()
                val json = Gson().toJson(
                    BookRequest(
                        seat.id,
                        viewModel.user.id,
                        ((date + 1).toString() + formatTime(time) + "00").toLong(),
                        ((date + 1).toString() + formatTime(time + 1) + "00").toLong()
                    )
                )
                RequestByOkhttp().post(Constant.reserve, json, object : RequestByOkhttp.MyCallBack(requireContext()) {
                    override fun onResponse(call: Call, response: Response) {
                        super.onResponse(call, response)
                        val myResponse = Gson().fromJson(response.body()?.string(), BookResponse::class.java)
                        if (myResponse.success) {
                            handler.sendEmptyMessage(1)
                            remove()
                        } else {
                            handler.sendEmptyMessage(2)
                        }
                    }
                })
                viewModel.loadData()
            } else {
                Toast.makeText(requireContext(), "选择时间出错，请重新选择", Toast.LENGTH_SHORT).show()
            }
        }

        background.setOnClickListener {
            remove()
        }

        btn_cancel.setOnClickListener {
            remove()
        }
    }

    private fun formatTime(time: Int): String {
        var result = ""
        result += if (time < 10) {
            "0$time"
        } else {
            time.toString()
        }
        return result
    }

    private fun getFreeTime(): List<Int> {
        val all = mutableListOf<Int>()
        for (i in 8..22) {
            all.add(i)
        }
        val booked = mutableListOf<Int>()
        for (s in seat.statusList) {
            if (s.sDate.toString().substring(0, 8) == (date + 1L).toString())
                booked.add(s.sDate.toString().substring(8, 10).toInt())
        }
        return all - booked
    }

    private fun remove() {
        requireActivity().supportFragmentManager.beginTransaction().remove(this).commit()
        this.onDestroy()
    }
}