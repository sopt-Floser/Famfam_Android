package com.sopt.famfam.database

internal object FamilyData {
    var userName: String? = "김팸팸"
    var groupId: Int = 0
    var userId: String = ""
    lateinit var token : String
    var statusMessage : String = ""
    var profilePhoto : String = ""
    var backPhoto : String = ""
    var birthday : String = ""
    var sexType : Int = 0

    lateinit var users : ArrayList<User>
}
data class User(
    var userIdx : Int ,
    var userName:String ,
    var statusMessage : String ,
    var profilePhoto : String ,
    var backPhoto : String ,
    var groupIdx : Int ,
    var birthday : String ,
    var sexType : Int
)