package com.sopt.famfam.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.sopt.famfam.R
import com.sopt.famfam.database.FamilyData
import com.sopt.famfam.database.SharedPreferenceController
import com.sopt.famfam.delete.DeleteUserResponse
import com.sopt.famfam.get.GetGroupsCreateCodeResponse
import com.sopt.famfam.network.ApplicationController
import com.sopt.famfam.network.NetworkService
import kotlinx.android.synthetic.main.activity_account_security.*
import kotlinx.android.synthetic.main.activity_code_generator.*
import org.jetbrains.anko.startActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AccountSecurityActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_security)
        setOnClickListenr()
    }


    private fun setOnClickListenr () {
        membership_withdrawal_btn.setOnClickListener {
            // 회원탈퇴
            startActivity<MoreSecedeActivity>()
        }
        logout_btn.setOnClickListener {
            // logout
            SharedPreferenceController.clearUserSharedPreferences(this)
            startActivity<IntroActivity>()
            finish()
        }
        tv_more_disconnect_btn.setOnClickListener {
            // 연결끊기
            startActivity<MoreDisconnectActivity>()
        }
        btn_back.setOnClickListener {
            finish()
        }
    }


}
