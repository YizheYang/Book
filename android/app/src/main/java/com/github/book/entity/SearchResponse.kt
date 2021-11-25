package com.github.book.entity

/**
 * description none
 * author ez_yang@qq.com
 * date 2021.11.25 上午 12:35
 * version 1.0
 * update none
 **/
data class SearchResponse(
    val success: String,
    val code: String,
    val msg: String,
    val data: List<SearchResponseData>
)
