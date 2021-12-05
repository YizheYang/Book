package com.github.book.entity

import com.google.gson.annotations.SerializedName

/**
 * description none
 * author ez_yang@qq.com
 * date 2021.12.5 下午 3:16
 * version 1.0
 * update none
 **/
data class FloorBean(
    val floor: Int,
    @SerializedName("areas")
    val areaList: List<String>
)
