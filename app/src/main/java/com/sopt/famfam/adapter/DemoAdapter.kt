package com.sopt.famfam.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sopt.famfam.R
import com.sopt.famfam.adapter.item.DemoItem

class DemoAdapter(var context: Context, var list : ArrayList<DemoItem>) : RecyclerView.Adapter<DemoAdapter.Holder>()
{
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): Holder {
        var view: View
        view = LayoutInflater.from(context).inflate(R.layout.abc_dialog_title_material, p0, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(p0: Holder, position: Int) {
    }

    class Holder(var view: View) : RecyclerView.ViewHolder(view)
    {
        // var id= view.findViewById<ImageView>(R.id.iv_mycard_benefit_info_icon)
    }
}


