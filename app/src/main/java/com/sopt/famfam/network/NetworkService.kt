package com.sopt.famfam.network

import com.google.gson.JsonObject
import com.sopt.famfam.get.GetCommentCountResponse
import com.sopt.famfam.get.GetContentCountResponse
import com.sopt.famfam.get.GetFeelCountResponse
import com.sopt.famfam.get.GetGroupUserResponse
import com.sopt.famfam.get.GetGroupsCreateCodeResponse
import com.sopt.famfam.post.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

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

    // 그룹초대코드 생성/조회
    @GET("/groups/invitation")
    fun getGroupsCreateCodeResponse (
        @Header("Content-Type") content_type : String,
        @Header("Authorization") token : String
    ) : Call<GetGroupsCreateCodeResponse>


   //게시판 글쓰기
    @Multipart
    @POST("/contents")
    fun postWriteBoardResponse(
        @Header("Authorization") token: String,
        @Part("title") title: RequestBody,
        @Part("contents") contents: RequestBody,
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