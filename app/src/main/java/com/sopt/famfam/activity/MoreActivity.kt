package com.sopt.famfam.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.sopt.famfam.R
import com.sopt.famfam.database.FamilyData
import com.sopt.famfam.database.SharedPreferenceController
import com.sopt.famfam.network.ApplicationController
import com.sopt.famfam.network.NetworkService
import kotlinx.android.synthetic.main.activity_more.*
import kotlinx.android.synthetic.main.activity_more.view.*
import org.jetbrains.anko.startActivity

class MoreActivity : AppCompatActivity() {

    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_more)
        setOnClickListener()

        val requestOptions1 = RequestOptions()
           requestOptions1.placeholder(R.drawable.myimg)
        Glide.with(applicationContext)
            .setDefaultRequestOptions(requestOptions1)
            .load(FamilyData.profilePhoto)
            .thumbnail(0.5f)
            .into(iv_more_profile_img)

        val requestOptions2 = RequestOptions()
        requestOptions1.placeholder(R.drawable.mybackimg)
        Glide.with(applicationContext)
            .setDefaultRequestOptions(requestOptions2)
            .load(FamilyData.backPhoto)
            .thumbnail(0.5f)
            .into(iv_more_bg)
        tv_more_nickname.text = FamilyData.userName
        tv_more_statusMessage.text = FamilyData.statusMessage
    }


    private fun setOnClickListener() {
        btn_more_version.setOnClickListener {
            startActivity<MoreVersionActivity>()
        }

        btn_more_alert_setting.setOnClickListener {
            // 알람 성정
        }

        btn_more_cc.setOnClickListener {
            // 이용정보
        }
        btn_more_editprofile.setOnClickListener {
            startActivity<MoreEditProfileActivity>()
            finish()
        }
        btn_more_account.setOnClickListener {
            startActivity<AccountSecurityActivity>()
            finish()
        }
    }
}
