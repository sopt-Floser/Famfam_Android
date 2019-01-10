package com.sopt.famfam.adapter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.sopt.famfam.R
import com.sopt.famfam.adapter.item.FamilyListItem
import kotlinx.android.synthetic.main.item_home_family.view.*
import org.jetbrains.anko.image

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

    override fun onBindViewHolder(holder: Holder, position: Int) {
        //list[position].profilePhoto
        val requestOptions = RequestOptions()
        Glide.with(context)
                .setDefaultRequestOptions(requestOptions)
                .load(list[position].profilePhoto)
                .thumbnail(0.5f)
                .into(holder.id)
        holder.name.text = list[position].userName
    }

    class Holder(var view: View) : RecyclerView.ViewHolder(view)
    {
        var name = view.tv_home_famliy_name
        var id = view.iv_home_famliy_other
        // var id= view.findViewById<ImageView>(R.id.iv_mycard_benefit_info_icon)
    }
}


