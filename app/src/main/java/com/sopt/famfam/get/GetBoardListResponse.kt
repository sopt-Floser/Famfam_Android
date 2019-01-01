import com.sopt.famfam.data.BoardData


data class GetBoardListResponse(
        val status : Int,
        val message : String,
        val data : ArrayList<BoardData>
)