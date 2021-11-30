package com.github.book.entity

import com.google.gson.annotations.SerializedName

/**
 * description none
 * author ez_yang@qq.com
 * date 2021.11.26 下午 9:02
 * version 1.0
 * update none
 **/
data class OrderBean(
    val id: String,
    val floor: Int,
    val area: String,
    @SerializedName("number")
    val no: Int,
    @SerializedName("sdate")
    val sDate: Long,
    @SerializedName("ddate")
    val dDate: Long
)
