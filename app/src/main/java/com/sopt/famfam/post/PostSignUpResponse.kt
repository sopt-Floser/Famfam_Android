package com.sopt.famfam.post

data class PostSignUpResponse(
        val status : String,
        val message : String,
        val data : SignupData
)

data class SignupData(
        val token : String,
        val user : user
)

data class user (
    val userId : String,
    val userName : String,
    val profilePhoto : String,
    val backPhoto : String,
    val groupIdx: Int
)