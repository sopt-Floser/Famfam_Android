package com.sopt.famfam.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.sopt.famfam.R
import com.sopt.famfam.database.FamilyData
import com.sopt.famfam.database.SharedPreferenceController
import com.sopt.famfam.network.ApplicationController
import com.sopt.famfam.network.NetworkService
import kotlinx.android.synthetic.main.activity_more.*
import kotlinx.android.synthetic.main.activity_more.view.*
import kotlinx.android.synthetic.main.activity_policies.*
import org.jetbrains.anko.startActivity

class MoreActivity : AppCompatActivity() {

    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_more)
        setOnClickListener()

        if (FamilyData.profilePhoto == "") {
            val requestOptions1 = RequestOptions()
            Glide.with(applicationContext)
                .setDefaultRequestOptions(requestOptions1)
                .load(R.drawable.myimg)
                .thumbnail(0.5f)
                .into(iv_more_profile_img)
        } else {
            val requestOptions1 = RequestOptions()
            requestOptions1.placeholder(R.drawable.myimg)
            Glide.with(applicationContext)
                .setDefaultRequestOptions(requestOptions1)
                .load(FamilyData.profilePhoto)
                .thumbnail(0.5f)
                .into(iv_more_profile_img)

        }

        if (FamilyData.backPhoto == "") {
            val requestOptions2 = RequestOptions()
            Glide.with(applicationContext)
                .setDefaultRequestOptions(requestOptions2)
                .load(R.drawable.mybackimg)
                .thumbnail(0.5f)
                .into(iv_more_bg)
        } else {
            val requestOptions2 = RequestOptions()
            requestOptions2.placeholder(R.drawable.mybackimg)
            Glide.with(applicationContext)
                .setDefaultRequestOptions(requestOptions2)
                .load(FamilyData.backPhoto)
                .thumbnail(0.5f)
                .into(iv_more_bg)
        }
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
            startActivity<PoliciesActivity>()

        }
        btn_more_editprofile.setOnClickListener {
            startActivity<MoreEditProfileActivity>()
            finish()
        }
        btn_more_account.setOnClickListener {
            startActivity<AccountSecurityActivity>()
            finish()
        }
        btn_more_cc1.setOnClickListener {
            startActivity<PersonalInformationProtectionActivity>()
        }
    }
}
