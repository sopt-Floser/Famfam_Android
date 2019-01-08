package com.sopt.famfam.post

data class PostLogInResponse(
        val status : Int,
        val message : String,
        val data : LoginData
)

data class LoginData(
        val token : String
)