package com.github.book.entity

import com.google.gson.annotations.SerializedName

/**
 * description none
 * author ez_yang@qq.com
 * date 2021.11.25 上午 11:19
 * version 1.0
 * update none
 **/
data class Status(
    val id: String,
    val status: Boolean,
    @SerializedName("sdate")
    val sDate: Long,
    @SerializedName("ddate")
    val dDate: Long,
    val userId: String,
    val statusId: String
)
