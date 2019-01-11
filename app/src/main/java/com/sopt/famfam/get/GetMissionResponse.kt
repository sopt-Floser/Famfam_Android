package com.sopt.famfam.get

data class GetMissionResponse(
        val status : Int,
        val message : String,
        val data : mission
)

data class mission (
        val mission : data2,
        val target: String
)

data class data2 (
        val missionIdx : Int,
        val missionType : Int,
        val suffixType : Int,
        val content : String
)