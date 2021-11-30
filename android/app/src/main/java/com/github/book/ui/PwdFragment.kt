package com.github.book.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.github.book.Constant
import com.github.book.MainVM
import com.github.book.R
import com.github.book.base.BaseFragment
import com.github.book.entity.LoginRequest
import com.github.book.entity.PwdResponse
import com.github.book.network.RequestByOkhttp
import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Response

/**
 * description none
 * author ez_yang@qq.com
 * date 2021.11.25 下午 10:54
 * version 1.0
 * update none
 **/
class PwdFragment : BaseFragment() {
    private lateinit var et_new: EditText
    private lateinit var btn_cancel: Button
    private lateinit var btn_confirm: Button
    private lateinit var background: View

    private lateinit var viewModel: MainVM

    private val handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            when (msg.what) {
                1 -> {
                    Toast.makeText(requireContext(), "密码修改成功", Toast.LENGTH_SHORT).show()
                    remove()
                }
            }
        }
    }

    override fun getLayoutId() = R.layout.fragment_pwd

    override fun initView(view: View, savedInstanceState: Bundle?) {
        et_new = view.findViewById(R.id.et_pwd_new)
        btn_cancel = view.findViewById(R.id.btn_pwd_cancel)
        btn_confirm = view.findViewById(R.id.btn_pwd_confirm)
        background = view.findViewById(R.id.view_pwd_background)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(requireActivity())[MainVM::class.java]
        setListener()
    }

    private fun setListener() {
        btn_confirm.setOnClickListener {
            val json = Gson().toJson(LoginRequest(viewModel.user.account, et_new.text.toString()))
            RequestByOkhttp().post(Constant.expassword, json, object : RequestByOkhttp.MyCallBack(requireContext()) {
                override fun onResponse(call: Call, response: Response) {
                    val myResponse = Gson().fromJson(response.body()?.string(), PwdResponse::class.java)
                    if (myResponse.success) {
                        handler.sendEmptyMessage(1)
                    }
                }
            })
        }

        background.setOnClickListener {
            remove()
        }

        btn_cancel.setOnClickListener {
            remove()
        }
    }

    private fun remove() {
        requireActivity().supportFragmentManager.beginTransaction().remove(this).commit()
        this.onDestroy()
    }
}