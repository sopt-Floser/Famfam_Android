package com.sopt.famfam.activity

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.sopt.famfam.R
import kotlinx.android.synthetic.main.activity_signup.*
import com.sopt.famfam.fragment.DatePickerFragment
import com.sopt.famfam.network.ApplicationController
import com.sopt.famfam.network.NetworkService
import com.sopt.famfam.post.PostConfirmIdResponse
import com.sopt.famfam.post.PostSignUpResponse
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.textColor
import org.jetbrains.anko.toast
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.regex.Pattern


class SignupActivity : AppCompatActivity() {



//    companion object {
//        private var instance: DatePickerFragment? = null
//
//        @Synchronized
//        fun getInstance(year: Int, month: Int, day: Int): DatePickerFragment {
//            if (instance == null) {
//                instance = DatePickerFragment().apply {
//                    arguments = Bundle().apply {
//                        putString("year", year.toString())
//                        putString("month", month.toString())
//                        putString("day", day.toString())
//                    }
//                }
//            }
//            return instance!!
//        }
//    }


    var sexType: Int = 0
    var idCheck : Boolean = false

    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
//        savedInstanceState?.let {
//            year = it.getString("year")
//            month = it.getString("month")
//            day = it.getString("day")
//            Log.e("uuuu1", year)
//            Log.e("uuuu1", month)
//        Log.e("uuuu1", day)
//        }

