package com.github.book.entity

import com.google.gson.annotations.SerializedName

/**
 * description none
 * author ez_yang@qq.com
 * date 2021.11.25 下午 4:20
 * version 1.0
 * update none
 **/
data class BookRequest(
    val libraryId: String,
    val userId: String,
    @SerializedName("sdate")
    val sDate: Long,
    @SerializedName("ddate")
    val dDate: Long
)
