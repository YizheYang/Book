package com.github.book.entity

/**
 * description none
 * author ez_yang@qq.com
 * date 2021.11.26 下午 8:51
 * version 1.0
 * update none
 **/
data class OrderResponse(
    val success: Boolean,
    val code: Int,
    val msg: String,
    val data: List<OrderBean>
)
