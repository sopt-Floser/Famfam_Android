package com.sopt.famfam.post

data class PostLogInResponse(
    val status: Int,
    val message: String,
    val data: LoginData
)

data class LoginData(
    val token: String,
    val user: LoginUser
)

data class LoginUser(
    val userId: String,
    val userIdx: Int,
    val userName: String,
    val profilePhoto: String ="",
    val backPhoto: String ="",
    val groupIdx: Int,
    val statusMessage: String,
    val birthday: String,
    val sexType: Int
)