package com.sopt.famfam.fragment

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.google.firebase.iid.FirebaseInstanceId
import com.sopt.famfam.R
import com.sopt.famfam.activity.CodeGeneratorActivity
import com.sopt.famfam.activity.MoreActivity
import com.sopt.famfam.activity.MoreEditProfileActivity
import com.sopt.famfam.adapter.FamilyListAdapter
import com.sopt.famfam.adapter.item.FamilyListItem
import com.sopt.famfam.database.FamilyData
import com.sopt.famfam.get.GetGroupUserResponse
import com.sopt.famfam.network.ApplicationController
import com.sopt.famfam.network.NetworkService
import kotlinx.android.synthetic.main.fragment_home.view.*
import org.jetbrains.anko.support.v4.startActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeFragment : Fragment() {
    lateinit var FamilyListAdapter: FamilyListAdapter
    override fun onResume() {
        super.onResume()
    }

    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }
    lateinit var content: ViewPager
    var familylist: RecyclerView? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        getGroupMemberListResponse()
        // 가족 리스트 통신
        var view = inflater.inflate(R.layout.fragment_home, container, false)
        // 패밀리 붙이기
        familylist = view.rv_home_family

        val transaction = childFragmentManager.beginTransaction()
        view.invite_btn.setOnClickListener {
            startActivity<CodeGeneratorActivity>()
        }
        view.home_act_setting_btn.setOnClickListener {
            // Store the Fragment in stack
            startActivity<MoreActivity>()
        }
        content = view.vp_home_main_content


        val padding = 80
        content.setPadding(160, 0, 160, 0)
        content.clipToPadding = false
        content.pageMargin = 80
        content.adapter = PagerAdapter(childFragmentManager!!, activity!!)

        content.offscreenPageLimit = 3
        content.setCurrentItem(100, true)

        Log.d("asd", FirebaseInstanceId.getInstance().getToken())

        view.iv_home_famliy_my.setOnClickListener {
            startActivity<MoreEditProfileActivity>()
        }


        return view
    }


    private fun getGroupMemberListResponse() {
        val postLogInResponse = networkService.getGroupUserResponse("application/json", FamilyData.token)
        postLogInResponse.enqueue(object : Callback<GetGroupUserResponse> {
            override fun onFailure(call: Call<GetGroupUserResponse>, t: Throwable) {
                Log.e("Login fail", t.toString())
            }

            override fun onResponse(call: Call<GetGroupUserResponse>, response: Response<GetGroupUserResponse>) {
                if (response.isSuccessful) {
                    val data = response.body()!!.data.users
                    var list = ArrayList<FamilyListItem>();
                    var i: Int = 0
                    for (item in data) {
                        Log.d("uuuu1", item.toString())
                        if (item.profilePhoto.isNullOrEmpty()) {
                            list.add(FamilyListItem(item.userIdx, "https://s3.ap-northeast-2.amazonaws.com/testfamfam/default/profile.png", item.userName))
                        } else {
                            list.add(FamilyListItem(item.userIdx, item.profilePhoto, item.userName))
                        }
                    }
//                    for (user in data) {
//                        i++
//                        FamilyData.users[i].userId = user.userId
//                        FamilyData.users[i].userIdx = user.userIdx
//                        FamilyData.users[i].sexType = user.sexType
//                        FamilyData.users[i].birthday = user.birthday
//                        FamilyData.users[i].statusMessage = user.statusMessage
//                        FamilyData.users[i].userName = user.userName
//                        FamilyData.users[i].userPhone = user.userPhone
////                       FamilyData.users[i].backPhoto = user.backPhoto
////                       FamilyData.users[i].profilePhoto = user.profilePhoto
//                        FamilyData.users[i].groupIdx = user.groupIdx
//                    }
                    familylist!!.adapter=FamilyListAdapter(context!!,list)
                    familylist!!.layoutManager = LinearLayoutManager(context, LinearLayout.HORIZONTAL, false)
                    Log.d("asd", "여긴되나요")
                }
            }
        })
    }

    class PagerAdapter(manager: FragmentManager, context: Context) : FragmentStatePagerAdapter(manager) {
//        var frags = ArrayList<android.support.v4.app.Fragment>()
//        var context: Context? = null

//        init {
//            this.context = context
//            frags.add(HomeCalendarFragment())
//            frags.add(HomeStatisticsFragment())
//            frags.add(HomeAlertFrament())
//        }


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

}