package com.sopt.famfam.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.sopt.famfam.R
import com.sopt.famfam.database.FamilyData
import com.sopt.famfam.database.SharedPreferenceController
import com.sopt.famfam.get.GetGroupsCreateCodeResponse
import com.sopt.famfam.network.ApplicationController
import com.sopt.famfam.network.NetworkService
import com.sopt.famfam.post.PostLogInResponse
import com.sopt.famfam.put.PutResetPwResponse
import kotlinx.android.synthetic.main.activity_code_generator.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_resetting_pw.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ResettingPwActivity : AppCompatActivity() {

    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resetting_pw)
        setOnBtnClickListener()
    }

    private fun setOnBtnClickListener() {
        tv_resetting_pw_btn.setOnClickListener {
            if(et_resetting_pw_act_input_user_pw.text.toString() == et_resetting_pw_act_input_user_pw_confirm.text.toString()){
                // 패스워드 재설정 통신 달기
                startActivity<LoginActivity>()
                finish()
            }
            // putResetPwResponse

        }
    }

//    private fun putResetPwResponse() {
//        if (et_login_act_id.text.toString().isNotEmpty() && et_login_act_pw.text.toString().isNotEmpty()) {
//            val input_id = et_login_act_id.text.toString()
//            val input_pw = et_login_act_pw.text.toString()
//            val jsonObject: JSONObject = JSONObject()
//            jsonObject.put("userId", input_id)
//            jsonObject.put("userPw", input_pw)
//            val gsonObject: JsonObject = JsonParser().parse(jsonObject.toString()) as JsonObject
//
//            val putResetPwResponse = networkService.putResetPwResponse("application/json", token,gsonObject )
//            postLogInResponse.enqueue(object : Callback<PostLogInResponse> {
//                override fun onFailure(call: Call<PostLogInResponse>, t: Throwable) {
//                    Log.e("Login fail", t.toString())
//                }
//
//                override fun onResponse(call: Call<PostLogInResponse>, response: Response<PostLogInResponse>) {
//                    if (response.isSuccessful) {
//                        val token = response.body()!!.data.token
//                        FamilyData.groupId = response.body()!!.data.user.groupIdx
//                        FamilyData.userId = response.body()!!.data.user.userId
//                        FamilyData.userName = response.body()!!.data.user.userName
//                        FamilyData.token = token
//                        FamilyData.statusMessage =response.body()!!.data.user.statusMessage
//                        FamilyData.birthday = response.body()!!.data.user.birthday
////                        FamilyData.backPhoto = response.body()!!.data.user.backPhoto
////                        FamilyData.profilePhoto = response.body()!!.data.user.profilePhoto
//                        FamilyData.sexType = response.body()!!.data.user.sexType
//                        Log.e("uuuu1", token)
//
//                        SharedPreferenceController.setLoginData(
//                            this@LoginActivity,
//                            FamilyData.groupId.toString() + "," +
//                                    FamilyData.userId + "," +
//                                    FamilyData.userName+ ","+
//                                    FamilyData.statusMessage+","+
//                                    FamilyData.birthday+","+
//                                    FamilyData.sexType
//
//                        )
//                        //저번 시간에 배웠던 SharedPreference에 토큰을 저장! 왜냐하면 토큰이 필요한 통신에 사용하기 위해서!!
//                        SharedPreferenceController.setAuthorization(this@LoginActivity, token)
//                        toast(SharedPreferenceController.getAuthorization(this@LoginActivity))
////                        startActivity<MainActivity>()
//////                        finish()
//                        val groupIdx = FamilyData.groupId.toString()
//                        Log.e("uuuu1", groupIdx)
//                        if (groupIdx == "-1") {
//                            startActivity<SelectActivity>()
//                        } else {
//                            startActivity<MainActivity>()
//                            finish()
//                        }
//                    }
//                }
//            })
//        }
//    }

}
