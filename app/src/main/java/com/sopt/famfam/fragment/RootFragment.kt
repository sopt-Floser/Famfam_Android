package com.sopt.famfam.fragment

import android.support.v4.app.Fragment
import com.sopt.famfam.BackPressImpl
import com.sopt.famfam.OnBackPressedListener

open class RootFragment : Fragment(), OnBackPressedListener {

    override fun onBackPressed(): Boolean {
        return BackPressImpl(this).onBackPressed()
    }
}