        setOnBtnClickListener()
        editTextChangeListener()
/*
1# : 아이디

2# : 비밀번호

4# : 정규식 (영문(대소문자 구분), 숫자, 특수문자 조합, 9~12자리)

5# : Pattern 클래스의 compile(), matcher() 함수를 활용하여 Matcher 클래스 생성

7# : 정규식 (같은 문자 4개 이상 사용 불가)

8# : Pattern 클래스의 compile(), matcher() 함수를 활용하여 Matcher 클래스 생성

10# : Matcher 클래스의 matches() 함수를 활용하여 체크, true 일 경우 정규식을 만족함

14# : Matcher 클래스의 find() 함수를 활용하여 체크, true 일 경우 정규식을 만족함

18# : String 클래스의 contains() 함수를 활용하여 Id가 비밀번호 문자열에 있는지 체크함

22# : String 클래스의 contains() 함수를 활용하여 공백문자가 비밀번호 문자열에 있는지 체크함*/

//        val userId = "아이디"
//        val password = "비밀번호"
//
//        var pwPattern = "^(?=.*\\d)(?=.*[~`!@#$%\\^&*()-])(?=.*[a-z])(?=.*[A-Z]).{9,12}$"
//        val matcher = Pattern.compile(pwPattern).matcher(password)
//
//        pwPattern = "(.)\\1\\1\\1"
//        val matcher2 = Pattern.compile(pwPattern).matcher(password)
//
//        if (!matcher.matches()) {
//            if (matcher2.find()) {
//
//            }
//        }


    }

    private fun setOnBtnClickListener() {
        tv_signup_act_datepicker_btn.setOnClickListener {
            val newFragment = DatePickerFragment()
            newFragment.show(fragmentManager, "Date Picker")
        }

        // female -> true, male -> false

        tv_signup_act_sex_female.setOnClickListener {
            sexType = 0
            tv_signup_act_sex_female.setBackgroundResource(R.drawable.signup_act_blue_sex_type)
            val blueColor = "#366ce2"
            tv_signup_act_female_text.setTextColor(Color.parseColor(blueColor))
            tv_signup_act_sex_male.setBackgroundResource(R.drawable.signup_act_gray_sex_type)
            val grayColor = "#9a9a9a"
            tv_signup_act_male_text.setTextColor(Color.parseColor(grayColor))
            tv_signup_act_sex_validation_check_text.visibility = View.INVISIBLE
            Log.d("버튼", sexType.toString())
        }

        tv_signup_act_sex_male.setOnClickListener {
            sexType = 1
            tv_signup_act_sex_male.setBackgroundResource(R.drawable.signup_act_blue_sex_type)
            val grayColor = "#9a9a9a"
            tv_signup_act_female_text.setTextColor(Color.parseColor(grayColor))
            val blueColor = "#366ce2"
            tv_signup_act_male_text.setTextColor(Color.parseColor(blueColor))
            tv_signup_act_sex_female.setBackgroundResource(R.drawable.signup_act_gray_sex_type)
            tv_signup_act_sex_validation_check_text.visibility = View.INVISIBLE
            Log.d("버튼", sexType.toString())
        }

        tv_signup_act_complete_signup.setOnClickListener {
            getSignUpResponse()
            startActivity<SelectActivity>()
            finish()
        }
    }

    private fun editTextChangeListener() {
        // nickname editText
        et_signup_act_input_nickname.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                if (s.isEmpty()) {
                    tv_signup_act_nickname_validation_check_text.visibility = View.VISIBLE
                    iv_signup_act_nickname_validation_check_ok.visibility = View.INVISIBLE
                } else {
                    tv_signup_act_nickname_validation_check_text.visibility = View.INVISIBLE
                    iv_signup_act_nickname_validation_check_ok.visibility = View.VISIBLE
                }
            }
        })
        // id editText
        et_signup_act_input_id.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                postConfirmIdResponse()
                if (idCheck) {
                    tv_signup_act_id_validation_check_text.visibility = View.INVISIBLE
                    iv_signup_act_id_validation_check_ok.visibility = View.VISIBLE
                    if (s.isEmpty()) {
                        tv_signup_act_id_validation_check_text.visibility = View.VISIBLE
                        iv_signup_act_id_validation_check_ok.visibility = View.INVISIBLE
                    }
                }
            }
        })
        // pw editText
        et_signup_act_input_pw.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                if (s.isEmpty()) {
                    tv_signup_act_pw_validation_check_text.visibility = View.VISIBLE
                    iv_signup_act_pw_validation_check_ok.visibility = View.INVISIBLE
                } else {
                    tv_signup_act_pw_validation_check_text.visibility = View.INVISIBLE
                    iv_signup_act_pw_validation_check_ok.visibility = View.VISIBLE
                }
            }
        })
        // pw confirm editText
        et_signup_act_input_confirm_pw.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
            }

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                if (s.isEmpty()) {
                    tv_signup_act_pw_confirm_validation_check_text.visibility = View.VISIBLE
                    iv_signup_act_pw_confirm_validation_check_ok.visibility = View.GONE
                    tv_signup_act_pw_confirm_validation_check_error_text.visibility = View.GONE
                } else {
                    Log.d("비번 1", et_signup_act_input_pw.text.toString())
                    Log.d("비번 2", s.toString())
                    if (et_signup_act_input_pw.text.toString() == s.toString()) {
                        iv_signup_act_pw_confirm_validation_check_ok.visibility = View.VISIBLE
                        tv_signup_act_pw_confirm_validation_check_text.visibility = View.GONE
                        tv_signup_act_pw_confirm_validation_check_error_text.visibility = View.GONE
                    } else {
                        if ("" == s.toString()) {
                            iv_signup_act_pw_confirm_validation_check_ok.visibility = View.GONE
                            tv_signup_act_pw_confirm_validation_check_text.visibility = View.VISIBLE
                            tv_signup_act_pw_confirm_validation_check_error_text.visibility = View.GONE
                        }
                        iv_signup_act_pw_confirm_validation_check_ok.visibility = View.GONE
                        tv_signup_act_pw_confirm_validation_check_text.visibility = View.GONE
                        tv_signup_act_pw_confirm_validation_check_error_text.visibility = View.VISIBLE
                    }
                }
            }
        })
    }

    private fun getSignUpResponse() {
        //EditText에 있는 값 받기
        val hint = tv_signup_act_datepicker_text.hint
        val year = hint.split('.')[0]
        val month = hint.split('.')[1]
        val day = hint.split('.')[2]
        val input_userName: String = et_signup_act_input_nickname.text.toString()
        val input_userId: String = et_signup_act_input_id.text.toString()
        val input_userPw: String = et_signup_act_input_confirm_pw.text.toString()
        val input_userPhone: String = intent.getStringExtra("phoneNumber")
        val input_birthday: String = "$year-$month-$day"+"T00:00"
        val input_sexType: Int = sexType
        //Json 형식의 객체 만들기
        var jsonObject = JSONObject()
        jsonObject.put("userName", input_userName)
        jsonObject.put("userId", input_userId)
        jsonObject.put("userPw", input_userPw)
        jsonObject.put("userPhone", input_userPhone)
        jsonObject.put("birthday", input_birthday)
        jsonObject.put("sexType", input_sexType)
        //Gson 라이브러리의 Json Parser을 통해 객체를 Json으로!
        val gsonObject = JsonParser().parse(jsonObject.toString()) as JsonObject
        Log.e("uuuu1", input_userName)
        Log.e("uuuu1", input_userId)
        Log.e("uuuu1", input_userPw)
        Log.e("uuuu1", input_userPhone)
        Log.e("uuuu1", input_birthday)
        Log.e("uuuu1", input_sexType.toString())

        //통신 시작
        val postSignUpResponse: Call<PostSignUpResponse> =
            networkService.postSignUpResponse("application/json", gsonObject)
        postSignUpResponse.enqueue(object : Callback<PostSignUpResponse> {
            override fun onFailure(call: Call<PostSignUpResponse>, t: Throwable) {
                Log.e("Sign Up Fail", t.toString())
            }

            override fun onResponse(call: Call<PostSignUpResponse>, response: Response<PostSignUpResponse>) {
                if (response.isSuccessful) {
                    var message: String = response.body()!!.message
                    toast(message)
                    finish()
                }
            }
        })
    }

    private fun postConfirmIdResponse() {
        //EditText에 있는 값 받기
        val input_userId: String = et_signup_act_input_id.text.toString()
        //Json 형식의 객체 만들기
        var jsonObject = JSONObject()
        jsonObject.put("userId", input_userId)
        val gsonObject = JsonParser().parse(jsonObject.toString()) as JsonObject

        Log.e("uuuu1", input_userId)


        //통신 시작
        val postConfirmIdResponse: Call<PostConfirmIdResponse> =
            networkService.postConfirmIdResponse("application/json", gsonObject)
        postConfirmIdResponse.enqueue(object : Callback<PostConfirmIdResponse> {
            override fun onFailure(call: Call<PostConfirmIdResponse>, t: Throwable) {
                Log.e("Sign Up Fail", t.toString())
            }

            override fun onResponse(call: Call<PostConfirmIdResponse>, response: Response<PostConfirmIdResponse>) {
                if (response.isSuccessful) {
                    var message: String = response.body()!!.message
                    toast(message)
                    //tv_confirm_id_text
                    if (message == "사용할 수 있는 아이디입니다."){
                        val strColor = "#366ce2"
                        tv_confirm_id_text.setTextColor(Color.parseColor(strColor))
                        idCheck = true
                    } else {
                        val strColor = "#8a8a8a"
                        tv_confirm_id_text.setTextColor(Color.parseColor(strColor))
                        idCheck = false
                    }

//                    finish()
                }
            }
        })
    }
}

