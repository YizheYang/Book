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
import com.github.book.Constant.format8date
import com.github.book.MainVM
import com.github.book.R
import com.github.book.adapter.SeatAdapter
import com.github.book.base.BaseActivity
import com.github.book.entity.SeatBean
import com.github.book.entity.User
import com.github.book.widget.ComboBox
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.text.SimpleDateFormat
import java.util.*

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
    private var flag = false

    override fun getLayoutId() = R.layout.activity_main

    override fun doubleReturn() = true

    override fun initProgressBar() = R.id.pgb_main

    override fun initBackground() = R.id.view_main_background

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
        setListener()
        setObserver()// 放在onStart设置的话，会在切换别的AC然后切换回来的时候执行观察时间。
    }

    override fun onStart() {
        super.onStart()
        tv_title.text = "欢迎，${viewModel.user.name}！今天是${
            SimpleDateFormat("yyyyMMdd").format(Date(System.currentTimeMillis())).format8date()
        }\n" + "当前显示今天的座位情况，可预订明天的座位"
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            1 -> {
                when (resultCode) {
                    RESULT_LOGOUT -> {
                        LoginActivity.startActivity(this)
                        finish()
                    }
                    RESULT_CHANGEUSERNAME -> {
                        viewModel.user = data?.extras?.get("user") as User
                    }
                    RESULT_UNSUBSCRIBE -> {
                        refreshList()
                    }
                }
            }
        }
    }

    private fun setListener() {
        viewModel.setOnRequest(object : MainVM.OnRequest {
            override fun onFinish() {
                runOnUiThread {
                    initCBFloor()
                    initCBArea()
                    viewModel.getSeatListLD().value?.let { list ->
                        if (list.size != 0) {
                            list.filter { it.floor == viewModel.tempFloor.value && it.area == viewModel.tempArea.value }
                                .let { adapter.setList(it) }
                            adapter.notifyDataSetChanged()
                        }
                    }
                    sfl.isRefreshing = false
//                    Toast.makeText(this@MainActivity, "数据刷新成功", Toast.LENGTH_SHORT).show()
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
                        flag = false
                        cb_area.apply {
                            viewModel.floorList
                                .filter { it.floor.toString() == s }[0].areaList
                                .let {
                                    setList(it)
                                    setItem(it[0])
                                    viewModel.tempArea.value = it[0]
                                }
                        }
                        flag = true
                        viewModel.tempFloor.value = s.toInt()
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
                        viewModel.tempArea.value = s
                    }
                }
            })
        }

        fab.setOnClickListener {
            SettingActivity.startActivityForResult(this, viewModel.user)
        }

        sfl.setOnRefreshListener {
            refreshList()
        }
    }


    private fun setObserver() {
        viewModel.tempFloor.observe(this) {
            if (flag) {
                refreshList()
            }
        }

        viewModel.tempArea.observe(this) {
            if (flag) {
                refreshList()
            }
        }
    }

    private fun refreshList() {
        sfl.isRefreshing = true
        viewModel.loadData()
    }

    private fun initCBFloor() {
        flag = false
        cb_floor.apply {
            viewModel.floorList
                .map { it.floor }
                .let { list ->
                    setList(list.map { it.toString() })
                    viewModel.tempFloor.apply {
                        value = value ?: list[0]
                    }
                }
            setDescription("层")
        }
        flag = true
    }

    private fun initCBArea() {
        flag = false
        cb_area.apply {
            viewModel.floorList
                .filter { it.floor == viewModel.tempFloor.value }[0].areaList
                .let { list ->
                    setList(list)
                    viewModel.tempArea.apply {
                        value = value ?: list[0]
                    }
                }
            setDescription("区")
        }
        flag = true
    }

    private fun showSeatFragment(seatBean: SeatBean) {
        seatFragment = SeatFragment(seatBean)
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container_main, seatFragment).commit()
    }

}