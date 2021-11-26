package com.github.book.ui

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.github.book.MainVM
import com.github.book.R
import com.github.book.adapter.OrderAdapter
import com.github.book.entity.SeatBean

/**
 * description none
 * author ez_yang@qq.com
 * date 2021.11.25 下午 11:24
 * version 1.0
 * update none
 **/
class OrderFragment : Fragment() {
    private lateinit var viewModel: MainVM
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: OrderAdapter

    private val orderList = mutableListOf<SeatBean>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_order, container, false)
        recyclerView = view.findViewById(R.id.rv_list)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(requireActivity())[MainVM::class.java]
//        viewModel.getSeatListLD().value.apply {
//        }
//        TODO("search by id to get list")
//        adapter = OrderAdapter(viewModel.getSeatListLD().value!!)
        recyclerView.adapter = adapter
        setListener()
    }

    private fun setListener() {
        adapter.setOnChildrenClickListener(object : OrderAdapter.OnChildrenClickListener {
            override fun onItemClick() {
                val builder = AlertDialog.Builder(requireContext())
                builder.setTitle("退订")
                    .setMessage("是否退订该座位")
                    .setNegativeButton("取消") { dialog, which -> dialog.dismiss() }
                    .setPositiveButton("确认") { dialog, which ->
                        TODO("Not yet implemented")

                    }
                builder.create().show()
            }

        })
    }

    private fun remove() {
        requireActivity().supportFragmentManager.beginTransaction().remove(this).commit()
        this.onDestroy()
    }
}