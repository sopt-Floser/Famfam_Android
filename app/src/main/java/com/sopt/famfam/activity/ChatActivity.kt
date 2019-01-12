package com.sopt.famfam.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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
import com.google.firebase.iid.FirebaseInstanceId
import com.sopt.famfam.adapter.FamilyListAdapter
import com.sopt.famfam.adapter.item.FamilyListItem
import com.sopt.famfam.adapter.item.RoomItem
import com.sopt.famfam.database.User
import com.sopt.famfam.get.GetGroupUserResponse
import com.sopt.famfam.network.ApplicationController
import com.sopt.famfam.network.NetworkService
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.time.LocalDate


class ChatActivity : AppCompatActivity() {
    var database = FirebaseDatabase.getInstance()
    var databaseReference = database.getReference("message")

    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        getGroupMemberListResponse()
        var time = Date()
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
        //data class ChatItem(var id:String, var name : String,var content : String, var time : String, var type : Int){

        et_chat_comment.setOnKeyListener(View.OnKeyListener { view, i, keyEvent ->
            //Enter키눌렀을떄
            when (i) {
                KeyEvent.KEYCODE_ENTER -> {
                    if (keyEvent.action == 1)
                        return@OnKeyListener true
                    val chat = ChatItem(
                        FamilyData.userIdx,
                        FamilyData.userName!!,
                        et_chat_comment.getText().toString(),
                        sdf.format(time),
                        0
                    ) //ChatDTO를 이용하여 데이터를 묶는다.
                    databaseReference.child(FamilyData.groupId.toString()).push()
                        .setValue(chat) //databaseReference를 이용해 데이터 푸쉬
                    et_chat_comment.setText("")
                }
            }
            false
        })

        btn_chat_send.setOnClickListener {
            val chat = ChatItem(
                FamilyData.userIdx,
                FamilyData.userName!!,
                et_chat_comment.getText().toString(),
                sdf.format(time),
                0
            ) //ChatDTO를 이용하여 데이터를 묶는다.
            if (!et_chat_comment.text.equals(""))
                databaseReference.child(FamilyData.groupId.toString()).push().setValue(chat) //databaseReference를 이용해 데이터 푸쉬
            et_chat_comment.setText("")
        }
        var list = ArrayList<ChatItem>()
        var adapter = ChatAdapter(applicationContext, list)
        rv_chat_chatlist.adapter = adapter
        rv_chat_chatlist.layoutManager =
                LinearLayoutManager(applicationContext, LinearLayout.VERTICAL, false)



        databaseReference.child(FamilyData.groupId.toString())
            .addChildEventListener(object : ChildEventListener {  // message는 child의 이벤트를 수신합니다.
                override fun onChildAdded(dataSnapshot: DataSnapshot, s: String?) {
                    val chatData = dataSnapshot.getValue(ChatItem::class.java)  // chatData를 가져오고
                    if ((chatData as ChatItem).idx == FamilyData.userIdx)
                        chatData.type = 0
                    else
                        chatData.type = 1
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

    private fun getGroupMemberListResponse() {
        val postLogInResponse = networkService.getGroupUserResponse("application/json", FamilyData.token)
        postLogInResponse.enqueue(object : Callback<GetGroupUserResponse> {
            override fun onFailure(call: Call<GetGroupUserResponse>, t: Throwable) {
                Log.e("Login fail", t.toString())
            }

            override fun onResponse(call: Call<GetGroupUserResponse>, response: Response<GetGroupUserResponse>) {
                if (response.isSuccessful) {
                    val data = response.body()!!.data.users
                    var list = ArrayList<FamilyListItem>();
                    var i: Int = 0
                    for (item in data) {
                        if (FamilyData.userIdx == item.userIdx)
                            continue
                        Log.d("uuuu1", item.toString())
                        if (item.profilePhoto.isNullOrEmpty()) {
                            list.add(
                                FamilyListItem(
                                    item.userIdx,
                                    "https://s3.ap-northeast-2.amazonaws.com/testfamfam/default/profile_default.png",
                                    item.userName
                                )
                            )
                        } else {
                            list.add(FamilyListItem(item.userIdx, item.profilePhoto, item.userName))
                            Log.d("asdphoto", item.profilePhoto)
                        }
                    }

                    FamilyData.users = ArrayList<User>()
                    for (user in data) {
                        if (user.backPhoto.isNullOrEmpty()) {
                            if (user.profilePhoto != null) {
                                FamilyData.users.add(
                                    User(
                                        user.userIdx,
                                        user.userId,
                                        user.userName,
                                        user.userPhone,
                                        user.birthday,
                                        user.sexType,
                                        user.statusMessage,
                                        user.profilePhoto,
                                        "",
                                        user.groupIdx
                                    )
                                )
                            } else if (user.profilePhoto == null) {
                                FamilyData.users.add(
                                    User(
                                        user.userIdx,
                                        user.userId,
                                        user.userName,
                                        user.userPhone,
                                        user.birthday,
                                        user.sexType,
                                        user.statusMessage,
                                        "",
                                        "",
                                        user.groupIdx
                                    )
                                )
                            }
                        } else if (user.profilePhoto.isNullOrEmpty()) {
                            if (user.backPhoto != null) {
                                FamilyData.users.add(
                                    User(
                                        user.userIdx,
                                        user.userId,
                                        user.userName,
                                        user.userPhone,
                                        user.birthday,
                                        user.sexType,
                                        user.statusMessage,
                                        "",
                                        user.backPhoto,
                                        user.groupIdx
                                    )
                                )
                            } else if (user.backPhoto == null) {
                                FamilyData.users.add(
                                    User(
                                        user.userIdx,
                                        user.userId,
                                        user.userName,
                                        user.userPhone,
                                        user.birthday,
                                        user.sexType,
                                        user.statusMessage,
                                        "",
                                        "",
                                        user.groupIdx
                                    )
                                )
                            }

                        } else {
                            FamilyData.users.add(
                                User(
                                    user.userIdx,
                                    user.userId,
                                    user.userName,
                                    user.userPhone,
                                    user.birthday,
                                    user.sexType,
                                    user.statusMessage,
                                    user.profilePhoto,
                                    user.backPhoto,
                                    user.groupIdx
                                )
                            )
                        }


                    }
                    var database = FirebaseDatabase.getInstance()
                    var databaseReference = database.getReference("RoomUser")
                    var chat = RoomItem(
                        FirebaseInstanceId.getInstance().getToken()!!,
                        FamilyData.userIdx,
                        FamilyData.userName!!
                    )
                    databaseReference.child(FamilyData.groupId.toString()).child(FamilyData.userIdx.toString())
                        .setValue(chat) //databaseReference를 이용해 데이터 푸쉬

                    Log.d("asd", "여긴되나요")
                }
            }
        })
    }
}