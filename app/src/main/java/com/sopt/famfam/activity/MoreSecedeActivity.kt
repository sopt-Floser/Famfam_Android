package com.sopt.famfam.activity

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.sopt.famfam.R
import com.sopt.famfam.database.FamilyData
import com.sopt.famfam.database.SharedPreferenceController
import com.sopt.famfam.delete.DeleteUserResponse
import com.sopt.famfam.network.ApplicationController
import com.sopt.famfam.network.NetworkService
import kotlinx.android.synthetic.main.activity_more_secede.*
import org.jetbrains.anko.startActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MoreSecedeActivity : AppCompatActivity() {

    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_more_secede)
        setOnClickListener()
    }

    private fun setOnClickListener() {
        btn_more_disconnect_ok.setOnClickListener {
            deleteUserResponse()
        }
    }

    private fun deleteUserResponse() {
        val token: String = FamilyData.token
        //통신 시작
        val deleteUserResponse: Call<DeleteUserResponse> =
            networkService.deleteUserResponse("application/json", token)
        deleteUserResponse.enqueue(object : Callback<DeleteUserResponse> {
            override fun onFailure(call: Call<DeleteUserResponse>, t: Throwable) {
                Log.e("Sign Up Fail", t.toString())
            }

            override fun onResponse(call: Call<DeleteUserResponse>, response: Response<DeleteUserResponse>) {
                if (response.body()!!.message == "회원 탈퇴 성공") {
                    SharedPreferenceController.clearUserSharedPreferences(this@MoreSecedeActivity)
                    startActivity<IntroActivity>()
                    finish()
                } else {
                    Log.e("error", "error")
                    startActivity<MainActivity>()
                    finish()
                }
            }
        })
    }
}
