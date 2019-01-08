package com.sopt.famfam.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.sopt.famfam.R
import kotlinx.android.synthetic.main.activity_select.*
import org.jetbrains.anko.startActivity

class SelectActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select)
        setOnBtnClickListener()
    }

    private fun setOnBtnClickListener() {
        select_act_create_btn.setOnClickListener {
            startActivity<MainActivity>()
            finish()
        }
        select_act_join_btn.setOnClickListener {
            startActivity<JoinActivity>()
        }
    }
}