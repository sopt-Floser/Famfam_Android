package com.sopt.famfam.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.sopt.famfam.R
import android.widget.AdapterView
import kotlinx.android.synthetic.main.activity_certification.*


class CertificationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_certification)
        sp_certification_act_select_telecom.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                var telecom = parent!!.getItemAtPosition(position) // 통신사 뽑아내기
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                //
            }
        }
        setOnBtnClickListener()
    }

    private fun setOnBtnClickListener() {
        tv_certification_act_request_code_btn.setOnClickListener {
            et_certification_act_input_code.requestFocus()
            certification_act_request_code_layout.visibility = View.VISIBLE
            et_certification_act_phone_number_layout.setBackgroundResource(R.drawable.certification_act_gray_request_authorization_code)
        }
    }
}
