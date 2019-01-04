package com.sopt.famfam.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.sopt.famfam.R
import com.sopt.famfam.database.SharedPreferenceController
import com.sopt.famfam.network.ApplicationController
import com.sopt.famfam.network.NetworkService
import com.sopt.famfam.post.PostLogInResponse
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setOnBtnClickListener()

        if (SharedPreferenceController.getAuthorization(this).isNotEmpty()){
            startActivity<MainActivity>()
        }

    }

    private fun setOnBtnClickListener() {
        btn_login_act_signin.setOnClickListener {
            getLoginResponse()
        }
        tv_login_act_find_id.setOnClickListener {
            startActivity<FindIdActivity>()
        }
        tv_login_act_find_pw.setOnClickListener {
            startActivity<FindPwActivity>()
        }
    }

    private fun getLoginResponse(){
        if (et_login_act_id.text.toString().isNotEmpty() && et_login_act_pw.text.toString().isNotEmpty()){
            val input_id = et_login_act_id.text.toString()
            val input_pw = et_login_act_pw.text.toString()
            val jsonObject : JSONObject = JSONObject()
            jsonObject.put("userId", input_id)
            jsonObject.put("userPw", input_pw)
            val gsonObject : JsonObject = JsonParser().parse(jsonObject.toString()) as JsonObject

            val postLogInResponse = networkService.postLoginResponse("application/json", gsonObject)
            postLogInResponse.enqueue(object : Callback<PostLogInResponse> {
                override fun onFailure(call: Call<PostLogInResponse>, t: Throwable) {
                    Log.e("Login fail", t.toString())
                }

                override fun onResponse(call: Call<PostLogInResponse>, response: Response<PostLogInResponse>) {
                    if (response.isSuccessful){
                        val token = response.body()!!.data.token
                        //저번 시간에 배웠던 SharedPreference에 토큰을 저장! 왜냐하면 토큰이 필요한 통신에 사용하기 위해서!!
                        SharedPreferenceController.setAuthorization(this@LoginActivity, token)
                        toast(SharedPreferenceController.getAuthorization(this@LoginActivity))
                        startActivity<MainActivity>()
                        finish()
                    }
                }
            })
        }
    }

}
