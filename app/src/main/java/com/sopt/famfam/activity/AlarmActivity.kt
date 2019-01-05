package com.sopt.famfam.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import com.sopt.famfam.R
import com.sopt.famfam.adapter.AlarmAdapter
import com.sopt.famfam.data.AlarmData
import kotlinx.android.synthetic.main.activity_alarm.*
import org.jetbrains.anko.verticalLayout

class AlarmActivity : AppCompatActivity() {
    lateinit var AlarmAdapter: AlarmAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm)
        setRecyclerView()
    }
    private fun setRecyclerView(){
        var dataList : ArrayList<AlarmData> = ArrayList()
        dataList.add(AlarmData("123", "123", "리스트1"))
        dataList.add(AlarmData("123", "123", "리스트2"))
        dataList.add(AlarmData("123", "123", "리스트3"))
        dataList.add(AlarmData("123", "123", "리스트4"))
        dataList.add(AlarmData("123", "123", "리스트5"))
        dataList.add(AlarmData("123", "123", "리스트6"))
        dataList.add(AlarmData("123", "123", "리스트7"))
        dataList.add(AlarmData("123", "123", "리스트8"))

        AlarmAdapter = AlarmAdapter(application, dataList)
        rv_alarm_list.adapter = AlarmAdapter
        rv_alarm_list.layoutManager =LinearLayoutManager(application,LinearLayoutManager.VERTICAL, false)

//                GridLayoutManager(application, 8)
    }
}
