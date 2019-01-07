package com.sopt.famfam.database

internal object FamilyData {
    var userName: String? = "김팸팸"
    var groupId: Int = 0
    var userId: String = ""
    var token : String = ""
    lateinit var users : ArrayList<User>
}
data class User(
    val userIdx : Int,
    val userName:String,
    val statusMessage : String,
    val profilePhoto : String,
    val backPhoto : String,
    val groupIdx : Int
)