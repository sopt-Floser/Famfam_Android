package com.sopt.famfam.fragment

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter


class IntroFragmentStatePagerAdapter(fm: FragmentManager, val fragmentCount: Int) : FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> return IntroFirstFragment()
            1 -> return IntroSecondFragment()
            2 -> return IntroThirdFragment()
            3 -> return IntroFourthFragment()
            else -> return IntroFirstFragment()
        }
    }

    override fun getCount(): Int = fragmentCount
}