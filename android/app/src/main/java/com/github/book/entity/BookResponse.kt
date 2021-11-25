package com.github.book.entity

/**
 * description none
 * author ez_yang@qq.com
 * date 2021.11.25 下午 4:35
 * version 1.0
 * update none
 **/
data class BookResponse(
    val success: Boolean,
    val code: Int,
    val msg: String,
    val data: Any?
)
