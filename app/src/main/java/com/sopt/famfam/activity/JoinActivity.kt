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

class JoinActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join)
        setOnBtnClickListener()
    }


    private fun setOnBtnClickListener() {
        tv_code_generator_act_request_code_btn.setOnClickListener {
            code_generator_act_time_layout.visibility = View.VISIBLE
            tv_code_generator_act_request_code_btn_text.text = "완료"
        }
    }

}
