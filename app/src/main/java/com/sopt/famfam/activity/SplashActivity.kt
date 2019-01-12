package com.sopt.famfam.activity

import android.animation.Animator
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import android.util.Log
import android.widget.LinearLayout
import com.airbnb.lottie.LottieAnimationView
import com.google.firebase.iid.FirebaseInstanceId
import com.sopt.famfam.R
import com.sopt.famfam.adapter.FamilyListAdapter
import com.sopt.famfam.adapter.item.FamilyListItem
import com.sopt.famfam.database.FamilyData
import com.sopt.famfam.database.FamilyData.token
import com.sopt.famfam.database.SharedPreferenceController
import com.sopt.famfam.database.User
import com.sopt.famfam.get.GetGroupUserResponse
import com.sopt.famfam.network.ApplicationController
import com.sopt.famfam.network.NetworkService
import com.sopt.famfam.post.GetLogInResponse
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SplashActivity : AppCompatActivity() {

    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Log.e("uuu1", "start")
        val star_badge_animation: LottieAnimationView = findViewById(R.id.lottie_main_act_star)

        star_badge_animation.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {
                Log.e("Animation:", "repeat")
            }

            override fun onAnimationEnd(animation: Animator?) {
                Log.d("uuuu1", "들어온다1")
                val token = SharedPreferenceController.getAuthorization(applicationContext)
                getLoginResponse(token)
            }

            override fun onAnimationCancel(animation: Animator?) {
                Log.e("Animation:", "cancel")
            }

            override fun onAnimationStart(animation: Animator?) {
                Log.e("Animation:", "start")
                getGroupMemberListResponse()
            }
        })
    }

    private fun getLoginResponse(token: String) {
        if (token.isNotEmpty()) {
            Log.d("uuuu1", "들어온다2")
            Log.d("uuuu1", token)
            val getLoginResponse = networkService.getLoginResponse(FirebaseInstanceId.getInstance().getToken()!!,"application/json", token)
            getLoginResponse.enqueue(object : Callback<GetLogInResponse> {
                override fun onFailure(call: Call<GetLogInResponse>, t: Throwable) {
                    Log.e("Login fail", t.toString())
                    toast("서버연결 실패")
                    finish()
                }

                override fun onResponse(call: Call<GetLogInResponse>, response: Response<GetLogInResponse>) {
                    Log.d("uuuu1", response.message())
                    if (response.isSuccessful) {
                        Log.d("uuuu1", response.body().toString())
                        val token = response.body()!!.data.token
                        FamilyData.groupId = response.body()!!.data.user.groupIdx
                        FamilyData.userIdx = response.body()!!.data.user.userIdx
                        FamilyData.userName = response.body()!!.data.user.userName
                        FamilyData.token = token
                        FamilyData.statusMessage = response.body()!!.data.user.statusMessage
                        FamilyData.birthday = response.body()!!.data.user.birthday

                        if(response.body()!!.data.user.profilePhoto!=null)
                            FamilyData.profilePhoto = response.body()!!.data.user.profilePhoto
                        else
                            FamilyData.profilePhoto = ""
                        Log.d("uuuu1", "로그인"+FamilyData.profilePhoto)

                        if(response.body()!!.data.user.backPhoto!=null)
                            FamilyData.backPhoto = response.body()!!.data.user.backPhoto
                        else
                            FamilyData.backPhoto = ""

                        FamilyData.sexType = response.body()!!.data.user.sexType
                        Log.e("uuuu1", token)

                        SharedPreferenceController.setLoginData(
                            this@SplashActivity,
                            FamilyData.groupId.toString() + "," +
                                    FamilyData.userIdx + "," +
                                    FamilyData.userName + "," +
                                    FamilyData.statusMessage + "," +
                                    FamilyData.birthday + "," +
                                    FamilyData.sexType
                        )
                        SharedPreferenceController.setAuthorization(this@SplashActivity, token)
                        val groupIdx = FamilyData.groupId.toString()
                        Log.e("uuuu1", groupIdx)
                        if (groupIdx == "-1") {
                            startActivity<SelectActivity>()
                        } else {
                            startActivity<MainActivity>()
                            finish()
                        }
                    }
                }
            })
        } else {
            Log.d("uuuu1", "들어온다3")
            startActivity(Intent(applicationContext, IntroActivity::class.java))
            finish()
        }
    }


    private fun getGroupMemberListResponse() {
        val token = SharedPreferenceController.getLoginData(applicationContext)
        val postLogInResponse = networkService.getGroupUserResponse("application/json", token)
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
                            list.add(
                                FamilyListItem(
                                    item.userIdx,
                                    "",
                                    item.userName
                                )
                            )
                        } else {
                            list.add(FamilyListItem(item.userIdx, item.profilePhoto, item.userName))
                            Log.d("asdphoto", item.profilePhoto)
                        }
                    }
                    FamilyData.users = ArrayList<User>()
                    for (user in data) {
                        if(FamilyData.userIdx==user.userIdx)
                            continue
                        FamilyData.users.add(
                            User(
                                user.userIdx,
                                user.userId,
                                user.userName,
                                user.userPhone,
                                user.birthday,
                                user.sexType,
                                user.statusMessage,
                                user.profilePhoto,
                                user.backPhoto,
                                user.groupIdx
                            )
                        )
                    }
                    Log.d("asd", "여긴되나요")
                }
            }
        })
    }
}