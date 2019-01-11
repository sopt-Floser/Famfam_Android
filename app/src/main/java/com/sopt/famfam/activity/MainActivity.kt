package com.sopt.famfam.activity

import android.content.Context
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.MotionEventCompat
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.RelativeLayout
import com.sopt.famfam.R
import com.sopt.famfam.database.FamilyData
import com.sopt.famfam.fragment.HomeFragment
import com.sopt.famfam.fragment.TodayFragment
import com.sopt.famfam.get.GetUserResponse
import com.sopt.famfam.network.ApplicationController
import com.sopt.famfam.network.NetworkService
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.support.v4.toast
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class MainActivity : AppCompatActivity() {

    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }

    var adapter = PagerAdapter(supportFragmentManager, this)
    var index: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        vp_mainpage.adapter = adapter
        vp_mainpage.offscreenPageLimit = 3
        vp_mainpage.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                index = position
            }

        })
        tl_main_top.setupWithViewPager(vp_mainpage)
        var tab = findViewById<TabLayout>(R.id.tl_main_top)
        var bottomNaviLayout: View = this.layoutInflater.inflate(R.layout.menu_topmenubar, null, false)
        tab.getTabAt(0)!!.customView = bottomNaviLayout.findViewById(R.id.btn_top_home) as RelativeLayout
        tab.getTabAt(1)!!.customView = bottomNaviLayout.findViewById(R.id.btn_top_select_card) as RelativeLayout
        tab.getTabAt(2)?.customView = bottomNaviLayout.findViewById(R.id.btn_top_point) as RelativeLayout
        tab.getTabAt(3)?.customView = bottomNaviLayout.findViewById(R.id.btn_top_setting) as RelativeLayout


        tab.getTabAt(2)?.customView?.setOnTouchListener(View.OnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                }
                MotionEvent.ACTION_UP -> {
                    startActivity<ChatActivity>()
                }
            }
            return@OnTouchListener true
        })
        tab.getTabAt(3)?.customView?.setOnTouchListener(View.OnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                }
                MotionEvent.ACTION_UP -> {
                    //startActivity<ChatActivity>()
                    //달력
                }
            }
            return@OnTouchListener true
        })
    }

    override fun onResume() {
        super.onResume()
        getUserResponse()
    }


    override fun onBackPressed() {
        var curFrags = adapter.getItem(index).childFragmentManager

        val count = curFrags.backStackEntryCount

        if (count == 0) {
            super.onBackPressed()
            //additional code
        } else {
            curFrags.popBackStack()
        }
    }

    class PagerAdapter(manager: FragmentManager, context: Context) : FragmentStatePagerAdapter(manager) {
        var frags = ArrayList<Fragment>()
        var context: Context? = null

        init {
            this.context = context
            frags.add(TodayFragment())
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

    private fun getUserResponse() {
        if (FamilyData.token != null) {
            Log.d("uuuu1", FamilyData.token)
            val getUserResponse =
                networkService.getUserResponse("application/json", FamilyData.token)

            getUserResponse.enqueue(object : Callback<GetUserResponse> {
                override fun onFailure(call: Call<GetUserResponse>, t: Throwable) {
                    Log.e("uuuu1", t.toString())
                }

                override fun onResponse(call: Call<GetUserResponse>, response: Response<GetUserResponse>) {
                    if (response.body()!!.message == "회원 정보 조회 성공") {
                        if(response.body()!!.data.profilePhoto!=null)
                            FamilyData.profilePhoto = response.body()!!.data.profilePhoto
                        else
                            FamilyData.profilePhoto = ""
                        Log.d("uuuu1", "로그인"+FamilyData.profilePhoto)

                        if(response.body()!!.data.backPhoto!=null)
                            FamilyData.backPhoto = response.body()!!.data.backPhoto
                        else
                            FamilyData.backPhoto = ""
                        Log.d("uuuu1", FamilyData.profilePhoto)
                        toast(FamilyData.profilePhoto)
                    } else {
                        toast("fail")
                    }
                }
            })
        }
    }

    //invite_btn

}

class SwipeViewPager : ViewPager {
    var enabled1: Boolean = false

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        if (enabled1) {
            return super.onInterceptTouchEvent(ev)
        } else {
            if (MotionEventCompat.getActionMasked(ev) == MotionEvent.ACTION_MOVE) {
                // ignore move action
            } else {
                if (super.onInterceptTouchEvent(ev)) {
                    super.onTouchEvent(ev)
                }
            }
            return false
        }
    }

    override fun onTouchEvent(ev: MotionEvent): Boolean {
        return if (enabled1) {
            super.onTouchEvent(ev)
        } else {
            MotionEventCompat.getActionMasked(ev) != MotionEvent.ACTION_MOVE && super.onTouchEvent(ev)
        }
    }

    fun setPagingEnabled(enabled: Boolean) {
        this.enabled1 = enabled
    }
}

