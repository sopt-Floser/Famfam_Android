package com.sopt.famfam.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import com.sopt.famfam.R
import kotlinx.android.synthetic.main.activity_signup.*
import com.sopt.famfam.fragment.DatePickerFragment


class SignupActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        setOnBtnClickListener()

    }

    private fun setOnBtnClickListener() {
        tv_signup_act_datepicker_btn.setOnClickListener {
            val newFragment = DatePickerFragment()
            newFragment.show(fragmentManager, "Date Picker")
        }


//        et_signup_act_input_nickname.setOnEditorActionListener() { v, action, event ->
//            if(action== EditorInfo.IME_ACTION_DONE){
//                val nickname: String = et_signup_act_input_nickname.text.toString()
//                if (nickname.isEmpty()) {
//                    tv_signup_act_nickname_validation_check_text.visibility = View.VISIBLE
//                } else {
//                    tv_signup_act_nickname_validation_check_text.visibility = View.INVISIBLE
//                }
//                true
//            } else {
//                false
//            }
//        }
    }

//            switch (actionId) {
//                case EditorInfo.IME_ACTION_SEARCH:
//                // 검색 동작
//                break;
//                default:
//                // 기본 엔터키 동작
//                return false;
//            }
//            return true;
}
