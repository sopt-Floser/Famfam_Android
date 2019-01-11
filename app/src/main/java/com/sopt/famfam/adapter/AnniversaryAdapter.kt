package com.sopt.famfam.adapter

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sopt.famfam.R
import com.sopt.famfam.adapter.item.AnniversaryItem
import com.sopt.famfam.adapter.item.DemoItem
import kotlinx.android.synthetic.main.item_anniversary.view.*

class AnniversaryAdapter(var context: Context, var list : ArrayList<AnniversaryItem>) : RecyclerView.Adapter<AnniversaryAdapter.Holder>()
{
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): Holder {
        var view: View
        view = LayoutInflater.from(context).inflate(R.layout.item_anniversary, p0, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(p0: Holder, position: Int) {
        p0.title.text=list.get(position).title
        p0.date.text=list.get(position).date.toString()
        p0.exdate.text=list.get(position).exDate

    }

    class Holder(var view: View) : RecyclerView.ViewHolder(view)
    {
        var title= view.tv_anniversary_title
        var date =  view.tv_anniversary_date
        var exdate = view.tv_anniversary_exdate
    }
}


