package com.github.book.entity

import java.io.Serializable

/**
 * description none
 * author ez_yang@qq.com
 * date 2021.11.24 下午 4:08
 * version 1.0
 * update none
 **/
data class User(
    val id: String,
    val account: String,
    val name: String,
    val password: String?,
    val timeId: String
) : Serializable
