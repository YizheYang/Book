package com.github.book.entity

/**
 * description none
 * author ez_yang@qq.com
 * date 2021.12.5 上午 1:55
 * version 1.0
 * update none
 **/
data class SearchFloorResponse(
    val success: Boolean,
    val code: Int,
    val msg: String,
    val data: List<FloorBean>
)
