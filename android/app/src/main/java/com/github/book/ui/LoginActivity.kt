package com.github.book.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.room.Room
import com.github.book.Constant
import com.github.book.R
import com.github.book.base.BaseActivity
import com.github.book.database.AccountDatabase
import com.github.book.entity.AccountBean
import com.github.book.entity.LoginRequest
import com.github.book.entity.LoginResponse
import com.github.book.entity.User
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
    private lateinit var et_account: EditText
    private lateinit var et_password: EditText
    private lateinit var btn_login: Button
    private lateinit var btn_signin: Button
    private lateinit var cb_account: CheckBox
    private lateinit var cb_password: CheckBox

    private lateinit var database: AccountDatabase

    companion object{
        @JvmStatic
        fun startActivity(context: Context) {
            val intent = Intent(context, LoginActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun getLayoutId() = R.layout.activity_login

    override fun doubleReturn(): Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        et_account = findViewById(R.id.et_account)
        et_password = findViewById(R.id.et_password)
        btn_login = findViewById(R.id.btn_login)
        btn_signin = findViewById(R.id.btn_signin)
        cb_account = findViewById(R.id.cb_rememberAccount)
        cb_password = findViewById(R.id.cb_rememberPassword)
    }

    override fun onStart() {
        super.onStart()
        database = Room.databaseBuilder(this, AccountDatabase::class.java, "book.db")
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
        database.getAccountDao().getLastAccount()?.let { accountBean ->
            et_account.setText(accountBean.account)
            cb_account.isChecked = true
            accountBean.password?.let {
                et_password.setText(it)
                cb_password.isChecked = true
            }
        }
        setListener()
    }

    private fun setListener() {
        btn_login.setOnClickListener {
            val username = et_account.text.toString()
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
                        val accountBean = AccountBean(
                            et_account.text.toString(),
                            if (cb_password.isChecked) {
                                et_password.text.toString()
                            } else {
                                null
                            },
                            System.currentTimeMillis()
                        )
                        if (cb_account.isChecked) {
                            database.getAccountDao().apply {
                                if (getAccount(et_account.text.toString()) == null) {
                                    insertAccount(accountBean)
                                } else {
                                    updateAccount(accountBean)
                                }
                            }
                        } else {
                            database.getAccountDao().apply {
                                if (getAccount(et_account.text.toString()) != null) {
                                    deleteAccount(accountBean)
                                }
                            }
                        }
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