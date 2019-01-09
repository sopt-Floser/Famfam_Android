package com.sopt.famfam.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.sopt.famfam.R
import com.sopt.famfam.database.FamilyData
import com.sopt.famfam.database.SharedPreferenceController
import com.sopt.famfam.delete.DeleteUserResponse
import com.sopt.famfam.get.GetGroupsCreateCodeResponse
import com.sopt.famfam.network.ApplicationController
import com.sopt.famfam.network.NetworkService
import com.sopt.famfam.post.PostJoinGroupsResponse
import kotlinx.android.synthetic.main.activity_join.*
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class JoinActivity : AppCompatActivity() {

    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join)
        setOnBtnClickListener()
    }


    private fun setOnBtnClickListener() {
        tv_code_generator_act_request_code_btn.setOnClickListener {
            //            code_generator_act_time_layout.visibility = View.VISIBLE
            postJoinGroupsResponse()
        }
    }

    private fun postJoinGroupsResponse() {
        if (et_join_act_input_code.text.isNotEmpty()) {
            val token: String = FamilyData.token
            val input_code = et_join_act_input_code.text.toString()
            val jsonObject: JSONObject = JSONObject()
            jsonObject.put("code", input_code)
            val gsonObject: JsonObject = JsonParser().parse(jsonObject.toString()) as JsonObject
            //통신 시작
            val postJoinGroupsResponse: Call<PostJoinGroupsResponse> =
                networkService.postJoinGroupsResponse("application/json", token, gsonObject)
            postJoinGroupsResponse.enqueue(object : Callback<PostJoinGroupsResponse> {
                override fun onFailure(call: Call<PostJoinGroupsResponse>, t: Throwable) {
                    Log.e("Sign Up Fail", t.toString())
                }

                override fun onResponse(
                    call: Call<PostJoinGroupsResponse>,
                    response: Response<PostJoinGroupsResponse>
                ) {
                    if (response.body()!!.message == "그룹 참여 성공") {
                     FamilyData.groupId = response.body()!!.data.groupIdx
                    FamilyData.groupIdx = response.body()!!.data.groupId
                        startActivity<MainActivity>()
                        finish()
                    } else if (response.body()!!.message == "회원을 찾을 수 없습니다.") {
                        toast("회원을 찾을 수 없습니다.")
                    } else if (response.body()!!.message == "이미 가입된 그룹이 있습니다.") {
                        toast("이미 가입된 그룹이 있습니다.")
                    } else if (response.body()!!.message == "초대코드가 유효하지 않습니다.") {
                        toast("초대코드가 유효하지 않습니다.")
                    } else if (response.body()!!.message == "데이터베이스 에러") {
                        toast("데이터베이스 에러")
                    } else {
                        toast("서버 내부 에러")
                    }
                }
            })
        } else {
            toast("초대 코드를 입력해주세요!")
        }
    }
}
