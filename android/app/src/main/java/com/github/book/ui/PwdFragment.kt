package com.github.book.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.github.book.MainVM
import com.github.book.R

/**
 * description none
 * author ez_yang@qq.com
 * date 2021.11.25 下午 10:54
 * version 1.0
 * update none
 **/
class PwdFragment : Fragment() {
    private lateinit var et_new: EditText
    private lateinit var btn_cancel: Button
    private lateinit var btn_confirm: Button
    private lateinit var background: View

    private lateinit var viewModel: MainVM

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_pwd, container, false)
        et_new = view.findViewById(R.id.et_pwd_new)
        btn_cancel = view.findViewById(R.id.btn_pwd_cancel)
        btn_confirm = view.findViewById(R.id.btn_pwd_confirm)
        background = view.findViewById(R.id.view_pwd_background)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(requireActivity())[MainVM::class.java]
        setListener()
    }

    private fun setListener() {
        btn_confirm.setOnClickListener {
//            TODO("修改密码")
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