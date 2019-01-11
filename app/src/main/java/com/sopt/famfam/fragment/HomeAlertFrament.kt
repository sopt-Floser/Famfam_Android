package com.sopt.famfam.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sopt.famfam.R
import com.sopt.famfam.activity.AlarmActivity
import com.sopt.famfam.database.FamilyData
import com.sopt.famfam.get.GethistoryResponse
import com.sopt.famfam.network.ApplicationController
import com.sopt.famfam.network.NetworkService
import org.jetbrains.anko.support.v4.startActivity
import kotlinx.android.synthetic.main.fragment_home_alert.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeAlertFrament : Fragment() {

    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_home_alert, container, false)
        getHistoryListResponse()
        view.setOnClickListener {
            startActivity<AlarmActivity>()
        }
        return view
    }

    private fun getHistoryListResponse() {
        val gethistoryResponse = networkService.gethistoryResponse("application/json", FamilyData.token)
        gethistoryResponse.enqueue(object : Callback<GethistoryResponse> {
            override fun onFailure(call: Call<GethistoryResponse>, t: Throwable) {
                Log.e("Login fail", t.toString())
            }

            override fun onResponse(call: Call<GethistoryResponse>, response: Response<GethistoryResponse>) {
                if (response.body()!!.message == "히스토리 조회 성공") {
                    var i: Int = 0
                    val data = response.body()!!.data.histories
                    for (item in data) {
                        if (i == 0) {
                            when (response.body()!!.data.histories[0].historyType) {
                                "ADD_SCHEDULE" -> history_image1.setImageResource(R.drawable.icon_calender_white)
                                "ADD_ANNIVERSARY" -> history_image1.setImageResource(R.drawable.icon_birthday_w)
                                "ADD_CONTENT" -> history_image1.setImageResource(R.drawable.icon_newfeed_w)
                                "ADD_COMMENT" -> history_image1.setImageResource(R.drawable.icon_new_w)
                                "ADD_EMOTION" -> history_image1.setImageResource(R.drawable.icon_emotion_w)
                            }
                            history_text1.text = response.body()!!.data.histories[0].content
                        } else if (i == 1) {
                            when (response.body()!!.data.histories[1].historyType) {
                                "ADD_SCHEDULE" -> history_image2.setImageResource(R.drawable.icon_calender_white)
                                "ADD_ANNIVERSARY" -> history_image2.setImageResource(R.drawable.icon_birthday_w)
                                "ADD_CONTENT" -> history_image2.setImageResource(R.drawable.icon_newfeed_w)
                                "ADD_COMMENT" -> history_image2.setImageResource(R.drawable.icon_new_w)
                                "ADD_EMOTION" -> history_image2.setImageResource(R.drawable.icon_emotion_w)
                            }
                            history_text2.text = response.body()!!.data.histories[1].content
                        } else if (i == 2) {
                            when (response.body()!!.data.histories[2].historyType) {
                                "ADD_SCHEDULE" -> history_image3.setImageResource(R.drawable.icon_calender_white)
                                "ADD_ANNIVERSARY" -> history_image3.setImageResource(R.drawable.icon_birthday_w)
                                "ADD_CONTENT" -> history_image3.setImageResource(R.drawable.icon_newfeed_w)
                                "ADD_COMMENT" -> history_image3.setImageResource(R.drawable.icon_new_w)
                                "ADD_EMOTION" -> history_image3.setImageResource(R.drawable.icon_emotion_w)
                            }
                            history_text3.text = response.body()!!.data.histories[2].content
                        } else if (i == 3) {
                            when (response.body()!!.data.histories[3].historyType) {
                                "ADD_SCHEDULE" -> history_image4.setImageResource(R.drawable.icon_calender_white)
                                "ADD_ANNIVERSARY" -> history_image4.setImageResource(R.drawable.icon_birthday_w)
                                "ADD_CONTENT" -> history_image4.setImageResource(R.drawable.icon_newfeed_w)
                                "ADD_COMMENT" -> history_image4.setImageResource(R.drawable.icon_new_w)
                                "ADD_EMOTION" -> history_image4.setImageResource(R.drawable.icon_emotion_w)
                            }
                            history_text4.text = response.body()!!.data.histories[3].content
                        } else if (i == 4) {
                            when (response.body()!!.data.histories[4].historyType) {
                                "ADD_SCHEDULE" -> history_image5.setImageResource(R.drawable.icon_calender_white)
                                "ADD_ANNIVERSARY" -> history_image5.setImageResource(R.drawable.icon_birthday_w)
                                "ADD_CONTENT" -> history_image5.setImageResource(R.drawable.icon_newfeed_w)
                                "ADD_COMMENT" -> history_image5.setImageResource(R.drawable.icon_new_w)
                                "ADD_EMOTION" -> history_image5.setImageResource(R.drawable.icon_emotion_w)
                            }
                            history_text5.text = response.body()!!.data.histories[4].content
                        } else {
                            when (response.body()!!.data.histories[5].historyType) {
                                "ADD_SCHEDULE" -> history_image6.setImageResource(R.drawable.icon_calender_white)
                                "ADD_ANNIVERSARY" -> history_image6.setImageResource(R.drawable.icon_birthday_w)
                                "ADD_CONTENT" -> history_image6.setImageResource(R.drawable.icon_newfeed_w)
                                "ADD_COMMENT" -> history_image6.setImageResource(R.drawable.icon_new_w)
                                "ADD_EMOTION" -> history_image6.setImageResource(R.drawable.icon_emotion_w)
                            }
                            history_text6.text = response.body()!!.data.histories[5].content
                        }
                        i++

                    }
//                    히스토리 타입
//                            일정 추가 ADD_SCHEDULE /
//                            기념일 추가 ADD_ANNIVERSARY /
//                            게시글 등록 ADD_CONTENT /
//                            댓글 등록 ADD_COMMENT /
//                            감정 표현 ADD_EMOTION

                } else {
                    history_image1.visibility = View.INVISIBLE
                    history_image2.visibility = View.INVISIBLE
                    history_image3.visibility = View.INVISIBLE
                    history_image4.visibility = View.INVISIBLE
                    history_image5.visibility = View.INVISIBLE
                    history_image6.visibility = View.INVISIBLE
                    history_text1.text = "알림이 없습니다."
                }
            }
        })
    }
}
