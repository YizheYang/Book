package com.github.book.ui

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.github.book.Constant
import com.github.book.R
import com.github.book.base.BaseActivity
import com.github.book.entity.LoginRequest
import com.github.book.entity.LoginResponse
import com.github.book.network.RequestByOkhttp
import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Response

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

    override fun doubleReturn(): Boolean = true

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
        val json = Gson().toJson(LoginRequest(username, password))
        RequestByOkhttp().post(Constant.login, json, object : RequestByOkhttp.MyCallBack(this) {
            override fun onResponse(call: Call, response: Response) {
                val myResponse = Gson().fromJson(response.body()?.string(), LoginResponse::class.java)
                runOnUiThread {
                    if (myResponse?.success == true) {
                        MainActivity.startActivity(this@LoginActivity, myResponse.data)
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