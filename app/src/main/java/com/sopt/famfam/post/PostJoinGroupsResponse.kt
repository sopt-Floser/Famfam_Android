package com.sopt.famfam.post

data class PostJoinGroupsResponse(
    val status: Int,
    val message: String,
    val data: group
)

data class group(
    val groupIdx: Int,
    val groupId: String
)