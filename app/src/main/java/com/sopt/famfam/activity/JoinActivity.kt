package com.sopt.famfam.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.sopt.famfam.R
import kotlinx.android.synthetic.main.activity_code_generator.*

class JoinActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join)
//        setOnBtnClickListener()
    }

    // code_generator_act_time_layout
    // tv_certification_act_request_code_btn
//    private fun setOnBtnClickListener() {
//        tv_code_generator_act_request_code_btn.setOnClickListener {
//            code_generator_act_time_layout.visibility = View.VISIBLE
//            tv_code_generator_act_request_code_btn_text.text = "완료"
//        }
//    }
}
