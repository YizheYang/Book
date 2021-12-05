package com.github.book.ui

import android.os.Handler
import android.os.Looper
import android.os.Message
import android.widget.Toast
import com.github.book.Constant
import com.github.book.entity.BookResponse
import com.github.book.entity.ChangeUsernameRequest
import com.github.book.entity.User
import com.github.book.network.RequestByOkhttp
import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Response

/**
 * description none
 * author ez_yang@qq.com
 * date 2021.12.1 下午 11:01
 * version 1.0
 * update none
 **/
class UsernameFragment : ChangeFragment() {
    override fun setHint() = "新用户名"

    private val handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            when (msg.what) {
                1 -> {
                    (requireActivity() as SettingActivity).apply {
                        user = User(user.id, user.account, et_new.text.toString(), user.password, user.timeId)
                    }
                    Toast.makeText(requireContext(), "用户名修改成功", Toast.LENGTH_SHORT).show()
                }
                2 -> Toast.makeText(requireContext(), "用户名修改失败", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun setListener() {
        super.setListener()
        btn_confirm.setOnClickListener {
            loading()
            val json = Gson().toJson(
                ChangeUsernameRequest(
                    (requireActivity() as SettingActivity).user.account,
                    et_new.text.toString()
                )
            )
            RequestByOkhttp().post(Constant.exusername, json, object : RequestByOkhttp.MyCallBack(requireContext()) {
                override fun onResponse(call: Call, response: Response) {
                    super.onResponse(call, response)
                    val myResponse = Gson().fromJson(response.body()?.string(), BookResponse::class.java)
                    if (myResponse.success) {
                        handler.sendEmptyMessage(1)
                    } else {
                        handler.sendEmptyMessage(2)
                    }
                    remove()
                }
            })
        }
    }
}