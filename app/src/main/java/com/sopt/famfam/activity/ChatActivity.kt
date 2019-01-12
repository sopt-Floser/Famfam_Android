package com.sopt.famfam.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.KeyEvent
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import com.sopt.famfam.R
import com.sopt.famfam.adapter.item.ChatItem
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.sopt.famfam.adapter.ChatAdapter
import com.sopt.famfam.database.FamilyData
import kotlinx.android.synthetic.main.activity_chat.*
import java.time.LocalDateTime
import java.util.*
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.ChildEventListener
import org.jetbrains.anko.toast
import java.text.SimpleDateFormat
import java.time.LocalDate


class ChatActivity : AppCompatActivity() {
    var database = FirebaseDatabase.getInstance()
    var databaseReference = database.getReference("message")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        var time = Date()
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
        //data class ChatItem(var id:String, var name : String,var content : String, var time : String, var type : Int){

        et_chat_comment.setOnKeyListener(View.OnKeyListener { view, i, keyEvent ->
            //Enter키눌렀을떄
            when (i) {
                KeyEvent.KEYCODE_ENTER -> {
                    if (keyEvent.action == 1)
                        return@OnKeyListener true
                    val chat = ChatItem(FamilyData.userIdx,FamilyData.userName!!, et_chat_comment.getText().toString(),sdf.format(time),0) //ChatDTO를 이용하여 데이터를 묶는다.
                    databaseReference.child(FamilyData.groupId.toString()).push().setValue(chat) //databaseReference를 이용해 데이터 푸쉬
                    et_chat_comment.setText("")
                }
            }
            false
        })

        btn_chat_send.setOnClickListener{
            val chat = ChatItem(FamilyData.userIdx,FamilyData.userName!!, et_chat_comment.getText().toString(),sdf.format(time),0) //ChatDTO를 이용하여 데이터를 묶는다.
            databaseReference.child(FamilyData.groupId.toString()).push().setValue(chat) //databaseReference를 이용해 데이터 푸쉬
            et_chat_comment.setText("")
        }
        var list = ArrayList<ChatItem>()
        var adapter = ChatAdapter(applicationContext,list)
        rv_chat_chatlist.adapter = adapter
        rv_chat_chatlist.layoutManager=
                LinearLayoutManager(applicationContext, LinearLayout.VERTICAL, false)



        databaseReference.child(FamilyData.groupId.toString()).addChildEventListener(object : ChildEventListener {  // message는 child의 이벤트를 수신합니다.
            override fun onChildAdded(dataSnapshot: DataSnapshot, s: String?) {
                val chatData = dataSnapshot.getValue(ChatItem::class.java)  // chatData를 가져오고
                if((chatData as ChatItem).idx==FamilyData.userIdx)
                    chatData.type=0
                else
                    chatData.type=1
                list.add(chatData!!)  // adapter에 추가합니다.
                rv_chat_chatlist.adapter!!.notifyDataSetChanged()
                rv_chat_chatlist.scrollToPosition(list.size - 1);
            }

            override fun onChildChanged(dataSnapshot: DataSnapshot, s: String?) {}

            override fun onChildRemoved(dataSnapshot: DataSnapshot) {}

            override fun onChildMoved(dataSnapshot: DataSnapshot, s: String?) {}

            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }
}