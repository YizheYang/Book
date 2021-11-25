package com.github.book.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.github.book.MainVM
import com.github.book.R
import com.github.book.entity.SeatBean

/**
 * description none
 * author ez_yang@qq.com
 * date 2021.11.25 下午 2:18
 * version 1.0
 * update none
 **/
class SeatFragment(private val seat: SeatBean) : Fragment() {
    private lateinit var background: View
    private lateinit var id: TextView
    private lateinit var status: TextView
    private lateinit var sDate: TextView
    private lateinit var dDate: TextView
    private lateinit var btn_cancel: Button
    private lateinit var btn_book: Button

    private lateinit var viewModel: MainVM

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_seat, container, false)
        background = view.findViewById(R.id.view_seat_background)
        id = view.findViewById(R.id.tv_seat_id)
        status = view.findViewById(R.id.tv_seat_status)
        sDate = view.findViewById(R.id.tv_seat_sDate)
        dDate = view.findViewById(R.id.tv_seat_ddate)
        btn_cancel = view.findViewById(R.id.btn_seat_cancel)
        btn_book = view.findViewById(R.id.btn_seat_book)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(requireActivity())[MainVM::class.java]
        id.text = id.text.toString() + seat.no.toString()
        if (seat.isBook()) {
            status.text = status.text.toString() + "已预订"
            sDate.text = sDate.text.toString() + seat.getLatelySDate().toString()
            dDate.text = dDate.text.toString() + seat.getLatelyDDate().toString()
        } else {
            status.text = status.text.toString() + "可预订"
        }
        setListener()
    }

    private fun setListener() {
        background.setOnClickListener {
            remove()
        }

        btn_cancel.setOnClickListener {
            remove()
        }

        btn_book.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, BookFragment(seat)).commit()
            remove()
        }
    }

    private fun remove() {
        requireActivity().supportFragmentManager.beginTransaction().remove(this).commit()
        this.onDestroy()
    }
}