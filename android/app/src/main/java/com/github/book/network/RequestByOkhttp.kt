package com.github.book.network

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.widget.Toast
import com.github.book.base.BaseActivity
import okhttp3.*
import java.io.IOException
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

    open class MyCallBack(private val context: Context?) : Callback {
        private val handler = object : Handler(Looper.getMainLooper()) {
            override fun handleMessage(msg: Message) {
                when (msg.what) {
                    1 -> {
                        try {
                            context?.let { (it as BaseActivity).stopLoading() }
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                    2 -> {
                        try {
                            Toast.makeText(context, "请求失败，请检查网络或者请求地址", Toast.LENGTH_SHORT).show()
                            context?.let { (it as BaseActivity).stopLoading() }
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                }
            }
        }

        override fun onFailure(call: Call, e: IOException) {
            context?.let {
                handler.sendEmptyMessage(2)
            }
        }

        override fun onResponse(call: Call, response: Response) {
            context?.let {
                handler.sendEmptyMessage(1)
            }
        }
    }
}