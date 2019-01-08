package com.sopt.famfam.get

//data class GetCalendarListResponse(
//    val status : Int,
//    val message : String,
//    val data : CalendarData
//)
data class GetContentCountResponse(
    val status : Int,
    val message : String,
    val data : ContentCount
)

data class ContentCount(
    val count : Int
)