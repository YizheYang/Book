package com.github.book.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.book.MainVM
import com.github.book.R
import com.github.book.adapter.SeatAdapter
import com.github.book.base.BaseActivity
import com.github.book.entity.SeatBean
import com.github.book.widget.ComboBox

class MainActivity : BaseActivity() {

    companion object {
        @JvmStatic
        fun startActivity(context: Context, username: String) {
            val intent = Intent(context, MainActivity::class.java)
            intent.putExtra("username", username)
            context.startActivity(intent)
        }
    }

    private lateinit var viewModel: MainVM
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: SeatAdapter
    private lateinit var username: String
    private lateinit var cb_floor: ComboBox
    private lateinit var cb_area: ComboBox

    override fun getLayoutId() = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[MainVM::class.java]
        recyclerView = findViewById(R.id.rv)
        val layoutManager = GridLayoutManager(this, 3)
        adapter = SeatAdapter(viewModel.getSeatListLD().value!!)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
        cb_floor = findViewById(R.id.cb_floor)
        cb_area = findViewById(R.id.cb_area)
    }

    override fun onStart() {
        super.onStart()
        username = intent.getStringExtra("username").toString()
        cb_floor.apply {
            viewModel.getSeatListLD().value
                ?.map { it.floor }// 取floor值组成新list
                ?.toSortedSet()?.toList()// 去重
                ?.let {
                    setList(it)
                    setItem(it[0])
                    viewModel.tempFloor.value = it[0]
                }
            setDescription("层")
        }
        cb_area.apply {
            viewModel.getSeatListLD().value
                ?.map { it.area }// 取area值组成新list
                ?.toSortedSet()?.toList()// 去重
                ?.let {
                    setList(it)
                    setItem(it[0])
                    viewModel.tempArea.value = it[0]
                }
            setDescription("区")
        }
        setListener()
        setObserver()
    }

    private fun setListener() {
        adapter.setOnChildrenClickListener(object : SeatAdapter.OnChildrenClickListener {
            override fun onSeatClickListener(holder: SeatAdapter.SeatViewHolder, position: Int) {
                Toast.makeText(this@MainActivity, "$position", Toast.LENGTH_SHORT).show()
            }
        })

        cb_floor.apply {
            setMyClick(object : ComboBox.OnMyClick {
                override fun onClick(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long,
                    list: List<String>
                ) {
                    list[position].let { s ->
                        setItem(s)
                        cb_area.apply {
                            viewModel.getSeatListLD().value
                                ?.filter { it.floor == s }
                                ?.map { it.area }// 取area值组成新list
                                ?.toSortedSet()?.toList()// 去重
                                ?.let {
                                    setList(it)
                                    setItem(it[0])
                                    viewModel.tempArea.value = it[0]
                                }
                        }
                        viewModel.tempFloor.value = s
                    }
                }
            })
        }

        cb_area.apply {
            setMyClick(object : ComboBox.OnMyClick {
                override fun onClick(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long,
                    list: List<String>
                ) {
                    list[position].let { s ->
                        setItem(s)
                        viewModel.tempArea.value = s
                    }
                }
            })
        }
    }

    private fun setObserver() {
        viewModel.getSeatListLD().observe(this) {
            adapter.notifyDataSetChanged()
        }

        viewModel.tempFloor.observe(this) {
            refreshList()
        }

        viewModel.tempArea.observe(this) {
            refreshList()
        }
    }

    private fun refreshList() {
        viewModel.getSeatListLD().value
            ?.filter { it.floor == viewModel.tempFloor.value && it.area == viewModel.tempArea.value }
            ?.let {
                adapter.setList(it as MutableList<SeatBean>)
            }
        adapter.notifyDataSetChanged()
    }
}