package com.sopt.famfam.get


data class GetContentListResponse(
    val status : Int,
    val message : String,
    val data : Contents
)

data class Contents(
    val totalPage : Int,
    val photos : ArrayList<Photos>
)
data class Photos(
    val photoIdx :Int,
    val photoName : String,
    val createdAt : String,
    val contentIdx : Int,
    val UserIdx : Int
)