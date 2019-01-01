package com.sopt.famfam.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.ViewPager
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
            4
        )
        vp_intro_act_frag_pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(p0: Int) {

            }

            override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {

            }

            override fun onPageSelected(p0: Int) {
                ci_intro_act_viewPager_indicator.selectDot(p0)
            }

        })//vp_bottom_navi_act_frag_pager.offscreenPageLimit = 3
        ci_intro_act_viewPager_indicator.createDotPanel(4, R.drawable.indicator_dot_off, R.drawable.indicator_dot_on, 0)
    }

}
