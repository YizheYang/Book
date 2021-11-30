package com.github.book.base

import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsetsController
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        autoAdjustStatusBarText()
    }

    @LayoutRes
    abstract fun getLayoutId(): Int

    abstract fun doubleReturn(): Boolean

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