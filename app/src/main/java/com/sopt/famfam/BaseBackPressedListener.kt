package com.sopt.famfam

import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager


class BaseBackPressedListener(private val activity: FragmentActivity) : OnBackPressedListener {
    override fun onBackPressed(): Boolean {
        activity.supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        return false
    }
}