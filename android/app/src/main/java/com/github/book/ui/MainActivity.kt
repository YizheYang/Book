package com.github.book.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.github.book.MainVM
import com.github.book.R
import com.github.book.adapter.SeatAdapter
import com.github.book.base.BaseActivity
import com.github.book.entity.SeatBean
import com.github.book.entity.User
import com.github.book.widget.ComboBox
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : BaseActivity() {

    companion object {
        @JvmStatic
        fun startActivity(context: Context, user: User) {
            val intent = Intent(context, MainActivity::class.java)
            intent.putExtra("user", user)
            context.startActivity(intent)
        }
    }

    private lateinit var viewModel: MainVM
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: SeatAdapter
    private lateinit var cb_floor: ComboBox
    private lateinit var cb_area: ComboBox
    private lateinit var tv_title: TextView
    private lateinit var fab: FloatingActionButton
    private lateinit var sfl: SwipeRefreshLayout

    private lateinit var seatFragment: SeatFragment

    override fun getLayoutId() = R.layout.activity_main

    override fun doubleReturn(): Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sfl = findViewById(R.id.sfl)
        sfl.isRefreshing = true
        viewModel = ViewModelProvider(this)[MainVM::class.java]
        recyclerView = findViewById(R.id.rv)
        adapter = SeatAdapter(viewModel.getSeatListLD().value!!)
        recyclerView.layoutManager = GridLayoutManager(this, 3)
        recyclerView.adapter = adapter
        cb_floor = findViewById(R.id.cbb_floor)
        cb_area = findViewById(R.id.cbb_area)
        tv_title = findViewById(R.id.tv_title)
        fab = findViewById(R.id.fab)

        viewModel.user = intent.extras?.get("user") as User
        tv_title.text = "欢迎！${viewModel.user.name}"
    }

    override fun onStart() {
        super.onStart()
        setListener()
        setObserver()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            1 -> {
                when (resultCode) {
                    RESULT_OK -> {
                        LoginActivity.startActivity(this)
                        finish()
                    }
                    RESULT_CANCELED -> {
                        viewModel.user = data?.extras?.get("user") as User
                        tv_title.text = "欢迎！${viewModel.user.name}"
                    }
                }
            }
        }
    }

    private fun setListener() {
        viewModel.setOnRequest(object : MainVM.OnRequest {
            override fun onFinish() {
                if (viewModel.getSeatListLD().value?.size != 0) {
                    runOnUiThread {
                        initCBFloor()
                        initCBArea()
                    }
                }
                sfl.isRefreshing = false
                runOnUiThread {
                    Toast.makeText(this@MainActivity, "数据刷新成功", Toast.LENGTH_SHORT).show()
                }
            }
        })

        adapter.setOnChildrenClickListener(object : SeatAdapter.OnChildrenClickListener {
            override fun onSeatClick(
                holder: SeatAdapter.SeatViewHolder,
                list: MutableList<SeatBean>,
                position: Int
            ) {
                showSeatFragment(list[position])
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
                                ?.filter { it.floor.toString() == s }
                                ?.map { it.area }// 取area值组成新list
                                ?.toSortedSet()?.toList()// 去重
                                ?.let { it ->
                                    setList(it.map { it.toString() })
                                    viewModel.tempArea.value = it[0].toString()
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

        fab.setOnClickListener {
            SettingActivity.startActivityForResult(this, viewModel.user)
        }

        sfl.setOnRefreshListener {
            sfl.isRefreshing = true
            viewModel.loadData()
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
            ?.filter { it.floor.toString() == viewModel.tempFloor.value && it.area == viewModel.tempArea.value }
            ?.let {
                adapter.setList(it as MutableList<SeatBean>)
            }
        adapter.notifyDataSetChanged()
    }

    private fun initCBFloor() {
        cb_floor.apply {
            viewModel.getSeatListLD().value
                ?.map { it.floor }// 取floor值组成新list
                ?.toSortedSet()?.toList()// 去重
                ?.let { it ->
                    setList(it.map { it.toString() })
                    viewModel.tempFloor.value = it[0].toString()
                }
            setDescription("层")
        }
    }

    private fun initCBArea() {
        cb_area.apply {
            viewModel.getSeatListLD().value
                ?.filter { it.floor.toString() == viewModel.tempFloor.value }
                ?.map { it.area }// 取area值组成新list
                ?.toSortedSet()?.toList()// 去重
                ?.let { it ->
                    setList(it.map { it.toString() })
                    viewModel.tempArea.value = it[0].toString()
                }
            setDescription("区")
        }
    }

    private fun showSeatFragment(seatBean: SeatBean) {
        seatFragment = SeatFragment(seatBean)
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container_main, seatFragment).commit()
    }

}