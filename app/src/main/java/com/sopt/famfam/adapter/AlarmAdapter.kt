package com.sopt.famfam.adapter

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
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

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.profile_image.setImageResource(list[position].profile_photo)
        holder.content.text = list[position].content
    }

    class Holder(var view: View) : RecyclerView.ViewHolder(view) {
        val profile_image : ImageView = itemView.findViewById(R.id.alarm_profile_image) as ImageView
        val content : TextView = itemView.findViewById(R.id.alarm_content) as TextView
    }
    // network
}


