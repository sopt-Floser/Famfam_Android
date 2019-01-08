package com.sopt.famfam.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.sopt.famfam.R
import com.sopt.famfam.adapter.item.FamilyListItem
import com.sopt.famfam.database.FamilyData
import com.sopt.famfam.get.GetCommentCountResponse
import com.sopt.famfam.get.GetContentCountResponse
import com.sopt.famfam.get.GetFeelCountResponse
import com.sopt.famfam.get.GetGroupUserResponse
import com.sopt.famfam.network.ApplicationController
import com.sopt.famfam.network.NetworkService
import kotlinx.android.synthetic.main.fragment_home_statistics.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeStatisticsFragment : Fragment(){
    var feelTextView : TextView?=null
    var commentTextView : TextView?=null
    var contentTextView : TextView?=null
    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_home_statistics, container, false)
        feelTextView=view.tv_home_statistics_heart
        commentTextView=view.tv_home_statistics_comment
        contentTextView=view.tv_home_statistics_post

        getCommentCountResponse()
        getContentCountResponse()
        getFeelCountResponse()
        view.test.setOnClickListener{
            getCommentCountResponse()
            getContentCountResponse()
            getFeelCountResponse()
            Log.d("asd","asd")
        }
        return view
    }

    private fun getFeelCountResponse(){
        val postLogInResponse = networkService.getFeelCountResponse("application/json", FamilyData.token)
        postLogInResponse.enqueue(object : Callback<GetFeelCountResponse> {
            override fun onFailure(call: Call<GetFeelCountResponse>, t: Throwable) {
                Log.e("Login fail", t.toString())
            }

            override fun onResponse(call: Call<GetFeelCountResponse>, response: Response<GetFeelCountResponse>) {
                if (response.isSuccessful){
                    val data = response.body()!!.data
                    feelTextView!!.text = data.count.toString()
                    Log.d("asd",data.count.toString())
                }
                Log.d("asd",response.message())
            }
        })
    }
    private fun getContentCountResponse(){
        val postLogInResponse = networkService.getContentCountResponse("application/json", FamilyData.token)
        postLogInResponse.enqueue(object : Callback<GetContentCountResponse> {
            override fun onFailure(call: Call<GetContentCountResponse>, t: Throwable) {
                Log.e("Login fail", t.toString())
            }

            override fun onResponse(call: Call<GetContentCountResponse>, response: Response<GetContentCountResponse>) {
                if (response.isSuccessful){
                    val data = response.body()!!.data
                    contentTextView!!.text = data.count.toString()

                }
            }
        })
    }
    private fun getCommentCountResponse(){
        val postLogInResponse = networkService.getCommentCountResponse("application/json", FamilyData.token)
        postLogInResponse.enqueue(object : Callback<GetCommentCountResponse> {
            override fun onFailure(call: Call<GetCommentCountResponse>, t: Throwable) {
                Log.e("Login fail", t.toString())
            }

            override fun onResponse(call: Call<GetCommentCountResponse>, response: Response<GetCommentCountResponse>) {
                if (response.isSuccessful){
                    val data = response.body()!!.data
                    commentTextView!!.text = data.count.toString()

                }
            }
        })
    }
}
