package com.github.book.entity

/**
 * description none
 * author ez_yang@qq.com
 * date 2021.12.2 下午 3:57
 * version 1.0
 * update none
 **/
data class RegisterRequest(
    val account: String,
    val name: String,
    val password: String
)
