package com.github.book.entity

/**
 * description none
 * author ez_yang@qq.com
 * date 2021.11.24 下午 3:52
 * version 1.0
 * update none
 **/
data class LoginResponse(
    val success: Boolean,
    val code: Int,
    val msg: String,
    val data: User
)
