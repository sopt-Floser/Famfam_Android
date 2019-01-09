package com.sopt.famfam.activity

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.View
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.sopt.famfam.R
import com.sopt.famfam.database.FamilyData
import com.sopt.famfam.fragment.DatePickerFragment
import com.sopt.famfam.fragment.DatePickerFragment3
import com.sopt.famfam.get.GetGroupsCreateCodeResponse
import com.sopt.famfam.network.ApplicationController
import com.sopt.famfam.network.NetworkService
import com.sopt.famfam.put.PutEditProfileResponse
import kotlinx.android.synthetic.main.activity_code_generator.*
import kotlinx.android.synthetic.main.activity_more_edit_profile.*
import org.jetbrains.anko.startActivity
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MoreEditProfileActivity : AppCompatActivity() {


    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_more_edit_profile)
        tv_more_editprofile_nickname.setText(FamilyData.userName)
        tv_more_editprofile_statusmessage.setText(FamilyData?.statusMessage)
        tv_more_editprofile_birth_text.setText(FamilyData.birthday?.substring(0, 10))
        var parsedSexType = ""
        if (FamilyData.sexType == 0) {
            parsedSexType = "여자"
        } else {
            parsedSexType = "남자"
        }
        tv_more_editprofile_sexType.setText(parsedSexType)
        setOnClickListener()

    }

//    var nickname : Editable =
//    var statusMessage : Editable = tv_more_editprofile_statusmessage.text
//    var birth : Editable = tv_more_editprofile_birth_text.text
//    var sex : Editable = tv_more_editprofile_sexType.text

    private fun setOnClickListener() {
        edit_nickname_statusMessage_btn.setOnClickListener {
            // 닉네임 수정
            tv_more_editprofile_nickname.isEnabled = true
            tv_more_editprofile_statusmessage.isEnabled = true
            more_edit_complete_btn.visibility = View.VISIBLE
        }
        tv_more_editprofile_birth_edit.setOnClickListener {
            // 생일 수정
            tv_more_editprofile_birth_text.isEnabled = true
            val newFragment = DatePickerFragment3()
            newFragment.show(fragmentManager, "Date Picker")
            more_edit_complete_btn.visibility = View.VISIBLE
        }
        tv_more_editprofile_gender_edit.setOnClickListener {
            // 성별 수정
            tv_more_editprofile_sexType.isEnabled = true
            more_edit_complete_btn.visibility = View.VISIBLE
        }
        more_edit_complete_btn.setOnClickListener {
            //수정완료 통신
            getMoreEditProfileResponse()
        }
    }

    private fun getMoreEditProfileResponse() {
        val token : String = FamilyData.token
        var sexType : Int = 1
        if(tv_more_editprofile_sexType.text.toString() == "남자"){
            sexType = 1
        }else {
            sexType = 0
        }
        var birthDay :String = tv_more_editprofile_birth_text.text.toString()+"T00:00"
        //Json 형식의 객체 만들기
        var jsonObject = JSONObject()
        jsonObject.put("userName", tv_more_editprofile_nickname.text.toString())
        jsonObject.put("birthday", birthDay)
        jsonObject.put("sexType", sexType)
        jsonObject.put("statusMessage", tv_more_editprofile_statusmessage.text.toString())

        //Gson 라이브러리의 Json Parser을 통해 객체를 Json으로!
        val gsonObject = JsonParser().parse(jsonObject.toString()) as JsonObject

        //통신 시작
        val putEditProfileResponse: Call<PutEditProfileResponse> =
            networkService.putEditProfileResponse("application/json", token, gsonObject)
        putEditProfileResponse.enqueue(object : Callback<PutEditProfileResponse> {
            override fun onFailure(call: Call<PutEditProfileResponse>, t: Throwable) {
                Log.e("edit Fail", t.toString())
            }
            override fun onResponse(call: Call<PutEditProfileResponse>, response: Response<PutEditProfileResponse>) {
                if (response.body()!!.message == "회원 정보 수정 성공") {
                    FamilyData.userName = tv_more_editprofile_nickname.text.toString()
                    FamilyData.sexType = sexType
                    FamilyData.statusMessage = tv_more_editprofile_statusmessage.text.toString()
                    FamilyData.birthday = birthDay
                    finish()
                } else {
                    Log.e("error", "error")
                    startActivity<MoreActivity>()
                    finish()
                }
            }
        })
    }
}
