package com.sopt.famfam.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.sopt.famfam.R
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.startActivity

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setOnBtnClickListener()
    }

    private fun setOnBtnClickListener() {
        btn_login_act_signin.setOnClickListener {
            startActivity<MainActivity>()
            finish()
        }
    }

}
