package com.sopt.famfam.activity

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.sopt.famfam.R
import com.sopt.famfam.fragment.HomeFragment
import kotlinx.android.synthetic.main.activity_main.*
import java.util.ArrayList

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        vp_mainpage.adapter=PagerAdapter(supportFragmentManager,this)
    }

    class PagerAdapter(manager: FragmentManager, context: Context) : FragmentStatePagerAdapter(manager) {
        var frags = ArrayList<Fragment>()
        var context: Context? = null

        init {
            this.context = context
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
