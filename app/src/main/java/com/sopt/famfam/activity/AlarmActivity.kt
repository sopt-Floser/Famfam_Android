package com.sopt.famfam.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.LinearLayout
import com.sopt.famfam.R
import com.sopt.famfam.adapter.AlarmAdapter
import com.sopt.famfam.adapter.FamilyListAdapter
import com.sopt.famfam.adapter.item.FamilyListItem
import com.sopt.famfam.data.AlarmData
import com.sopt.famfam.database.FamilyData
import com.sopt.famfam.get.GetGroupUserResponse
import com.sopt.famfam.get.GethistoryResponse
import com.sopt.famfam.network.ApplicationController
import com.sopt.famfam.network.NetworkService
import kotlinx.android.synthetic.main.activity_alarm.*
import kotlinx.android.synthetic.main.fragment_home_alert.*
import org.jetbrains.anko.toast
import org.jetbrains.anko.verticalLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AlarmActivity : AppCompatActivity() {
    lateinit var AlarmAdapter: AlarmAdapter

    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm)
        btn_alarm_back.setOnClickListener {
            finish()
        }
        getAlarmListResponse()
    }
//    private fun setRecyclerView(){
//        getAlarmListResponse()
//        rv_alarm_list.adapter = AlarmAdapter
//        rv_alarm_list.layoutManager =LinearLayoutManager(application, LinearLayoutManager.VERTICAL, false)
//    }

    private fun getAlarmListResponse() {
        val gethistoryResponse = networkService.gethistoryResponse("application/json", FamilyData.token)
        gethistoryResponse.enqueue(object : Callback<GethistoryResponse> {
            override fun onFailure(call: Call<GethistoryResponse>, t: Throwable) {
                Log.e("Login fail", t.toString())
            }

            override fun onResponse(call: Call<GethistoryResponse>, response: Response<GethistoryResponse>) {
                if (response.isSuccessful) {
                    val data1 = response.body()!!.data
                    when(data1){
                        null -> return
                    }
                    val data = response.body()!!.data.histories
                    var list = ArrayList<AlarmData>()
                    var i: Int = 0
                    for (item in data) {
                        if (response.body()!!.message == "히스토리 조회 성공") {
                            var image : Int = R.drawable.icon_new
                                when (item.historyType) {
                                    "ADD_SCHEDULE" -> image = R.drawable.icon_calender
                                    "ADD_ANNIVERSARY" -> image = R.drawable.icon_birthday
                                    "ADD_CONTENT" -> image = R.drawable.icon_newfeed
                                    "ADD_COMMENT" -> image = R.drawable.icon_new
                                    "ADD_EMOTION" -> image = R.drawable.icon_emotion_b
                                }
                            list.add(AlarmData(image, item.content))
                        } else {
                            toast("알림이 없습니다!")
                        }
                    }
//                    alarmlist!!.adapter= FamilyListAdapter(context!!,list)
//                    alarmlist!!.layoutManager = LinearLayoutManager(context, LinearLayout.HORIZONTAL, false)
                    rv_alarm_list.adapter = AlarmAdapter(applicationContext!!, list)
                    rv_alarm_list.layoutManager =LinearLayoutManager(application, LinearLayoutManager.VERTICAL, false)
                }
            }
        })
    }
}
