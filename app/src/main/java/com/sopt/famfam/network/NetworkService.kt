package com.sopt.famfam.network

import com.google.gson.JsonObject
import com.sopt.famfam.post.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*
import java.util.ArrayList
import android.provider.MediaStore
import android.provider.DocumentsContract
import android.content.ContentUris
import android.os.Environment.getExternalStorageDirectory
import android.os.Build
import com.sopt.famfam.get.*


interface NetworkService {
    //회원가입
    @POST("/users")
    fun postSignUpResponse(
        @Header("Content-Type") content_type: String,
        @Body() body: JsonObject
    ): Call<PostSignUpResponse>

    //로그인
    @POST("/login")
    fun postLoginResponse(
        @Header("Content-Type") content_type: String,
        @Body() body: JsonObject
    ): Call<PostLogInResponse>


    //아이디 중복체크
    @POST("/users/id")
    fun postConfirmIdResponse(
        @Header("Content-Type") content_type: String,
        @Body() body: JsonObject
    ): Call<PostConfirmIdResponse>

    //그룹생성
    @POST("/groups")
    fun postGroupsResponse(
        @Header("Content-Type") content_type: String,
        @Header("Authorization") token : String
    ): Call<PostGroupsResponse>


    //게시판 글쓰기
    @Multipart
    @POST("/contents")
    fun postWriteBoardResponse(
        @Header("Authorization") token: String,
        @Part("title") title: RequestBody,
        @Part contents: MultipartBody.Part?,
        @Part photo: MultipartBody.Part?
    ): Call<PostWriteBoardResponse>


    // 그룹원 조회
    @GET("/users/groups")
    fun getGroupUserResponse(
            @Header("Content-Type") content_type : String,
            @Header("Authorization") token : String
    ) : Call<GetGroupUserResponse>

    // 감정표현 수 조회
    @GET("/feels/count/week")
    fun getFeelCountResponse(
        @Header("Content-Type") content_type : String,
        @Header("Authorization") token : String
    ) : Call<GetFeelCountResponse>
    // 댓글 수 조회
    @GET("/contents/count/week")
    fun getContentCountResponse(
        @Header("Content-Type") content_type : String,
        @Header("Authorization") token : String
    ) : Call<GetContentCountResponse>
    // 감정표현 수 조회
    @GET("/comments/count/week")
    fun getCommentCountResponse(
        @Header("Content-Type") content_type : String,
        @Header("Authorization") token : String
    ) : Call<GetCommentCountResponse>

    @GET("/contents")
    fun getContentListResponse(
        @Header("Authorization") token : String,
        @Query("page") page : Int,
        @Query("size") size : Int
    ) : Call<GetContentListResponse>

    //
    @Multipart
    @POST("/contents")
    fun postWriteContentResponse(
            @Header("Authorization") token : String,
            @Header("Content-Type") content_type: String,
            @Part("content") content: RequestBody,
            @Part photos: List<MultipartBody.Part>
    ): Call<PostWriteContentResponse>

//
//    //게시물 상세 보기
//    @GET("/contents/{contentIdx}")
//    fun getDetailedBoardResponse(
//            @Header("Content-Type") content_type : String,
//            @Header("Authorization") token : String,
//            @Path("contentIdx") contentIdx : Int
//    ) : Call<GetDetailedBoardResponse>
//


}

//@POST("/users")
//fun postSignUpResponse(
//        @Header("Content-Type") content_type : String,
//        @Field("name") name : String,
//        @Field("email") email : String,
//        @Field("password") password : String,
//        @Field("part") part : String
//) : Call<PostSignUpResponse>
//"name" : "테스트",
//"email" : "2",
//"password" : "1234",
//"part" : "서버"


