package com.sopt.famfam.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.sopt.famfam.R
import android.widget.AdapterView
import android.widget.Spinner
import android.widget.TextView
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

    }
}
