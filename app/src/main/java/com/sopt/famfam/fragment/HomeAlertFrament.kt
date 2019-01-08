package com.sopt.famfam.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sopt.famfam.R
import com.sopt.famfam.activity.AlarmActivity
import org.jetbrains.anko.support.v4.startActivity

class HomeAlertFrament : Fragment(){
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_home_alert, container, false)
        view.setOnClickListener {
            startActivity<AlarmActivity>()
        }
        return view

    }
}
