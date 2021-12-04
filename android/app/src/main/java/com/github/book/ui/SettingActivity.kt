package com.github.book.ui

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.github.book.MainVM
import com.github.book.R
import com.github.book.base.BaseActivity
import com.github.book.entity.User
import java.io.File

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
    private lateinit var btn_logout: Button
    private lateinit var btn_username: Button
    private lateinit var btn_crash: Button
    private lateinit var viewModel: MainVM
    private lateinit var pwdFragment: PwdFragment
    private lateinit var orderFragment: OrderFragment
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
        btn_crash = findViewById(R.id.btn_setting_crashDir)
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
            setResult(RESULT_LOGOUT)
            finish()
        }

        btn_username.setOnClickListener {
            usernameFragment = UsernameFragment()
            supportFragmentManager.beginTransaction().replace(R.id.fragment_container_setting, usernameFragment)
                .commit()
        }

        btn_crash.setOnClickListener {
            val path = (ContextCompat.getExternalFilesDirs(this.applicationContext, null)[0].absolutePath
                    + "/crashLog/")
            val dir = File(path)
            if (!dir.exists()) {
                dir.mkdirs()
            }
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.setDataAndType(Uri.fromFile(dir), "text/plain")
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            intent.addFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED)
            try {
                startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                e.printStackTrace()
            }
            Toast.makeText(this, "路径是：$path", Toast.LENGTH_LONG).show()
        }
    }

    override fun onBackPressed() {
        val intent = Intent()
        intent.putExtra("user", viewModel.user)
        setResult(RESULT_CHANGEUSERNAME, intent)
        finish()
    }
}