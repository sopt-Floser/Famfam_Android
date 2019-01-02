package com.sopt.famfam.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.sopt.famfam.R
import kotlinx.android.synthetic.main.activity_access_terms.*
import kotlinx.android.synthetic.main.activity_certification.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.startActivityForResult

class AccessTermsActivity : AppCompatActivity() {
    var checkFirst : Boolean = false
    var checkSecond : Boolean = false
    var checkthird : Boolean = false
    var checkFourth : Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_access_terms)
        setOnBtnClickListener()

    }

    private fun setOnBtnClickListener() {
        iv_access_terms_act_second_btn.setOnClickListener {
            startActivityForResult<PoliciesActivity>(1004, "message" to "success")
        }
        iv_access_terms_act_third_btn.setOnClickListener {
            startActivityForResult<PersonalInformationProtectionActivity>(1004, "message" to "success")
        }
        iv_access_terms_act_fifth_btn.setOnClickListener {
            iv_access_terms_act_fifth_btn.setImageResource(R.drawable.icon_check_circle)
            checkthird = true
        }
        iv_access_terms_act_sixth_btn.setOnClickListener {
            iv_access_terms_act_sixth_btn.setImageResource(R.drawable.icon_check_circle)
            checkFourth = true
        }
        tv_access_terms_act_first_complete_btn.setOnClickListener {
            if(checkFirst && checkSecond){
                startActivity<CertificationActivity>()
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == 201) {
            iv_access_terms_act_second_btn.setImageResource(R.drawable.icon_check_circle)
            checkFirst = true
        } else if (resultCode == 203) {
            iv_access_terms_act_third_btn.setImageResource(R.drawable.icon_check_circle)
            checkSecond = true
        }
    }
}
