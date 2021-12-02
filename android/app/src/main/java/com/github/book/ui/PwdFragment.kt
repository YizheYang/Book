package com.github.book.ui

import android.os.Handler
import android.os.Looper
import android.os.Message
import android.widget.Toast
import com.github.book.Constant
import com.github.book.entity.LoginRequest
import com.github.book.entity.PwdResponse
import com.github.book.network.RequestByOkhttp
import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Response

/**
 * description none
 * author ez_yang@qq.com
 * date 2021.12.1 下午 10:50
 * version 1.0
 * update none
 **/
class PwdFragment : ChangeFragment() {
    private val handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            when (msg.what) {
                1 -> {
                    Toast.makeText(requireContext(), "密码修改成功", Toast.LENGTH_SHORT).show()
                    remove()
                }
            }
        }
    }

    override fun setHint() = "新密码"

    override fun setListener() {
        super.setListener()
        btn_confirm.setOnClickListener {
            val json = Gson().toJson(LoginRequest(viewModel.user.account, et_new.text.toString()))
            RequestByOkhttp().post(Constant.expassword, json, object : RequestByOkhttp.MyCallBack(requireContext()) {
                override fun onResponse(call: Call, response: Response) {
                    val myResponse = Gson().fromJson(response.body()?.string(), PwdResponse::class.java)
                    if (myResponse.success) {
                        handler.sendEmptyMessage(1)
                    }
                }
            })
        }
    }
}