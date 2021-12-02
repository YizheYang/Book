package com.github.book.ui

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.room.Room
import com.github.book.Constant
import com.github.book.R
import com.github.book.base.BaseActivity
import com.github.book.database.AccountDatabase
import com.github.book.entity.AccountBean
import com.github.book.entity.LoginRequest
import com.github.book.entity.LoginResponse
import com.github.book.entity.RegisterRequest
import com.github.book.network.RequestByOkhttp
import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Response
import java.io.IOException
import java.util.*

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
    private lateinit var ib_list: ImageButton

    private lateinit var database: AccountDatabase
    private var listPopupWindow: ListPopupWindow? = null
    private lateinit var inputMethodManager: InputMethodManager

    companion object {
        @JvmStatic
        fun startActivity(context: Context) {
            val intent = Intent(context, LoginActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun getLayoutId() = R.layout.activity_login

    override fun initProgressBar() = R.id.pgb_login

    override fun initBackground() = R.id.view_login_background

    override fun doubleReturn(): Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        et_account = findViewById(R.id.et_account)
        et_password = findViewById(R.id.et_password)
        btn_login = findViewById(R.id.btn_login)
        btn_signin = findViewById(R.id.btn_signin)
        cb_account = findViewById(R.id.cb_rememberAccount)
        cb_password = findViewById(R.id.cb_rememberPassword)
        ib_list = findViewById(R.id.ib_accountList)
        inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStart() {
        super.onStart()
        setAutoFillHint()
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
            loading()
            val username = et_account.text.toString()
            val password = et_password.text.toString()
            request(username, password)
        }

        btn_signin.setOnClickListener {
            loading()
            val account = et_account.text.toString()
            val password = et_password.text.toString()
            register(account, "newUser", password)
        }

        ib_list.setOnClickListener {
            val list = database.getAccountDao().getAllAccount()
            list?.let {
                if (listPopupWindow == null) {
                    listPopupWindow = ListPopupWindow(this)
                    show(it)
                    inputMethodManager.hideSoftInputFromWindow(
                        et_account.windowToken,
                        InputMethodManager.HIDE_NOT_ALWAYS
                    )
                } else {
                    close()
                }
            }
        }
    }

    private fun show(list: List<AccountBean>) {
        val adapter = SimpleAdapter(
            this,
            getStandardList(list),
            R.layout.item_listpopupwindow,
            arrayOf("object"),
            intArrayOf(R.id.tv_listPopupWindow)
        )
        listPopupWindow?.setAdapter(adapter)
        listPopupWindow?.anchorView = et_account
        listPopupWindow?.setOnItemClickListener { parent, view, position, id ->
            et_account.setText(list[position].account)
            cb_account.isChecked = true
            et_password.setText(list[position].password)
            cb_password.isChecked = list[position].password != null
            close()
        }
        listPopupWindow?.show()
    }

    private fun close() {
        listPopupWindow?.dismiss()
        listPopupWindow = null
    }

    private fun getStandardList(l: List<AccountBean>): List<Map<String, Any>> {
        val list = mutableListOf<Map<String, Any>>()
        var map: MutableMap<String, Any>
        for (i in l.indices) {
            map = HashMap()
            map["object"] = l[i].account
            list.add(map)
        }
        return list
    }

    private fun request(username: String, password: String) {
        val json = Gson().toJson(LoginRequest(username, password))
        RequestByOkhttp().post(Constant.login, json, object : RequestByOkhttp.MyCallBack(this) {
            override fun onFailure(call: Call, e: IOException) {
                stopLoading()
                super.onFailure(call, e)
            }

            override fun onResponse(call: Call, response: Response) {
                stopLoading()
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

    private fun register(account: String, username: String, password: String) {
        val json = Gson().toJson(RegisterRequest(account, username, password))
        RequestByOkhttp().post(Constant.register, json, object : RequestByOkhttp.MyCallBack(this) {
            override fun onFailure(call: Call, e: IOException) {
                stopLoading()
                super.onFailure(call, e)
            }

            override fun onResponse(call: Call, response: Response) {
                stopLoading()
                val myResponse = Gson().fromJson(response.body()?.string(), LoginResponse::class.java)
                runOnUiThread {
                    if (myResponse?.success == true) {
                        if (cb_account.isChecked) {
                            val accountBean = AccountBean(
                                et_account.text.toString(),
                                if (cb_password.isChecked) {
                                    et_password.text.toString()
                                } else {
                                    null
                                },
                                System.currentTimeMillis()
                            )
                            database.getAccountDao().insertAccount(accountBean)
                        }
                        MainActivity.startActivity(this@LoginActivity, myResponse.data)
                        Toast.makeText(this@LoginActivity, "注册成功", Toast.LENGTH_SHORT).show()
                        finish()
                    } else {
                        Toast.makeText(this@LoginActivity, "注册失败，账号已经存在", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setAutoFillHint() {
        et_account.setAutofillHints(View.AUTOFILL_HINT_USERNAME)
        et_password.setAutofillHints(View.AUTOFILL_HINT_PASSWORD)
    }

}