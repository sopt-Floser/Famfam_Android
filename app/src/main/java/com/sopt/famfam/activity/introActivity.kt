package com.sopt.famfam.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.sopt.famfam.R
import com.sopt.famfam.fragment.IntroFragmentStatePagerAdapter
import kotlinx.android.synthetic.main.activity_intro.*

class introActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        configureBottomNavigation()
    }

    private fun configureBottomNavigation() {
        vp_intro_act_frag_pager.adapter = IntroFragmentStatePagerAdapter(
            supportFragmentManager,
            3
        ) //vp_bottom_navi_act_frag_pager.offscreenPageLimit = 3
    }

}
