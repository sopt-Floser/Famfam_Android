package com.sopt.famfam.fragment

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.bumptech.glide.Glide
import com.sopt.famfam.R
import com.sopt.famfam.adapter.FamilyListAdapter
import com.sopt.famfam.adapter.item.FamilyListItem
import kotlinx.android.synthetic.main.fragment_home.view.*
import java.util.ArrayList

class HomeFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_home, container, false)
        var familylist = view.rv_home_family
        val content = view.vp_home_main_content

        var list = ArrayList<FamilyListItem>();
        list.add(FamilyListItem(0,"","엄마"))
        list.add(FamilyListItem(0,"","아빠"))
        list.add(FamilyListItem(0,"","동생"))
        familylist.adapter=FamilyListAdapter(context!!,list);
        familylist.layoutManager= LinearLayoutManager(context, LinearLayout.HORIZONTAL, false)
        val padding= 80
        content.setPadding(160,0,160,0)
        content.clipToPadding=false
        content.pageMargin=80
        content.adapter = PagerAdapter(fragmentManager!!, activity!!)
        content.offscreenPageLimit=3
        return view
    }

    class PagerAdapter(manager: FragmentManager, context: Context) : FragmentStatePagerAdapter(manager) {
        var frags = ArrayList<android.support.v4.app.Fragment>()
        var context: Context? = null

        init {
            this.context = context
            frags.add(HomeCalendarFragment())

            frags.add(HomeCalendarFragment())
            frags.add(HomeCalendarFragment())

        }

        override fun getItem(i: Int): android.support.v4.app.Fragment {
            return frags[i]
        }

        override fun getCount(): Int {
            return frags.size
        }
    }
}