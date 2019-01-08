package com.sopt.famfam.database

import android.content.Context
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.sopt.famfam.fragment.HomeAlertFrament
import com.sopt.famfam.fragment.HomeCalendarFragment
import com.sopt.famfam.fragment.HomeStatisticsFragment

class PagerAdapter(manager: FragmentManager, context: Context) : FragmentStatePagerAdapter(manager) {
        var frags = ArrayList<android.support.v4.app.Fragment>()
        var context: Context? = null

        init {
            this.context = context
            frags.add(HomeCalendarFragment())
            frags.add(HomeStatisticsFragment())
            frags.add(HomeAlertFrament())
        }


    override fun getItem(i: Int): android.support.v4.app.Fragment? {

        when (i % 3) {
            0 -> return HomeStatisticsFragment()
            1 -> return HomeCalendarFragment()
            2 -> return HomeAlertFrament()
            else -> return null
        }
    }

    override fun getCount(): Int {
        return Integer.MAX_VALUE
    }
}