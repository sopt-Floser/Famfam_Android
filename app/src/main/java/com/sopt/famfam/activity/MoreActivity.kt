package com.sopt.famfam.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import com.sopt.famfam.R
import kotlinx.android.synthetic.main.activity_more.*
import org.jetbrains.anko.startActivity

class MoreActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_more)
        setOnClickListener()
    }

    private fun setOnClickListener() {
        btn_more_version.setOnClickListener{
            startActivity<MoreVersionActivity>()
        }

        btn_more_alert_setting.setOnClickListener{
            // 알람 성정
        }

        btn_more_cc.setOnClickListener{
            // 이용정보
        }
        btn_more_editprofile.setOnClickListener {
            startActivity<MoreEditProfileActivity>()
        }
    }
    private fun addFragment(fragment : Fragment){
//        val transaction : FragmentTransaction = supportFragmentManager.beginTransaction()
//        transaction.add(, fragment)
//        transaction.commit()
    }
}
