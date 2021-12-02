package com.github.book.entity

/**
 * description none
 * author ez_yang@qq.com
 * date 2021.12.2 下午 4:18
 * version 1.0
 * update none
 **/
data class ChangeUsernameRequest(
    val account: String,
    val newName: String
)
