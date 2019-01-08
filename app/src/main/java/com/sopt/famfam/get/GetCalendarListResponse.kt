import com.sopt.famfam.data.BoardData

//data class GetCalendarListResponse(
//    val status : Int,
//    val message : String,
//    val data : CalendarData
//)
//data class CalendarData(
//    val individual :
//)

data class individual(
    val calendarIdx : Int,
    val startDate : String,
    val endDate : String,
    val allDate : String,
    val content : String,
    val returningTime : Int,
    val dinner : Int,
    val userIdx : Int
)

data class family(
    val calendarIdx : Int,
    val startDate : String,
    val endDate : String,
    val allDate : String,
    val content : String,
    val userIdx : Int
)

data class anniversary(
    val anniversaryIdx : Int,
    val content : String,
    val date : String,
    val anniversaryType : String
)

