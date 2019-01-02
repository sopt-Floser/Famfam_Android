package com.sopt.famfam.activity

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.sopt.famfam.R
import kotlinx.android.synthetic.main.activity_signup.*
import com.sopt.famfam.fragment.DatePickerFragment


class SignupActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        setOnBtnClickListener()
        editTextChangeListener()
    }

    private fun setOnBtnClickListener() {
        tv_signup_act_datepicker_btn.setOnClickListener {
            val newFragment = DatePickerFragment()
            newFragment.show(fragmentManager, "Date Picker")
        }
        tv_signup_act_sex_female.setOnClickListener {
            tv_signup_act_sex_female.setBackgroundResource(R.drawable.certification_act_blue_request_authorization_code)
            val blueColor = "#366ce2"
            tv_signup_act_female_text.setTextColor(Color.parseColor(blueColor))
        }

        tv_signup_act_sex_male.setOnClickListener {
            tv_signup_act_sex_male.setBackgroundResource(R.drawable.certification_act_blue_request_authorization_code)
            val blueColor = "#366ce2"
            tv_signup_act_male_text.setTextColor(Color.parseColor(blueColor))
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

    private fun editTextChangeListener() {
        // nickname editText
        et_signup_act_input_nickname.addTextChangedListener(object : TextWatcher {
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
//                tvSample.setText("Text in EditText : "+s)
                if (s.isEmpty()) {
                    tv_signup_act_nickname_validation_check_text.visibility = View.VISIBLE
                } else {
                    tv_signup_act_nickname_validation_check_text.visibility = View.INVISIBLE
                }
            }
        })



    }
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

