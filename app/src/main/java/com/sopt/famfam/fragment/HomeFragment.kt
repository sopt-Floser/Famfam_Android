package com.sopt.famfam.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import androidx.recyclerview.widget.LinearLayoutManager
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
import com.sopt.famfam.OnBackPressListener



class HomeFragment : Fragment() {
    override fun onResume() {
        super.onResume()
    }
    lateinit var content : ViewPager
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_home, container, false)

        var familylist = view.rv_home_family
        content = view.vp_home_main_content
        var list = ArrayList<FamilyListItem>();
        list.add(FamilyListItem(0,"","엄마"))
        list.add(FamilyListItem(0,"","아빠"))
        list.add(FamilyListItem(0,"","동생"))
        familylist.adapter=FamilyListAdapter(context!!,list);
        familylist.layoutManager=
                LinearLayoutManager(context, LinearLayout.HORIZONTAL, false)
        val padding= 80
        content.setPadding(160,0,160,0)
        content.clipToPadding=false
        content.pageMargin=80
        content.adapter = PagerAdapter(childFragmentManager!!, activity!!)
        content.offscreenPageLimit=3
        return view
    }

    class PagerAdapter(manager: FragmentManager, context: Context) : FragmentStatePagerAdapter(manager) {
        var frags = ArrayList<Fragment>()
        var context: Context? = null

        init {
            this.context = context
            frags.add(HomeCalendarFragment())
            frags.add(HomeStatisticsFragment())
            frags.add(HomeStatisticsFragment())
        }

        override fun getItem(i: Int): Fragment {
            return frags[i]
        }

        override fun getCount(): Int {
            return frags.size
        }
    }

}