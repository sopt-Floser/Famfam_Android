package com.sopt.famfam.post

data class PostLogInResponse(
        val status : Int,
        val message : String,
        val data : LoginData
)

data class LoginData(
        val token : String,
        val user : User
)
data class User (
    val userId : String,
    val userName : String,
    val profilePhoto : String,
    val backPhoto : String,
    val groupIdx: Int
)