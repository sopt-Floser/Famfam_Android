package com.sopt.famfam.adapter

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sopt.famfam.R
import com.sopt.famfam.adapter.item.FamilyListItem
import kotlinx.android.synthetic.main.item_home_family.view.*

class FamilyListAdapter(var context: Context, var list : ArrayList<FamilyListItem>) : RecyclerView.Adapter<FamilyListAdapter.Holder>()
{
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): Holder {
        var view: View
        view = LayoutInflater.from(context).inflate(R.layout.item_home_family, p0, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(p0: Holder, position: Int) {

    }

    class Holder(var view: View) : RecyclerView.ViewHolder(view)
    {
        var name = view.tv_home_famliy_name
        var id = view.iv_home_famliy_other
        // var id= view.findViewById<ImageView>(R.id.iv_mycard_benefit_info_icon)
    }
}


