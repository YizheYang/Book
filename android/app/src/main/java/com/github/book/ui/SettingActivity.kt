package com.github.book.ui

import android.content.*
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
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
    private lateinit var pwdFragment: PwdFragment
    private lateinit var orderFragment: OrderFragment
    private lateinit var usernameFragment: UsernameFragment
    lateinit var user: User
    var changedUsername = false
    var unSubscribe = false

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
        user = intent.extras?.get("user") as User
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
            Toast.makeText(this, "如果没到达指定路径请手动进入（大概率需要手动）", Toast.LENGTH_SHORT).show()
            Toast.makeText(this, "路径：$path", Toast.LENGTH_LONG).show()
            (getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager).setPrimaryClip(
                ClipData.newPlainText("text", path)
            )
        }
    }

    override fun onBackPressed() {
        when {
            changedUsername -> {
                val intent = Intent()
                intent.putExtra("user", user)
                setResult(RESULT_CHANGEUSERNAME, intent)
                finish()
            }
            unSubscribe -> {
                setResult(RESULT_UNSUBSCRIBE)
                finish()
            }
            else -> {
                super.onBackPressed()
            }
        }
    }
}