package com.sopt.famfam.activity

import android.animation.Animator
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import com.airbnb.lottie.LottieAnimationView
import com.sopt.famfam.R
import kotlinx.android.synthetic.main.activity_splash.*
import org.jetbrains.anko.startActivity

class SplashActivity : AppCompatActivity() {

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
//                val handler = Handler()
//                handler.postDelayed({
                    startActivity(Intent(applicationContext, IntroActivity::class.java))
                    finish()
//                }, 1100)

            }

            override fun onAnimationCancel(animation: Animator?) {
                Log.e("Animation:", "cancel")
            }

            override fun onAnimationStart(animation: Animator?) {
                Log.e("Animation:", "start")
            }
        })
    }
}
