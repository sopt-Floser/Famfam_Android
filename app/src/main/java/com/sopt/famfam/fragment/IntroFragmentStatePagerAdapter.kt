package com.sopt.famfam.fragment

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter


class IntroFragmentStatePagerAdapter(fm: FragmentManager, val fragmentCount: Int) : FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): Fragment? {
        when (position) {
            0 -> return IntroFirstFragment()
            1 -> return IntroSecondFragment()
            2 -> return IntroThirdFragment()
            3 -> return IntroFourthFragment()
            else -> return null
        }

    }

    override fun getCount(): Int = fragmentCount
}