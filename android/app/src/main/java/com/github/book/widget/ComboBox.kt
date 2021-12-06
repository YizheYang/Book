package com.github.book.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import com.github.book.R
import java.util.*

/**
 * description none
 * author ez_yang@qq.com
 * date 2021.11.7 下午 9:39
 * version 1.0
 * update none
 **/
class ComboBox(context: Context, attrs: AttributeSet? = null, def: Int = 0) : ConstraintLayout(context, attrs, def) {

    constructor(context: Context, attrs: AttributeSet) : this(context, attrs, 0)

    constructor(context: Context) : this(context, null)

    private var tv_item: TextView
    private var ib_more: ImageButton
    private var tv_description: TextView

    private var listPopupWindow: ListPopupWindow? = null
    private var list = listOf<String>()
    private var myClick: OnMyClick? = null

    init {
        LayoutInflater.from(context).inflate(R.layout.widget_combobox, this, true)
        tv_item = findViewById(R.id.tv_comboBox_item)
        ib_more = findViewById(R.id.ib_comboBox)
        tv_description = findViewById(R.id.tv_comboBox_description)
        setListener()
    }

    fun setList(list: List<String>?) {
        list?.let {
            this.list = it
            if (tv_item.text == null || tv_item.text == "") {
                setItem(
                    if (it.isNotEmpty()) {
                        it[0]
                    } else {
                        null
                    }
                )
            }
        }
    }

    fun setItem(string: String?) {
        string?.let {
            tv_item.text = it
        }
    }

    fun setDescription(description: String) {
        tv_description.text = description
    }

    private fun setListener() {
        ib_more.setOnClickListener {
            if (listPopupWindow == null) {
                listPopupWindow = ListPopupWindow(context)
                show()
            } else {
                close()
            }
        }
    }

    private fun show() {
        val adapter = SimpleAdapter(
            context,
            getStandardList(list),
            R.layout.item_listpopupwindow,
            arrayOf("object"),
            intArrayOf(R.id.tv_listPopupWindow)
        )
        listPopupWindow?.setAdapter(adapter)
        listPopupWindow?.anchorView = tv_item
        listPopupWindow?.setOnItemClickListener { parent, view, position, id ->
            setItem(list[position])
            myClick?.onClick(parent, view, position, id, list)
            close()
        }
        listPopupWindow?.show()
    }

    private fun close() {
        listPopupWindow?.dismiss()
        listPopupWindow = null
    }

    private fun getStandardList(l: List<String>): MutableList<Map<String, Any>> {
        val list = mutableListOf<Map<String, Any>>()
        var map: MutableMap<String, Any>
        for (i in l.indices) {
            map = HashMap()
            map["object"] = l[i]
            list.add(map)
        }
        return list
    }

    interface OnMyClick {
        fun onClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long, list: List<String>)
    }

    fun setMyClick(myClick: OnMyClick) {
        this.myClick = myClick
    }

}