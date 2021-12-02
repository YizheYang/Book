package com.github.book.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
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

    private lateinit var btn_logout: Button
    private lateinit var btn_username: Button

    private lateinit var usernameFragment: UsernameFragment

    companion object {
        @JvmStatic
        fun startActivityForResult(context: Context, user: User) {
            val intent = Intent(context, SettingActivity::class.java)
            intent.putExtra("user", user)
            (context as AppCompatActivity).startActivityForResult(intent, 1)
        }
    }

    override fun getLayoutId() = R.layout.activity_setting

    override fun initProgressBar() = R.id.pgb_setting

    override fun initBackground() = R.id.view_setting_background

    override fun doubleReturn(): Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        btn_pwd = findViewById(R.id.btn_setting_pwd)
        btn_list = findViewById(R.id.btn_setting_list)
        btn_logout = findViewById(R.id.btn_setting_logout)
        btn_username = findViewById(R.id.btn_setting_username)
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

        btn_logout.setOnClickListener {
            setResult(RESULT_OK)
            finish()
        }

        btn_username.setOnClickListener {
            usernameFragment = UsernameFragment()
            supportFragmentManager.beginTransaction().replace(R.id.fragment_container_setting, usernameFragment)
                .commit()
        }
    }

    override fun onBackPressed() {
        val intent = Intent()
        intent.putExtra("user", viewModel.user)
        setResult(RESULT_CANCELED, intent)
        finish()
    }
}