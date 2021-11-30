package com.github.book.entity

import androidx.room.*

/**
 * description none
 * author ez_yang@qq.com
 * date 2021.11.27 下午 5:43
 * version 1.0
 * update none
 **/
@Entity(tableName = "account")
data class AccountBean(
    @PrimaryKey
    val account: String,
    val password: String?,
    val lastTime: Long
)
