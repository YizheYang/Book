package com.github.book.ui

import android.widget.Toast

/**
 * description none
 * author ez_yang@qq.com
 * date 2021.12.1 下午 11:01
 * version 1.0
 * update none
 **/
class UsernameFragment : ChangeFragment() {
    override fun setHint() = "新用户名"

    override fun setListener() {
        super.setListener()
        btn_confirm.setOnClickListener {
            Toast.makeText(requireContext(), "confirm", Toast.LENGTH_SHORT).show()
        }
    }
}