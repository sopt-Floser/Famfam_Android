package com.sopt.famfam.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.sopt.famfam.R
import com.sopt.famfam.adapter.item.ChatItem
import com.sopt.famfam.adapter.item.DemoItem
import kotlinx.android.synthetic.main.item_chat_other.view.*
val MY_CHAT = 0
val OTHER_CHAT = 1
val PHOTO = 2
val EMOTICON = 3
val VIDEO = 4
val SYSTEM = 5
class ChatAdapter(var context: Context, var list: ArrayList<ChatItem>) : RecyclerView.Adapter<ChatAdapter.Holder>() {
    var preItemType = 0;
    override fun onCreateViewHolder(p0: ViewGroup, viewType: Int): Holder {
        var view: View? = null
        if (viewType == MY_CHAT) {
            view = LayoutInflater.from(context).inflate(R.layout.item_chat_me, p0, false)
        } else if (viewType == OTHER_CHAT) {
            view = LayoutInflater.from(context).inflate(R.layout.item_chat_other, p0, false)
        } else if (viewType == PHOTO) {
            view = LayoutInflater.from(context).inflate(R.layout.item_chat_photo, p0, false)
        } else if (viewType == EMOTICON) {
            view = LayoutInflater.from(context).inflate(R.layout.item_chat_photo, p0, false)

        } else if (viewType == VIDEO) {
            view = LayoutInflater.from(context).inflate(R.layout.item_chat_photo, p0, false)
        } else if (viewType == SYSTEM) {
            view = LayoutInflater.from(context).inflate(R.layout.item_chat_me, p0, false)
        }
        return Holder(view, viewType)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun getItemViewType(position: Int): Int {
        return list.get(position).type
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        var viewType = holder.viewType
        if (viewType == MY_CHAT) {
            holder.content.text=list.get(position).content
            holder.time.text = list.get(position).time
        } else if (viewType == OTHER_CHAT) {
            // 이미지 해
            holder.content.text=list.get(position).content
            holder.nickname.text = list.get(position).name
            holder.time.text = list.get(position).time
        } else if (viewType == PHOTO) {

        } else if (viewType == EMOTICON) {

        } else if (viewType == VIDEO) {

        } else if (viewType == SYSTEM) {

        }
    }

    class Holder(var view: View?, var viewType: Int) : RecyclerView.ViewHolder(view!!) {
        lateinit var nickname : TextView
        lateinit var content : TextView
        lateinit var profile : ImageView
        lateinit var time : TextView
        init {
            if (viewType == MY_CHAT) {
                time=view!!.findViewById(R.id.tv_chat_me_time)
                content=view!!.findViewById(R.id.tv_chat_me)
            } else if (viewType == OTHER_CHAT) {
                time=view!!.findViewById(R.id.tv_chat_other_time)
                content=view!!.findViewById(R.id.tv_chat_other)
                profile=view!!.findViewById(R.id.iv_chat_profile)
                nickname=view!!.findViewById(R.id.tv_chat_other_nickname)
            } else if (viewType == PHOTO) {

            } else if (viewType == EMOTICON) {

            } else if (viewType == VIDEO) {

            } else if (viewType == SYSTEM) {

            }

        }
    }
}


