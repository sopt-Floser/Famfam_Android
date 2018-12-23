package com.sopt_nyh.retrofit2_example.get

import com.sopt_nyh.retrofit2_example.data.BoardData

data class GetBoardListResponse(
        val status : Int,
        val message : String,
        val data : ArrayList<BoardData>
)