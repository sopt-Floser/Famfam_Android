package com.sopt.famfam.get

data class GetUserResponse(
        val status: Int,
        val message: String,
        val data: LoginData
)

data class LoginData(
        val userId: String,
        val userName: String,
        val profilePhoto: String ="",
        val backPhoto: String ="",
        val groupIdx: Int,
        val statusMessage: String,
        val birthday: String,
        val sexType: Int
)
