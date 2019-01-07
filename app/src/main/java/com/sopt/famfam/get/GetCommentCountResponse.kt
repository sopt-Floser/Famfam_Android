package com.sopt.famfam.get

//data class GetCalendarListResponse(
//    val status : Int,
//    val message : String,
//    val data : CalendarData
//)
data class GetCommentCountResponse(
    val status : Int,
    val message : String,
    val data : CommentCount
)

data class CommentCount(
    val count : Int
)