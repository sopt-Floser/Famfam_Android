package com.sopt.famfam.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.sopt.famfam.R
import kotlinx.android.synthetic.main.activity_resetting_pw.*
import org.jetbrains.anko.startActivity

class ResettingPwActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resetting_pw)
        setOnBtnClickListener()
    }

    private fun setOnBtnClickListener() {
        tv_resetting_pw_btn.setOnClickListener {
            if(et_resetting_pw_act_input_user_pw.text.toString() == et_resetting_pw_act_input_user_pw_confirm.text.toString()){
                // 패스워드 재설정 통신 달기
                startActivity<LoginActivity>()
                finish()
            }

        }
    }

}
