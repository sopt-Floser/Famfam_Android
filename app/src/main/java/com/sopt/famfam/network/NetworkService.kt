package com.sopt.famfam.network

import com.google.gson.JsonObject
import com.sopt.famfam.delete.DeleteUserResponse
import com.sopt.famfam.get.*
import com.sopt.famfam.post.*

import com.sopt.famfam.put.PutEditProfileResponse
import com.sopt.famfam.put.PutResetPwResponse
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

    //자동로그인
    @GET("/login")
    fun getLoginResponse(
        @Header("Content-Type") content_type: String,
        @Header("Authorization") token : String
    ): Call<GetLogInResponse>

    ///users 회원탈퇴
    @DELETE("/users")
    fun deleteUserResponse(
        @Header("Content-Type") content_type: String,
        @Header("Authorization") token : String
    ): Call<DeleteUserResponse>

    //아이디 중복체크
    @POST("/users/id")
    fun postConfirmIdResponse(
        @Header("Content-Type") content_type: String,
        @Body() body: JsonObject
    ): Call<PostConfirmIdResponse>

    ///users/password 비밀번호 수정
    @PUT("/users/password")
    fun putResetPwResponse(
        @Header("Content-Type") content_type: String,
        @Header("Authorization") token : String,
        @Body() body: JsonObject
    ): Call<PutResetPwResponse>

    //그룹생성
    @POST("/groups")
    fun postGroupsResponse(
        @Header("Content-Type") content_type: String,
        @Header("Authorization") token : String
    ): Call<PostGroupsResponse>

    // 그룹참여
    @POST("/groups/join")
    fun postJoinGroupsResponse(
        @Header("Content-Type") content_type: String,
        @Header("Authorization") token : String,
        @Body() body: JsonObject
    ): Call<PostJoinGroupsResponse>

    // 그룹초대코드 생성/조회
    @GET("/groups/invitation")
    fun getGroupsCreateCodeResponse (
        @Header("Content-Type") content_type : String,
        @Header("Authorization") token : String
    ) : Call<GetGroupsCreateCodeResponse>

    // 프로필 정보 수정
    @PUT("/users")
    fun putEditProfileResponse (
        @Header("Content-Type") content_type : String,
        @Header("Authorization") token : String,
        @Body() body: JsonObject
    ) : Call<PutEditProfileResponse>

//    //모든 컨텐츠 조회
//    @GET("/contents")
//    fun getBoardListResponse(
//            @Header("Content-Type") content_type : String,
//            @Query("offset") offset : Int,
//            @Query("limit") limit : Int
//    ) : Call<GetBoardListResponse>


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

    // 게시글 내용 조회
    @GET("/contents")
    fun getContentListResponse(
        @Header("Authorization") token : String,
        @Query("page") page : Int,
        @Query("size") size : Int
    ) : Call<GetContentListResponse>

    //
    @GET("/comments/contents/")
    fun getCommentListResponse(
        @Header("Authorization") token : String,
        @Path("contentIdx") contentIdx : Int
    ) : Call<GetCommentListResponse>

    //upload
    @Multipart
    @POST("/contents")
    fun postWriteContentResponse(
            @Header("Authorization") token : String,
            @Part("content") content: RequestBody,
            @Part photos: List<MultipartBody.Part>
    ): Call<PostWriteContentResponse>

    @GET("/photos")
    fun getPhotoListResponse(
        @Header("Authorization") token : String,
        @Query("userIdx") userIdx : Int,
        @Query("page") page_no : Int,
        @Query("size") page_size : Int
    ) : Call<GetPhotoListResponse>
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


