package com.sopt.famfam.fragment
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.simplemobiletools.calendar.pro.activities.CalendarSplashActivity
import com.sopt.famfam.R
import kotlinx.android.synthetic.main.fragment_more.view.*

class MoreFragment : Fragment(){
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_more, container, false)

        val newFragment = MoreVersionFragment()
        val transaction = childFragmentManager.beginTransaction()


        view.btn_more_version.setOnClickListener({
            // Store the Fragment in stack
            transaction.addToBackStack(null)
            transaction.replace(R.id.layout_more, newFragment).commit()
        })

        view.btn_more_alert_setting.setOnClickListener(View.OnClickListener {
            // 알람 성정
        })

        val intent = Intent(context , CalendarSplashActivity::class.java)
        view.btn_more_cc.setOnClickListener({
            startActivity(intent)
        })



        return view
    }

}
