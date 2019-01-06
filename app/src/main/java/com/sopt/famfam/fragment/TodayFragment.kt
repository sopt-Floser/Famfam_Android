package com.sopt.famfam.fragment
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import com.sopt.famfam.R
import com.sopt.famfam.activity.UploadActivity
import kotlinx.android.synthetic.main.layout_today_top_toolbar.view.*

class TodayFragment : Fragment(){
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.activity_today, container, false)

        view.btn_add_post.setOnClickListener {
            val intent = Intent(context, UploadActivity::class.java)
            startActivity(intent)
        }
        return view
    }
}
