import com.sopt.famfam.data.BoardData

data class GetContentResponse(
        val status : Int,
        val message : String,
        val data : ArrayList<BoardData>
)