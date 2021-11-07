package com.github.book.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.book.MainVM
import com.github.book.R
import com.github.book.SeatAdapter
import com.github.book.base.BaseActivity
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
    private lateinit var cb_building: ComboBox
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
        cb_building = findViewById(R.id.cb_building)
        cb_floor = findViewById(R.id.cb_floor)
        cb_area = findViewById(R.id.cb_area)
    }

    override fun onStart() {
        super.onStart()
        username = intent.getStringExtra("username").toString()
        cb_building.apply {
            viewModel.getSeatListLD().value?.map { it.building }?.let { setList(it) }
            setDescription("楼")
        }
        cb_building.apply {
            viewModel.getSeatListLD().value?.map { it.floor }?.let { setList(it) }
            setDescription("层")
        }
        cb_building.apply {
            viewModel.getSeatListLD().value?.map { it.area }?.let { setList(it) }
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
    }

    private fun setObserver() {
        viewModel.getSeatListLD().observe(this) {
            adapter.notifyDataSetChanged()
        }
    }
}