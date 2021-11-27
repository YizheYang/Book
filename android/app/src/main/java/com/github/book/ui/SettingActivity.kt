package com.github.book.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import com.github.book.MainVM
import com.github.book.R
import com.github.book.base.BaseActivity
import com.github.book.entity.User

/**
 * description none
 * author ez_yang@qq.com
 * date 2021.11.25 下午 10:37
 * version 1.0
 * update none
 **/
class SettingActivity : BaseActivity() {
    private lateinit var btn_pwd: Button
    private lateinit var btn_list: Button
    private lateinit var viewModel: MainVM

    private lateinit var pwdFragment: PwdFragment
    private lateinit var orderFragment: OrderFragment

    companion object {
        @JvmStatic
        fun startActivity(context: Context, user: User) {
            val intent = Intent(context, SettingActivity::class.java)
            intent.putExtra("user", user)
            context.startActivity(intent)
        }
    }

    override fun getLayoutId() = R.layout.activity_setting

    override fun doubleReturn(): Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        btn_pwd = findViewById(R.id.btn_setting_pwd)
        btn_list = findViewById(R.id.btn_setting_list)
    }

    override fun onStart() {
        super.onStart()
        viewModel = ViewModelProvider(this)[MainVM::class.java]
        viewModel.user = intent.extras?.get("user") as User
        viewModel.loadData()
        setListener()
    }

    private fun setListener() {
        btn_pwd.setOnClickListener {
            pwdFragment = PwdFragment()
            supportFragmentManager.beginTransaction().replace(R.id.fragment_container_setting, pwdFragment).commit()
        }

        btn_list.setOnClickListener {
            orderFragment = OrderFragment()
            supportFragmentManager.beginTransaction().replace(R.id.fragment_container_setting, orderFragment).commit()
        }
    }
}