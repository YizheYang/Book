package com.github.book

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.book.entity.SearchResponse
import com.github.book.entity.SeatBean
import com.github.book.entity.User
import com.github.book.network.RequestByOkhttp
import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Response

/**
 * description none
 * author ez_yang@qq.com
 * date 2021.11.2 上午 11:37
 * version 1.0
 * update none
 **/
class MainVM : ViewModel() {
    private val seatListLD = MutableLiveData<MutableList<SeatBean>>()

    var tempFloor = MutableLiveData<String>()
    var tempArea = MutableLiveData<String>()
    lateinit var user: User

    private var mOnRequest: OnRequest? = null

    init {
        loadData()
    }

    fun getSeatListLD(): MutableLiveData<MutableList<SeatBean>> {
        return seatListLD
    }

    private fun setSeatList(list: MutableList<SeatBean>) {
        seatListLD.value = list
    }

    fun loadData() {
        val list = mutableListOf<SeatBean>()
        RequestByOkhttp().get(Constant.search, object : RequestByOkhttp.MyCallBack(null) {
            override fun onResponse(call: Call, response: Response) {
                val myResponse = Gson().fromJson(response.body()?.string(), SearchResponse::class.java)
                for (d in myResponse.data) {
                    d.apply {
                        seatListLD.value?.add(SeatBean(id, floor, area, number, statusList))
                    }
                }
                mOnRequest?.onFinish()
            }
        })
        setSeatList(list)
    }

    interface OnRequest {
        fun onFinish()
    }

    fun setOnRequest(onRequest: OnRequest) {
        this.mOnRequest = onRequest
    }
}