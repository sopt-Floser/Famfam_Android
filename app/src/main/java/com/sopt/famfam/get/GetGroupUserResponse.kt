package com.sopt.famfam.get


data class GetGroupUserResponse(
    val status : Int,
    val message : String,
    val data : tmp
)
data class tmp(
    var users :ArrayList<User>
)
data class User(
    var userIdx : Int ,
    var userId : String,
    var userName:String ,
    var userPhone : String,
    var birthday : String ,
    var sexType : Int,
    var statusMessage : String ,
    var profilePhoto : String ,
    var backPhoto : String ,
    var groupIdx : Int
)