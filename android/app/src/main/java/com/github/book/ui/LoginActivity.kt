package com.github.book.ui

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.github.book.R
import com.github.book.base.BaseActivity
import com.github.book.entity.LoginRequest
import com.github.book.entity.LoginResponse
import com.github.book.network.RequestByOkhttp
import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException

/**
 * description none
 * author ez_yang@qq.com
 * date 2021.11.1 下午 11:11
 * version 1.0
 * update none
 **/
class LoginActivity : BaseActivity() {
    private lateinit var et_username: EditText
    private lateinit var et_password: EditText
    private lateinit var btn_login: Button
    private lateinit var btn_signin: Button

    override fun getLayoutId() = R.layout.activity_login

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        et_username = findViewById(R.id.et_username)
        et_password = findViewById(R.id.et_password)
        btn_login = findViewById(R.id.btn_login)
        btn_signin = findViewById(R.id.btn_signin)
    }

    override fun onStart() {
        super.onStart()
        setListener()
    }

    private fun setListener() {
        btn_login.setOnClickListener {
            val username = et_username.text.toString()
            val password = et_password.text.toString()
            request(username, password)
        }

        btn_signin.setOnClickListener {
            Toast.makeText(this, "sign in", Toast.LENGTH_SHORT).show()
        }
    }

    private fun request(username: String, password: String) {
        RequestByOkhttp().post(
            "http://47.106.89.121:8080/login",
            Gson().toJson(LoginRequest(username, password)),
            object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    runOnUiThread {
                        Toast.makeText(this@LoginActivity, "请求失败，请检查网络或者请求地址", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onResponse(call: Call, response: Response) {
                    val myResponse = Gson().fromJson(response.body()?.string(), LoginResponse::class.java)
                    runOnUiThread {
                        if (myResponse?.success == "true") {
                            MainActivity.startActivity(this@LoginActivity, username)
                            Toast.makeText(this@LoginActivity, "登录成功", Toast.LENGTH_SHORT).show()
                            finish()
                        } else {
                            Toast.makeText(this@LoginActivity, "账号或密码错误", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            })
    }

}