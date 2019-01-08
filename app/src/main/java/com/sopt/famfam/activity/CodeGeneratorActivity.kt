package com.sopt.famfam.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.sopt.famfam.R
import com.sopt.famfam.database.FamilyData
import com.sopt.famfam.get.GetGroupsCreateCodeResponse
import com.sopt.famfam.network.ApplicationController
import com.sopt.famfam.network.NetworkService
import kotlinx.android.synthetic.main.activity_code_generator.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.widget.Toast
import android.view.KeyEvent.KEYCODE_ENTER
import android.content.Intent
import android.os.Handler
import android.os.Message
import android.widget.TextView
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*


class CodeGeneratorActivity : AppCompatActivity() {

    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_code_generator)
        setOnBtnClickListener()

    }

    // code_generator_act_time_layout
    // tv_certification_act_request_code_btn
    private fun setOnBtnClickListener() {
        tv_code_generator_act_request_code_btn.setOnClickListener {
            getGroupsCreateCodeResponse()
            tv_code_generator_act_request_code_btn.visibility = View.GONE
            tv_code_generator_act_complete_btn.visibility = View.VISIBLE
            code_generator_act_time_layout.visibility = View.VISIBLE
            h.sendEmptyMessage(0)
        }
        tv_code_generator_act_complete_btn.setOnClickListener {
            startActivity<MainActivity>()
            finish()
        }
    }

    private fun getGroupsCreateCodeResponse() {
        val token : String = FamilyData.token
        //통신 시작
        val postGroupsCreateCodeResponse: Call<GetGroupsCreateCodeResponse> =
            networkService.getGroupsCreateCodeResponse("application/json", token)
        postGroupsCreateCodeResponse.enqueue(object : Callback<GetGroupsCreateCodeResponse> {
            override fun onFailure(call: Call<GetGroupsCreateCodeResponse>, t: Throwable) {
                Log.e("Sign Up Fail", t.toString())
            }
            override fun onResponse(call: Call<GetGroupsCreateCodeResponse>, response: Response<GetGroupsCreateCodeResponse>) {
                if (response.body()!!.message == "초대코드 조회 성공") {
                    et_code_generator_act_show_code.text = response.body()!!.data.code
                } else {
                    Log.e("error", "error")
                    startActivity<MainActivity>()
                    finish()
                }
            }
        })
    }
    var timer : Int = 60
    var h: Handler = object : Handler() {
       override fun handleMessage(msg: Message) {
            // text 카운트 다운
            timer --
            if(timer == 0){
                tv_certification_act_seconds.text = "인증시간 만료"
            }
            tv_certification_act_seconds.text = timer.toString()+"초"
                this.sendEmptyMessageDelayed(0, 1000)
           Log.d("uuuu1", timer.toString())
        }
    }
}
