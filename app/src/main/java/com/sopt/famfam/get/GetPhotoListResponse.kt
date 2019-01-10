package com.sopt.famfam.get

data class GetPhotoListResponse(
        val status : Int,
        val message : String,
        val data : AlbumPhoto
)
data class AlbumPhoto(
        val totalPage: Int,
        val photos : ArrayList<Photos>
)