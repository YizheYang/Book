package com.github.book.entity

/**
 * description none
 * author ez_yang@qq.com
 * date 2021.11.25 上午 12:32
 * version 1.0
 * update none
 **/
data class SearchResponseData(
    val id: String,
    val floor: Int,
    val area: String,
    val number: Int,
    val statusList: List<Status>,
)
