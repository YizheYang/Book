package com.github.book.entity

import com.google.gson.annotations.SerializedName

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
)
