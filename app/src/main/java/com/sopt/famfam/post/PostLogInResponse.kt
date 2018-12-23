package com.sopt_nyh.retrofit2_example.post

data class PostLogInResponse(
        val status : Int,
        val message : String,
        val data : LoginData
)

data class LoginData(
        val token : String
)