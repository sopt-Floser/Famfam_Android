package com.sopt.famfam.get

//data class GetCalendarListResponse(
//    val status : Int,
//    val message : String,
//    val data : CalendarData
//)
data class GetFeelCountResponse(
    val status : Int,
    val message : String,
    val data : FeelCount
)

data class FeelCount(
    val count : Int
)