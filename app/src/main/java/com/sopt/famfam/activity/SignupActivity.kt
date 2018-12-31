package com.sopt.famfam.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
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
        tv_signup_act_datepicker_btn.setOnClickListener{
            val newFragment = DatePickerFragment()
            newFragment.show(fragmentManager, "Date Picker")
        }
    }
}
