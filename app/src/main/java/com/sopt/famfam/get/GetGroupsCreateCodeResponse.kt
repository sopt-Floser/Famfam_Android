package com.sopt.famfam.get

data class GetGroupsCreateCodeResponse(
        val status : Int,
        val message : String,
        val data : code
)

data class code (
        val code : String,
        val groupIdx : Int,
        val createdAt : String,
        val expiredAt : String
)