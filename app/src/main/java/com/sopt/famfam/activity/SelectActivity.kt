package com.sopt.famfam.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.sopt.famfam.R
import com.sopt.famfam.database.FamilyData
import com.sopt.famfam.database.SharedPreferenceController
import com.sopt.famfam.network.ApplicationController
import com.sopt.famfam.network.NetworkService
import com.sopt.famfam.post.PostGroupsResponse
import com.sopt.famfam.post.PostSignUpResponse
import kotlinx.android.synthetic.main.activity_select.*
import kotlinx.android.synthetic.main.activity_signup.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SelectActivity : AppCompatActivity() {

    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select)
        setOnBtnClickListener()
    }

    private fun setOnBtnClickListener() {
        select_act_create_btn.setOnClickListener {
            getGroupsCreateResponse()
        }
        select_act_join_btn.setOnClickListener {
            startActivity<JoinActivity>()
        }
    }

    private fun getGroupsCreateResponse() {
        val token : String = FamilyData.token
        //통신 시작
        val postGroupsResponse: Call<PostGroupsResponse> =
            networkService.postGroupsResponse("application/json", token)
        postGroupsResponse.enqueue(object : Callback<PostGroupsResponse> {
            override fun onFailure(call: Call<PostGroupsResponse>, t: Throwable) {
                Log.e("Sign Up Fail", t.toString())
            }
            override fun onResponse(call: Call<PostGroupsResponse>, response: Response<PostGroupsResponse>) {
                if (response.isSuccessful) {
                    var message: String = response.body()!!.message
                    toast(message)
                    startActivity<MainActivity>()
                    finish()
                }
            }
        })
    }
}