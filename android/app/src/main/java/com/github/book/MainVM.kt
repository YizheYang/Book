package com.github.book

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * description none
 * author ez_yang@qq.com
 * date 2021.11.2 上午 11:37
 * version 1.0
 * update none
 **/
class MainVM : ViewModel() {
    private val seatListLD = MutableLiveData<MutableList<SeatBean>>()

    fun getSeatListLD(): MutableLiveData<MutableList<SeatBean>> {
        return seatListLD
    }

    fun setSeatList(list: MutableList<SeatBean>) {
        seatListLD.value = list
    }
}