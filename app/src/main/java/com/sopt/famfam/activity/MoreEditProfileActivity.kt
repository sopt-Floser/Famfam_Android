package com.sopt.famfam.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.sopt.famfam.R
import com.sopt.famfam.database.FamilyData
import kotlinx.android.synthetic.main.activity_more_edit_profile.*

class MoreEditProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_more_edit_profile)
        tv_more_editprofile_nickname.text = FamilyData.userName
        tv_more_editprofile_statusmessage.text = FamilyData?.statusMessage
        tv_more_editprofile_birth_text.text = FamilyData.birthday?.substring(0,10)
        var parsedSexType = ""
        if(FamilyData.sexType == 0){
            parsedSexType = "여자"
        } else{
            parsedSexType = "남자"
        }
        tv_more_editprofile_sexType.text = parsedSexType

    }

    private fun setOnClickListener() {

    }
}
