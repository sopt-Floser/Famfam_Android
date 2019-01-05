package com.sopt.famfam.activity

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import com.sopt.famfam.R
import kotlinx.android.synthetic.main.activity_signup.*
import com.sopt.famfam.fragment.DatePickerFragment
import org.jetbrains.anko.startActivity
import java.util.regex.Pattern


class SignupActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

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
        var sexType: Int
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
                if (s.isEmpty()) {
                    tv_signup_act_id_validation_check_text.visibility = View.VISIBLE
                    iv_signup_act_id_validation_check_ok.visibility = View.INVISIBLE
                } else {
                    tv_signup_act_id_validation_check_text.visibility = View.INVISIBLE
                    iv_signup_act_id_validation_check_ok.visibility = View.VISIBLE
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
}
