package com.sopt.famfam.activity

import android.animation.Animator
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.airbnb.lottie.LottieAnimationView
import com.sopt.famfam.R
import com.sopt.famfam.database.FamilyData
import com.sopt.famfam.database.SharedPreferenceController
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
                val token = SharedPreferenceController.getAuthorization(applicationContext)
                Log.d("uuuu1", "들어온다1")
                getLoginResponse(token)
            }

            override fun onAnimationCancel(animation: Animator?) {
                Log.e("Animation:", "cancel")
            }

            override fun onAnimationStart(animation: Animator?) {
                Log.e("Animation:", "start")
            }
        })
    }

    private fun getLoginResponse(token: String) {
        if (token.isNotEmpty()) {
            Log.d("uuuu1", "들어온다2")
            Log.d("uuuu1", token)
            val getLoginResponse = networkService.getLoginResponse("application/json", token)
            getLoginResponse.enqueue(object : Callback<GetLogInResponse> {
                override fun onFailure(call: Call<GetLogInResponse>, t: Throwable) {
                    Log.e("Login fail", t.toString())
                }

                override fun onResponse(call: Call<GetLogInResponse>, response: Response<GetLogInResponse>) {
                    Log.d("uuuu1", response.message())
                    if (response.isSuccessful) {
                        val token = response.body()!!.data.token
                        FamilyData.groupId = response.body()!!.data.user.groupIdx
                        FamilyData.userId = response.body()!!.data.user.userId
                        FamilyData.userName = response.body()!!.data.user.userName
                        FamilyData.token = token
                        FamilyData.statusMessage = response.body()!!.data.user.statusMessage
                        FamilyData.birthday = response.body()!!.data.user.birthday
//                        FamilyData.backPhoto = response.body()!!.data.user.backPhoto
//                        FamilyData.profilePhoto = response.body()!!.data.user.profilePhoto
                        FamilyData.sexType = response.body()!!.data.user.sexType
                        Log.e("uuuu1", token)

                        SharedPreferenceController.setLoginData(
                            this@SplashActivity,
                            FamilyData.groupId.toString() + "," +
                                    FamilyData.userId + "," +
                                    FamilyData.userName + "," +
                                    FamilyData.statusMessage + "," +
                                    FamilyData.birthday + "," +
                                    FamilyData.sexType
                        )
                        SharedPreferenceController.setAuthorization(this@SplashActivity, token)
                        toast(SharedPreferenceController.getAuthorization(this@SplashActivity))
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
}