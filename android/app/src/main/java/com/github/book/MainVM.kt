package com.github.book

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.book.entity.SeatBean
import org.jetbrains.annotations.TestOnly

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

    init {
        loadData()
    }

    fun getSeatListLD(): MutableLiveData<MutableList<SeatBean>> {
        return seatListLD
    }

    fun setSeatList(list: MutableList<SeatBean>) {
        seatListLD.value = list
    }

    @TestOnly
    private fun loadData() {
        val list = mutableListOf(
            SeatBean("1", "a", 1, true),
            SeatBean("1", "a", 2, false),
            SeatBean("1", "a", 3, false),
            SeatBean("1", "b", 1, false),
            SeatBean("2", "b", 4, false)
        )
        setSeatList(list)
    }
}