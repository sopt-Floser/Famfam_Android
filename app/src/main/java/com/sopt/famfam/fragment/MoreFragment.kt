package com.sopt.famfam.fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sopt.famfam.R
import kotlinx.android.synthetic.main.fragment_more.view.*

class MoreFragment : RootFragment(){
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_more, container, false)

        var newFragment = MoreVersionFragment()
        var transaction = childFragmentManager.beginTransaction()


        view.btn_more_version.setOnClickListener {
            // Store the Fragment in stack
            newFragment = MoreVersionFragment()
            transaction = childFragmentManager.beginTransaction()
            transaction.addToBackStack(null)
            transaction.replace(R.id.layout_more, newFragment).commit()
        }

        view.btn_more_alert_setting.setOnClickListener{
            // 알람 성정
            transaction = childFragmentManager.beginTransaction()
            transaction.addToBackStack(null)
            transaction.replace(R.id.layout_more, newFragment).commit()
        }

        view.btn_more_cc.setOnClickListener(View.OnClickListener {
            // 이용정보
        })


        return view
    }

}
