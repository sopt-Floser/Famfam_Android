package com.sopt.famfam.post

data class GetLogInResponse(
        val status: Int,
        val message: String,
        val data: Login
)

data class Login(
        val token: String,
        val user: Auto
)

data class Auto(
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