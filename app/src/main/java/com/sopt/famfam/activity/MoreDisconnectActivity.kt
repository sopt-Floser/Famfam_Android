package com.sopt.famfam.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.sopt.famfam.R
import com.sopt.famfam.database.FamilyData
import com.sopt.famfam.database.SharedPreferenceController
import com.sopt.famfam.delete.DeleteGroupsResponse
import com.sopt.famfam.delete.DeleteUserResponse
import com.sopt.famfam.network.ApplicationController
import com.sopt.famfam.network.NetworkService
import kotlinx.android.synthetic.main.activity_more_disconnect.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MoreDisconnectActivity : AppCompatActivity() {

    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_more_disconnect)
        setOnClickListener()
    }

    private fun setOnClickListener() {
        btn_more_disconnect_ok.setOnClickListener {
            // 가족 연결 끊기
            deleteGroupsResponse()
        }
        btn_disconnect_back.setOnClickListener {
            finish()
        }
    }

    private fun deleteGroupsResponse() {
        val token: String = FamilyData.token
        //통신 시작
        val deleteGroupsResponse: Call<DeleteGroupsResponse> =
            networkService.deleteGroupsResponse("application/json", token)
        deleteGroupsResponse.enqueue(object : Callback<DeleteGroupsResponse> {
            override fun onFailure(call: Call<DeleteGroupsResponse>, t: Throwable) {
                Log.e("Sign Up Fail", t.toString())
            }

            override fun onResponse(call: Call<DeleteGroupsResponse>, response: Response<DeleteGroupsResponse>) {
                if (response.body()!!.message == "그룹 탈퇴 성공") {
                    SharedPreferenceController.clearUserSharedPreferences(this@MoreDisconnectActivity)
                    startActivity<IntroActivity>()
                    finish()
                } else {
                    Log.e("error", "error")
                    toast("연결끊기 실패")
                    startActivity<MainActivity>()
                    finish()
                }
            }
        })
    }
}

