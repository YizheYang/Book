package com.github.book.entity

/**
 * description none
 * author ez_yang@qq.com
 * date 2021.11.27 下午 1:35
 * version 1.0
 * update none
 **/
data class PwdResponse(
    val success: Boolean,
    val code: Int,
    val msg: String,
    val data: String
)
