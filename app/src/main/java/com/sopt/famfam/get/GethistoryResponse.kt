package com.sopt.famfam.get

data class GethistoryResponse(
        val status: Int,
        val message: String,
        val data: page
)

data class page (
        val totalPage : Int,
        val histories : ArrayList<History>
)

data class History(
        val historyIdx: Int,
        val userIdx: Int,
        val groupIdx: Int,
        val historyType: String,
        val content: String,
        val createdAt: String
)