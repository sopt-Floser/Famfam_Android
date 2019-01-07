package com.sopt.famfam.get

import com.sopt.famfam.database.User

data class GetGroupUserResponse(
    val status : Int,
    val message : String,
    val data : ArrayList<User>
)
