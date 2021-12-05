package com.github.book

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.book.entity.*
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
    private val seatListLD = MutableLiveData<MutableList<SeatBean>>(mutableListOf())
    val floorList = mutableListOf<FloorBean>()
    var tempFloor = MutableLiveData<Int>()
    var tempArea = MutableLiveData<String>()
    lateinit var user: User

    private var mOnRequest: OnRequest? = null

    init {
        loadData()
    }

    fun getSeatListLD(): MutableLiveData<MutableList<SeatBean>> {
        return seatListLD
    }

    fun loadData() {
//        val list = mutableListOf<SeatBean>()
        RequestByOkhttp().get(Constant.searchArea, object : RequestByOkhttp.MyCallBack(null) {
            override fun onResponse(call: Call, response: Response) {
                val myResponse = Gson().fromJson(response.body()?.string(), SearchFloorResponse::class.java)
                if (myResponse.success) {
                    floorList.clear()
                    for (d in myResponse.data) {
                        floorList.add(d)
                    }
                    RequestByOkhttp().get(
                        "${Constant.search}/?floor=${tempFloor.value ?: floorList.first().floor}&area=${tempArea.value ?: floorList.first().areaList.first()}",
                        object : RequestByOkhttp.MyCallBack(null) {
                            override fun onResponse(call: Call, response: Response) {
                                val myResponse1 = Gson().fromJson(response.body()?.string(), SearchResponse::class.java)
                                if (myResponse1.success) {
                                    seatListLD.value?.clear()
                                    for (d in myResponse1.data) {
                                        d.apply {
                                            seatListLD.value?.add(SeatBean(id, floor, area, number, statusList))
                                        }
                                    }
                                    mOnRequest?.onFinish()
                                }
                            }
                        })
                }
            }
        })
//        seatListLD.value = list
    }

    interface OnRequest {
        fun onFinish()
    }

    fun setOnRequest(onRequest: OnRequest) {
        this.mOnRequest = onRequest
    }
}