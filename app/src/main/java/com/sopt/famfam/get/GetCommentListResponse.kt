package com.sopt.famfam.get


data class GetCommentListResponse(
    val status : Int,
    val message : String,
    val data : ArrayList<Comments>
)

data class Comments(
    val commentIdx : Int,
    val content : String,
    val createdAt : String,
    val contentIdx : Int,
    val userIdx : Int,
    val userName : String,
    val userProfile :String
)
