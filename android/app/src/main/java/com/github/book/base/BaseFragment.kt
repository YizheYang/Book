package com.github.book.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment

/**
 * description none
 * author ez_yang@qq.com
 * date 2021.11.28 上午 12:28
 * version 1.0
 * update none
 **/
abstract class BaseFragment : Fragment() {

    @LayoutRes
    abstract fun getLayoutId(): Int

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(getLayoutId(), container, false)
        initView(view, savedInstanceState)
        return view
    }

    abstract fun initView(view: View, savedInstanceState: Bundle?)

    fun loading() {
        (requireActivity() as BaseActivity).loading()
    }

    fun stopLoading() {
        (requireActivity() as BaseActivity).stopLoading()
    }
}