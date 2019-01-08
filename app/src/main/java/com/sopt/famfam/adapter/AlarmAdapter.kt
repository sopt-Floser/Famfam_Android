package com.sopt.famfam.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.sopt.famfam.R
import com.sopt.famfam.data.AlarmData

class AlarmAdapter(var context: Context, var list : ArrayList<AlarmData>) : RecyclerView.Adapter<AlarmAdapter.Holder>()
{
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): Holder {
        var view: View
        view = LayoutInflater.from(context).inflate(R.layout.rv_item_alarm_list, p0, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(p0: Holder, position: Int) {
//        if (list[position].category_image.isNotEmpty() && list[position].profile_photo.isNotEmpty()) {
//            p0.category_image.visibility = View.VISIBLE
//            p0.profile_image.visibility = View.VISIBLE
//            p0.content.text = list[position].content
//        }
    }
    class Holder(var view: View) : RecyclerView.ViewHolder(view) {
        val category_image : ImageView = itemView.findViewById(R.id.alarm_category_image) as ImageView
        val profile_image : ImageView = itemView.findViewById(R.id.alarm_profile_image) as ImageView
        val content : TextView = itemView.findViewById(R.id.alarm_content) as TextView
    }
}


