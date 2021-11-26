package com.github.book.network

import okhttp3.*
import kotlin.concurrent.thread

/**
 * description none
 * author ez_yang@qq.com
 * date 2021.11.24 下午 4:14
 * version 1.0
 * update none
 **/
class RequestByOkhttp {

    fun post(ip: String, json: String, callback: Callback) {
        thread {
            val client = OkHttpClient()
            val requestBody = RequestBody.create(MediaType.get("application/json;charset=utf-8"), json)
            val request = Request.Builder()
                .url(ip)
                .post(requestBody)
                .build()
            client.newCall(request).enqueue(callback)
        }
    }

    fun get(ip: String, callback: Callback) {
        thread {
            val client = OkHttpClient()
            val request = Request.Builder()
                .url(ip)
                .build()
            client.newCall(request).enqueue(callback)
        }
    }
}