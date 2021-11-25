package com.github.book.entity

import com.google.gson.annotations.SerializedName
import java.text.SimpleDateFormat
import java.util.*

/**
 * description none
 * author ez_yang@qq.com
 * date 2021.11.2 上午 11:01
 * version 1.0
 * update none
 **/
data class SeatBean(
//    @Expose(deserialize = false)
    val id: Int,
//    @Transient
    val floor: Int,
    val area: String,
    @SerializedName("number")
    val no: Int,
    val statusList: List<Status>
) {
    fun isBook(): Boolean {
        val date = SimpleDateFormat("yyyyMMddHHmm").format(Date(System.currentTimeMillis())).toLong()
        for (d in statusList) {
            if (date in d.sDate..d.dDate) {
                return true
            }
        }
        return false
    }

    fun getBookedSDate(): String {
        val date = SimpleDateFormat("yyyyMMddHHmm").format(Date(System.currentTimeMillis())).toLong()
        val list = mutableListOf<Long>()
        for (d in statusList) {
            if (date in d.sDate..d.dDate) {
                list.add(d.sDate)
            }
        }
        val sb = StringBuilder()
        for (s in list.map { it.toString().substring(8, 10) }) {
            sb.append(s).append(",")
        }
        sb.deleteCharAt(sb.length - 1)
        return sb.toString()
    }

    fun getBookedDDate(): String {
        val date = SimpleDateFormat("yyyyMMddHHmm").format(Date(System.currentTimeMillis())).toLong()
        val list = mutableListOf<Long>()
        for (d in statusList) {
            if (date in d.sDate..d.dDate) {
                list.add(d.dDate)
            }
        }
        val sb = StringBuilder()
        for (s in list.map { it.toString().substring(8, 10) }) {
            sb.append(s).append(",")
        }
        sb.deleteCharAt(sb.length - 1)
        return sb.toString()
    }
}
