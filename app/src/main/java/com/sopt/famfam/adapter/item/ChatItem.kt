package com.sopt.famfam.adapter.item


data class ChatItem(var id:String, var name : String,var content : String, var time : String, var type : Int){
    constructor() : this("-1","test","text","2:20",0) {

    }
}