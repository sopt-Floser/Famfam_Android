package com.sopt.famfam.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.sopt.famfam.R
import android.widget.AdapterView
import kotlinx.android.synthetic.main.activity_certification.*
import org.jetbrains.anko.startActivity


class CertificationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_certification)

        setOnBtnClickListener()
    }

    private fun setOnBtnClickListener() {
        tv_certification_act_request_code_btn.setOnClickListener {
            if (tv_certification_act_request_code_btn_text.text.toString() == "인증번호 요청하기") {
                et_certification_act_input_code.requestFocus()
                certification_act_request_code_layout.visibility = View.VISIBLE
                et_certification_act_phone_number_layout.setBackgroundResource(R.drawable.certification_act_gray_request_authorization_code)
                tv_certification_act_request_code_btn_text.text = "입력 완료"
            } else {
                startActivity<SignupActivity>()
            }
        }
    }
}
