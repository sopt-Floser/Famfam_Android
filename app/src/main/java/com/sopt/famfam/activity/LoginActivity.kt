package com.sopt.famfam.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.sopt.famfam.R
import com.sopt.famfam.adapter.item.FamilyListItem
import com.sopt.famfam.database.FamilyData
import com.sopt.famfam.database.SharedPreferenceController
import com.sopt.famfam.database.User
import com.sopt.famfam.get.GetGroupUserResponse
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
        if (SharedPreferenceController.getLoginData(this).isNotEmpty()) {
            var tmp = SharedPreferenceController.getLoginData(this).split(",")
            FamilyData.groupId = tmp[0].toInt()
            FamilyData.userId = tmp[1]
            FamilyData.userName = tmp[2]
            FamilyData.statusMessage = tmp[4]
            FamilyData.birthday = tmp[5]
            FamilyData.backPhoto = tmp[6]
            FamilyData.profilePhoto = tmp[7]
            FamilyData.sexType = tmp[8].toInt()

                FamilyData.token = SharedPreferenceController.getAuthorization(this)

            /*
                        FamilyData.groupId = response.body()!!.data.user.groupIdx
                        FamilyData.userId = response.body()!!.data.user.userId
                        FamilyData.userName = response.body()!!.data.user.userName
                        FamilyData.token = token
                        FamilyData.statusMessage = response.body()!!.data.user.statusMessage
                        FamilyData.birthday = response.body()!!.data.user.birthday
                        FamilyData.backPhoto = response.body()!!.data.user?.backPhoto
                        FamilyData.profilePhoto = response.body()!!.data.user?.profilePhoto
                        FamilyData.sexType = response.body()!!.data.user.sexType
             */

        }
        if (SharedPreferenceController.getAuthorization(this).isNotEmpty()) {
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

    private fun getLoginResponse() {
        Log.d("uuuu1", "ok")
        if (et_login_act_id.text.toString().isNotEmpty() && et_login_act_pw.text.toString().isNotEmpty()) {
            Log.d("uuuu1", "ok2")
            Log.d("uuuu1", et_login_act_id.text.toString())
            Log.d("uuuu1", et_login_act_pw.text.toString())
            val input_id = et_login_act_id.text.toString()
            val input_pw = et_login_act_pw.text.toString()
            val jsonObject: JSONObject = JSONObject()
            jsonObject.put("userId", input_id)
            jsonObject.put("userPw", input_pw)
            val gsonObject: JsonObject = JsonParser().parse(jsonObject.toString()) as JsonObject

            val postLogInResponse = networkService.postLoginResponse("application/json", gsonObject)
            postLogInResponse.enqueue(object : Callback<PostLogInResponse> {
                override fun onFailure(call: Call<PostLogInResponse>, t: Throwable) {
                    Log.e("Login fail", t.toString())
                }

                override fun onResponse(call: Call<PostLogInResponse>, response: Response<PostLogInResponse>) {
                    Log.d("uuuu1", response.message())
                    if (response.isSuccessful) {
                        Log.d("Login", response.body().toString())
                        if(response.body()!!.status==400)
                            return
                        val token = response.body()!!.data.token
                        FamilyData.groupId = response.body()!!.data.user.groupIdx
                        FamilyData.userId = response.body()!!.data.user.userId
                        FamilyData.userName = response.body()!!.data.user.userName
                        FamilyData.token = token
                        FamilyData.statusMessage = response.body()!!.data.user.statusMessage
                        FamilyData.birthday = response.body()!!.data.user.birthday
                        val user_profilePhoto = response.body()!!.data.user.profilePhoto
                        if(response.body()!!.data.user.profilePhoto!=null)
                            FamilyData.profilePhoto = response.body()!!.data.user.profilePhoto
                        else
                            FamilyData.profilePhoto = ""
                        Log.d("uuuu1", "로그인"+FamilyData.profilePhoto)

                        if(response.body()!!.data.user.backPhoto!=null)
                            FamilyData.backPhoto = response.body()!!.data.user.backPhoto
                        else
                            FamilyData.backPhoto = ""

                        FamilyData.sexType = response.body()!!.data.user.sexType
                        Log.e("uuuu1", token)

                        SharedPreferenceController.setLoginData(
                            this@LoginActivity,
                            FamilyData.groupId.toString() + "," +
                                    FamilyData.userId + "," +
                                    FamilyData.userName + "," +
                                    FamilyData.statusMessage + "," +
                                    FamilyData.birthday + "," +
                                    FamilyData.sexType + "," +
                                    FamilyData.profilePhoto + "," +
                                    FamilyData.backPhoto

                        )
                        //저번 시간에 배웠던 SharedPreference에 토큰을 저장! 왜냐하면 토큰이 필요한 통신에 사용하기 위해서!!
                        SharedPreferenceController.setAuthorization(this@LoginActivity, token)
                        toast(SharedPreferenceController.getAuthorization(this@LoginActivity))
                        getGroupMemberListResponse()
//                        startActivity<MainActivity>()
////                        finish()
                        val groupIdx = FamilyData.groupId.toString()
                        Log.e("uuuu1", groupIdx)
                        if (groupIdx == "-1") {
                            startActivity<SelectActivity>()
                        } else {
                            startActivity<MainActivity>()
                            finish()
                        }
                    }
                }
            })
        }
    }

    private fun getGroupMemberListResponse() {
        val postLogInResponse = networkService.getGroupUserResponse("application/json", FamilyData.token)
        postLogInResponse.enqueue(object : Callback<GetGroupUserResponse> {
            override fun onFailure(call: Call<GetGroupUserResponse>, t: Throwable) {
                Log.e("Login fail", t.toString())
            }

            override fun onResponse(call: Call<GetGroupUserResponse>, response: Response<GetGroupUserResponse>) {
                if (response.isSuccessful) {
                    val data = response.body()!!.data.users
                    var list = ArrayList<FamilyListItem>();
                    var i: Int = 0
                    for (item in data) {
                        Log.d("uuuu1", item.toString())
                        if (item.profilePhoto.isNullOrEmpty()) {
                            list.add(
                                FamilyListItem(
                                    item.userIdx,
                                    "",
                                    item.userName
                                )
                            )
                        } else {
                            list.add(FamilyListItem(item.userIdx, item.profilePhoto, item.userName))
                            Log.d("asdphoto", item.profilePhoto)
                        }
                    }

//                    for (user in data) {
//                        FamilyData.users.add(
//                            User(
//                                user.userIdx,
//                                user.userId,
//                                user.userName,
//                                user.userPhone,
//                                user.birthday,
//                                user.sexType,
//                                user.statusMessage,
//                                user.profilePhoto,
//                                user.backPhoto,
//                                user.groupIdx
//                            )
//                        )
//                    }
                    Log.d("asd", "여긴되나요")
                }
            }
        })
    }

}
