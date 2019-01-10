package com.sopt.famfam.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.sopt.famfam.R
import kotlinx.android.synthetic.main.activity_more_version.*

class MoreVersionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_more_version)
        btn_back.setOnClickListener {
            finish()
        }
    }
}
