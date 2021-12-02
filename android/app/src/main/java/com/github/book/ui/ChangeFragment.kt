package com.github.book.ui

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.github.book.MainVM
import com.github.book.R
import com.github.book.base.BaseFragment

/**
 * description none
 * author ez_yang@qq.com
 * date 2021.11.25 下午 10:54
 * version 1.0
 * update none
 **/
abstract class ChangeFragment : BaseFragment() {
    lateinit var et_new: EditText
    lateinit var btn_cancel: Button
    lateinit var btn_confirm: Button
    lateinit var background: View
    lateinit var hint: TextView
    lateinit var viewModel: MainVM

    override fun getLayoutId() = R.layout.fragment_change

    override fun initView(view: View, savedInstanceState: Bundle?) {
        et_new = view.findViewById(R.id.et_pwd_new)
        btn_cancel = view.findViewById(R.id.btn_pwd_cancel)
        btn_confirm = view.findViewById(R.id.btn_pwd_confirm)
        background = view.findViewById(R.id.view_pwd_background)
        hint = view.findViewById(R.id.tv_pwd_new)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(requireActivity())[MainVM::class.java]
        hint.text = setHint()
        setListener()
    }

    abstract fun setHint(): String

    open fun setListener() {
        background.setOnClickListener {
            remove()
        }

        btn_cancel.setOnClickListener {
            remove()
        }
    }

    fun remove() {
        requireActivity().supportFragmentManager.beginTransaction().remove(this).commit()
        this.onDestroy()
    }
}