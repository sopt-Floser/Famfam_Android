package com.sopt.famfam.get


data class GetContentListResponse(
    val status : Int,
    val message : String,
    val data : PostContent
)
data class PostContent(
    val totalPage : Int,
    val contents : ArrayList<Contents>
)

data class Contents(
    val userName : String,
    val userProfile:String,
    val photos : ArrayList<Photos>,
    val content : MainContents
)
data class MainContents(
    val contentIdx:Int,
    val content : String,
    val createdAt : String,
    val commentCount : Int,
    val userIdx : Int,
    val GroupIdx : Int
)
data class Photos(
    val photoIdx :Int,
    val photoName : String,
    val createdAt : String,
    val contentIdx : Int,
    val UserIdx : Int
)