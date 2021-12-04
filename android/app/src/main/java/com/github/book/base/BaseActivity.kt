package com.github.book.base

import android.Manifest
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.VmPolicy
import android.provider.Settings
import android.view.View
import android.view.WindowInsetsController
import android.widget.ProgressBar
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.github.book.MyUncaughtExceptionHandler


/**
 * description none
 * author ez_yang@qq.com
 * date 2021.11.1 下午 10:59
 * version 1.0
 * update none
 **/
abstract class BaseActivity : AppCompatActivity() {
    private var firstBackPress = 0L
    private val backPressTime = 1000L

    private lateinit var progressBar: ProgressBar
    private lateinit var background: View

    private val permissions = listOf(
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE
    )

    val RESULT_LOGOUT = 1
    val RESULT_CHANGEUSERNAME = 2
    val RESULT_OPENCRASHDIR = 3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkAndApplyPermissions()
        // 判断是不是release版本
        if (applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE == 0) {
            Thread.setDefaultUncaughtExceptionHandler(MyUncaughtExceptionHandler(this))
        }
        setContentView(getLayoutId())
        autoAdjustStatusBarText()
        val builder = VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            builder.detectFileUriExposure()
        }
        try {
            progressBar = findViewById(initProgressBar())
            background = findViewById(initBackground())
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @LayoutRes
    abstract fun getLayoutId(): Int

    open fun initProgressBar(): Int {
        return 0
    }

    open fun initBackground(): Int {
        return 0
    }

    abstract fun doubleReturn(): Boolean

    fun loading() {
        runOnUiThread {
            progressBar.visibility = View.VISIBLE
            background.visibility = View.VISIBLE
        }
    }

    fun stopLoading() {
        runOnUiThread {
            progressBar.visibility = View.GONE
            background.visibility = View.GONE
        }
    }

    private fun checkAndApplyPermissions() {
        for (permission in permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, permissions.toTypedArray(), 0)
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            0 -> {
                if (grantResults.isNotEmpty()) {
                    for ((i, result) in grantResults.withIndex()) {
                        if (result != PackageManager.PERMISSION_GRANTED) {
                            if (!ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[i])) {
                                val builder = AlertDialog.Builder(this)
                                builder.setTitle("permission")
                                    .setMessage("点击允许才可以使用我们的app哦")
                                    .setPositiveButton("去允许") { dialog: DialogInterface?, id: Int ->
                                        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                                        val uri = Uri.fromParts("package", packageName, null)
                                        intent.data = uri
                                        startActivityForResult(intent, 0)
                                        dialog?.dismiss()
                                    }
                                val mDialog: AlertDialog = builder.create()
                                mDialog.setCanceledOnTouchOutside(false)
                                mDialog.show()
                            } else {
                                checkAndApplyPermissions()
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            0 -> {
                checkAndApplyPermissions()
            }
        }
    }

    private fun autoAdjustStatusBarText() {
        //手机为浅色模主题时，状态栏字体颜色设为黑色，由于状态栏字体颜色默认为白色，所以深色主题不需要适配
        if (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK === Configuration.UI_MODE_NIGHT_NO) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                window.decorView.windowInsetsController!!.setSystemBarsAppearance(
                    WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
                    WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
                )
            } else {
                window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            }
        }
    }

    override fun onBackPressed() {
        if (doubleReturn()) {
            val secondBackPress = System.currentTimeMillis()
            if (secondBackPress - firstBackPress > backPressTime) {
                Toast.makeText(this, "再按一次退出应用", Toast.LENGTH_SHORT).show()
                firstBackPress = secondBackPress
                return
            }
            moveTaskToBack(true)
        } else {
            super.onBackPressed()
        }
    }
}