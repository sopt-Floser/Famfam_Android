package com.sopt.famfam.activity

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.BoringLayout
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import com.sopt.famfam.R
import kotlinx.android.synthetic.main.activity_signup.*
import com.sopt.famfam.fragment.DatePickerFragment
import org.jetbrains.anko.sdk27.coroutines.textChangedListener


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
        var sexType: Boolean
        // female -> true male -> false

        tv_signup_act_sex_female.setOnClickListener {

            sexType = true
            tv_signup_act_sex_female.setBackgroundResource(R.drawable.signup_act_blue_sex_type)
            val blueColor = "#366ce2"
            tv_signup_act_female_text.setTextColor(Color.parseColor(blueColor))
            tv_signup_act_sex_male.setBackgroundResource(R.drawable.signup_act_gray_sex_type)
            val grayColor = "#9a9a9a"
            tv_signup_act_male_text.setTextColor(Color.parseColor(grayColor))
            tv_signup_act_sex_validation_check_text.visibility = View.INVISIBLE
            Log.d("버튼",sexType.toString())
        }


        tv_signup_act_sex_male.setOnClickListener {
            sexType = false
            tv_signup_act_sex_male.setBackgroundResource(R.drawable.signup_act_blue_sex_type)
            val grayColor = "#9a9a9a"
            tv_signup_act_female_text.setTextColor(Color.parseColor(grayColor))
            val blueColor = "#366ce2"
            tv_signup_act_male_text.setTextColor(Color.parseColor(blueColor))
            tv_signup_act_sex_female.setBackgroundResource(R.drawable.signup_act_gray_sex_type)
            tv_signup_act_sex_validation_check_text.visibility = View.INVISIBLE
            Log.d("버튼",sexType.toString())
        }

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
                    iv_signup_act_nickname_validation_check_ok.visibility = View.INVISIBLE
                } else {
                    tv_signup_act_nickname_validation_check_text.visibility = View.INVISIBLE
                    iv_signup_act_nickname_validation_check_ok.visibility = View.VISIBLE
                }
            }
        })
//        // birth editText
//        tv_signup_act_datepicker_text.addTextChangedListener(object : TextWatcher {
//            override fun afterTextChanged(s: Editable) {}
//
//            override fun beforeTextChanged(
//                s: CharSequence, start: Int,
//                count: Int, after: Int
//            ) {
//
//            }
//
//            override fun onTextChanged(
//                s: CharSequence, start: Int,
//                before: Int, count: Int
//            ) {
////                tvSample.setText("Text in EditText : "+s)
//                if (s !== "               년               월               일") {
//                    tv_signup_act_nickname_validation_check_text.visibility = View.INVISIBLE
//                } else {
//                    tv_signup_act_nickname_validation_check_text.visibility = View.VISIBLE
//                }
//            }
//        })

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
//                tvSample.setText("Text in EditText : "+s)
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
//                tvSample.setText("Text in EditText : "+s)
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
//                tvSample.setText("Text in EditText : "+s)
                if (s.isEmpty()) {
                    tv_signup_act_pw_confirm_validation_check_text.visibility = View.VISIBLE
                    iv_signup_act_pw_confirm_validation_check_ok.visibility = View.GONE
                    tv_signup_act_pw_confirm_validation_check_error_text.visibility = View.GONE
                } else {
                    Log.d("비번 1", et_signup_act_input_pw.text.toString())
                    Log.d("비번 2", s.toString())
                    if(et_signup_act_input_pw.text.toString() == s.toString()){
                        iv_signup_act_pw_confirm_validation_check_ok.visibility = View.VISIBLE
                        tv_signup_act_pw_confirm_validation_check_text.visibility = View.GONE
                        tv_signup_act_pw_confirm_validation_check_error_text.visibility = View.GONE
                    } else {
                        if("" == s.toString()){
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
