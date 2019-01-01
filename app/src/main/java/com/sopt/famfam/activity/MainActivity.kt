package com.sopt.famfam.activity

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.view.View
import android.widget.RelativeLayout
import com.sopt.famfam.R
import com.sopt.famfam.fragment.HomeFragment
import kotlinx.android.synthetic.main.activity_main.*
import java.util.ArrayList

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        vp_mainpage.adapter=PagerAdapter(supportFragmentManager,this)
        tl_main_top.setupWithViewPager(vp_mainpage)
        var tab = findViewById<TabLayout>(R.id.tl_main_top)
        var bottomNaviLayout : View = this.layoutInflater.inflate(R.layout.menu_topmenubar, null, false)
        tab.getTabAt(0)!!.customView = bottomNaviLayout.findViewById(R.id.btn_top_home) as RelativeLayout
        tab.getTabAt(1)!!.customView = bottomNaviLayout.findViewById(R.id.btn_top_select_card) as RelativeLayout
        tab.getTabAt(2)!!.customView = bottomNaviLayout.findViewById(R.id.btn_top_point) as RelativeLayout
        tab.getTabAt(3)!!.customView = bottomNaviLayout.findViewById(R.id.btn_top_setting) as RelativeLayout
    }

    class PagerAdapter(manager: FragmentManager, context: Context) : FragmentStatePagerAdapter(manager) {
        var frags = ArrayList<Fragment>()
        var context: Context? = null

        init {
            this.context = context
            frags.add(HomeFragment())
            frags.add(HomeFragment())
            frags.add(HomeFragment())
            frags.add(HomeFragment())
        }

        override fun getItem(i: Int): android.support.v4.app.Fragment {
            return frags[i]
        }

        override fun getCount(): Int {
            return frags.size
        }
    }
}
