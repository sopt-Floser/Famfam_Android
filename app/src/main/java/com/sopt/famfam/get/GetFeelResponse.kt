package com.sopt.famfam.get

//data class GetCalendarListResponse(
//    val status : Int,
//    val message : String,
//    val data : CalendarData
//)
data class GetFeelResponse(
    val status : Int,
    val message : String,
    val data : Feel
)

data class Feel(
    val types : ArrayList<Types>,
    val firstUserName : String,
    val feelCount : Int

)
data class Types(val feelType:Int)