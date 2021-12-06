package com.github.book.entity

import com.github.book.Constant.format12date
import com.github.book.Constant.mergeTime
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
    val id: String,
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

    fun getBookedDateList(): List<Array<Long>> {
        val date = SimpleDateFormat("yyyyMMddHHmm").format(Date(System.currentTimeMillis())).toLong()
        val list = mutableListOf<Array<Long>>()
        for (status in statusList) {
            if (date < status.dDate) {
                list.add(arrayOf(status.sDate, status.dDate))
            }
        }
        return list.mergeTime()
    }

    fun getBookedDate(): String {
        val sb = StringBuilder()
        for (date in getBookedDateList()) {
            sb.append(date[0].toString().format12date())
                .append("--")
                .append(date[1].toString().format12date())
                .append("\n")
        }
        sb.deleteAt(sb.length - 1)
        return sb.toString()
    }
}
