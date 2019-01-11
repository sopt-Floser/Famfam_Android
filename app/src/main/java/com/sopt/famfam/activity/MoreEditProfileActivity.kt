package com.sopt.famfam.activity

import android.Manifest
import android.app.Activity
import android.content.CursorLoader
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.sopt.famfam.R
import com.sopt.famfam.database.FamilyData
import com.sopt.famfam.database.FamilyData.token
import com.sopt.famfam.database.SharedPreferenceController
import com.sopt.famfam.fragment.DatePickerFragment3
import com.sopt.famfam.get.GetUserResponse
import com.sopt.famfam.network.ApplicationController
import com.sopt.famfam.network.NetworkService
import com.sopt.famfam.put.PutEditProfileResponse
import com.sopt.famfam.put.PutUserPhotoResponse
import kotlinx.android.synthetic.main.activity_more.*
import kotlinx.android.synthetic.main.activity_more_edit_profile.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class MoreEditProfileActivity : AppCompatActivity() {
    val REQUEST_CODE_SELECT_IMAGE: Int = 1004
    val My_READ_STORAGE_REQUEST_CODE = 7777
    var FLAG : Int = 1

    var imageURI : String? = null


    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_more_edit_profile)
        tv_more_editprofile_nickname.setText(FamilyData.userName)
        tv_more_editprofile_statusmessage.setText(FamilyData?.statusMessage)
        tv_more_editprofile_birth_text.setText(FamilyData.birthday?.substring(0, 10))
        var parsedSexType = ""
        if (FamilyData.sexType == 0) {
            parsedSexType = "여자"
        } else {
            parsedSexType = "남자"
        }
        tv_more_editprofile_sexType.setText(parsedSexType)
        setOnClickListener()
//        iv_more_profile_img
        if (FamilyData.backPhoto == ""){
            val requestOptions2 = RequestOptions()
            Glide.with(applicationContext)
                .setDefaultRequestOptions(requestOptions2)
                .load(R.drawable.mybackimg)
                .thumbnail(0.5f)
                .into(iv_more_edit_bg)
        }else {
            val requestOptions2 = RequestOptions()
            Glide.with(applicationContext)
                .setDefaultRequestOptions(requestOptions2)
                .load(FamilyData.backPhoto)
                .thumbnail(0.5f)
                .into(iv_more_edit_bg)
        }

        if (FamilyData.profilePhoto == ""){
            val requestOptions1 = RequestOptions()
            Glide.with(applicationContext)
                .setDefaultRequestOptions(requestOptions1)
                .load(R.drawable.myimg)
                .thumbnail(0.5f)
                .into(iv_more_edit_profile_img)
        } else {
            val requestOptions1 = RequestOptions()
            Glide.with(applicationContext)
                .setDefaultRequestOptions(requestOptions1)
                .load(FamilyData.profilePhoto)
                .thumbnail(0.5f)
                .into(iv_more_edit_profile_img)
        }

    }

    override fun onResume() {
        super.onResume()
        getUserResponse()
    }
    override fun onRestart() {
        super.onRestart()
        getUserResponse()
    }


//    var nickname : Editable =
//    var statusMessage : Editable = tv_more_editprofile_statusmessage.text
//    var birth : Editable = tv_more_editprofile_birth_text.text
//    var sex : Editable = tv_more_editprofile_sexType.text

    private fun setOnClickListener() {
        edit_nickname_statusMessage_btn.setOnClickListener {
            // 닉네임 수정
            tv_more_editprofile_nickname.isEnabled = true
            tv_more_editprofile_statusmessage.isEnabled = true
            more_edit_complete_btn.visibility = View.VISIBLE
        }
        tv_more_editprofile_birth_edit.setOnClickListener {
            // 생일 수정
            tv_more_editprofile_birth_text.isEnabled = true
            val newFragment = DatePickerFragment3()
            newFragment.show(fragmentManager, "Date Picker")
            more_edit_complete_btn.visibility = View.VISIBLE
        }
        tv_more_editprofile_gender_edit.setOnClickListener {
            // 성별 수정
            tv_more_editprofile_sexType.isEnabled = true
            more_edit_complete_btn.visibility = View.VISIBLE
        }
        more_edit_complete_btn.setOnClickListener {
            //수정완료 통신
            getMoreEditProfileResponse()
        }
        edit_user_profile_photo.setOnClickListener {
            // 앨범열고 통신하기
            FLAG = 1
            requestReadExternalStoragePermission()
        }
        edit_user_back_photo.setOnClickListener {
            FLAG = 2
            requestReadExternalStoragePermission()
        }
    }
    //이 메소드는 외부저장소(앨범과 같은)에 접근 관련해 권한 요청을 하는 로직을 메소드로 만든 것입니다!
    private fun requestReadExternalStoragePermission(){
        //첫번째 if문을 통해 과거에 이미 권한 메시지에 대한 OK를 했는지 아닌지에 대해 물어봅니다!
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)){
                //사용자에게 권한을 왜 허용해야되는지에 메시지를 주기 위한 대한 로직을 추가하려면 이 블락에서 하면됩니다!!
                //하지만 우리는 그냥 비워놓습니다!! 딱히 줄말 없으면 비워놔도 무관해요!!! 굳이 뭐 안넣어도됩니다!
            } else {
                //아래 코드는 권한을 요청하는 메시지를 띄우는 기능을 합니다! 요청에 대한 결과는 callback으로 onRequestPermissionsResult 메소드에서 받습니다!!!
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), My_READ_STORAGE_REQUEST_CODE)
            }
        } else {
            //첫번째 if문의 else로써, 기존에 이미 권한 메시지를 통해 권한을 허용했다면 아래와 같은 곧바로 앨범을 여는 메소드를 호출해주면됩니다!!
            showAlbum()
        }
    }

    //외부저장소(앨범과 같은)에 접근 관련 요청에 대해 OK를 했는지 거부했는지를 callback으로 받는 메소드입니다!
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        //onActivityResult와 같은 개념입니다. requestCode로 어떤 권한에 대한 callback인지를 체크합니다.
        if (requestCode == My_READ_STORAGE_REQUEST_CODE){
            //결과에 대해 허용을 눌렀는지 체크하는 조건문이구요!
            if(grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                //이곳은 외부저장소 접근을 허용했을 때에 대한 로직을 쓰시면됩니다. 우리는 앨범을 여는 메소드를 호출해주면되겠죠?
                showAlbum()
            } else {
                //이곳은 외부저장소 접근 거부를 했을때에 대한 로직을 넣어주시면 됩니다.
                finish()
            }
        }
    }

    //앨범을 여는 메소드입니다!
    //앨범에서 사진을 선택한 결과를 받기위해 startActivityForResult를 통해 앨범 엑티비티를 열어요!
    private fun showAlbum(){
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = android.provider.MediaStore.Images.Media.CONTENT_TYPE
        intent.data = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        startActivityForResult(intent, REQUEST_CODE_SELECT_IMAGE)
    }

    //startActivityForResult를 통해 실행한 엑티비티에 대한 callback을 처리하는 메소드입니다!
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        //REQUEST_CODE_SELECT_IMAGE를 통해 앨범에서 보낸 요청에 대한 Callback인지를 체크!!!
        if (requestCode == REQUEST_CODE_SELECT_IMAGE) {
            //앨범 사진 선택에 대한 Callback이 RESULT_OK인지 체크!!
            if (resultCode == Activity.RESULT_OK) {
                //data.data에는 앨범에서 선택한 사진의 Uri가 들어있습니다!! 그러니까 제대로 선택됐는지 null인지 아닌지를 체크!!!
                if(data != null){
                    val selectedImageUri : Uri = data.data
                    //Uri를 getRealPathFromURI라는 메소드를 통해 절대 경로를 알아내고, 인스턴스 변수인 imageURI에 String으로 넣어줍니다!
                    imageURI = getRealPathFromURI(selectedImageUri)
                    when(FLAG){
                        1 -> putUserProfileImagedResponse()
                        2 -> putUserBackImagedResponse()
                    }
//                    getUserResponse()
                }
            }
        }
    }

    //Uri에 대한 절대 경로를 리턴하는 메소드입니다! 굳이 코드를 해석하려고 하지말고,
    //앱잼때 코드를 복붙을 통해 재사용해주세요!!
    fun getRealPathFromURI(content : Uri) : String {
        val proj = arrayOf(MediaStore.Images.Media.DATA)
        val loader : CursorLoader = CursorLoader(this, content, proj, null, null, null)
        val cursor : Cursor = loader.loadInBackground()
        val column_idx = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        cursor.moveToFirst()
        val result = cursor.getString(column_idx)
        cursor.close()
        return result
    }

    private fun getMoreEditProfileResponse() {
        val token : String = FamilyData.token
        var sexType : Int = 1
        if(tv_more_editprofile_sexType.text.toString() == "남자"){
            sexType = 1
        }else {
            sexType = 0
        }
        var birthDay :String = tv_more_editprofile_birth_text.text.toString()+"T00:00"
        //Json 형식의 객체 만들기
        var jsonObject = JSONObject()
        jsonObject.put("userName", tv_more_editprofile_nickname.text.toString())
        jsonObject.put("birthday", birthDay)
        jsonObject.put("sexType", sexType)
        jsonObject.put("statusMessage", tv_more_editprofile_statusmessage.text.toString())

        //Gson 라이브러리의 Json Parser을 통해 객체를 Json으로!
        val gsonObject = JsonParser().parse(jsonObject.toString()) as JsonObject

        //통신 시작
        val putEditProfileResponse: Call<PutEditProfileResponse> =
            networkService.putEditProfileResponse("application/json", token, gsonObject)
        putEditProfileResponse.enqueue(object : Callback<PutEditProfileResponse> {
            override fun onFailure(call: Call<PutEditProfileResponse>, t: Throwable) {
                Log.e("edit Fail", t.toString())
            }
            override fun onResponse(call: Call<PutEditProfileResponse>, response: Response<PutEditProfileResponse>) {
                if (response.body()!!.message == "회원 정보 수정 성공") {
                    FamilyData.userName = tv_more_editprofile_nickname.text.toString()
                    FamilyData.sexType = sexType
                    FamilyData.statusMessage = tv_more_editprofile_statusmessage.text.toString()
                    FamilyData.birthday = birthDay
                    finish()
                } else {
                    Log.e("error", "error")
                    startActivity<MoreActivity>()
                    finish()
                }
            }
        })
    }

    private fun putUserProfileImagedResponse() {
        if (imageURI!= null) {
            //Multipart 형식은 String을 RequestBody 타입으로 바꿔줘야 합니다!
            val token = SharedPreferenceController.getAuthorization(this)
            //아래 3줄의 코드가 이미지 파일을 서버로 보내기 위해 MultipartBody.Part 형식으로 만드는 로직입니다.
            // imageURI는 앨범에서 선택한 이미지에 대한 절대 경로가 담겨있는 인스턴스 변수입니다.
            var data : MultipartBody.Part? = null

            val file : File = File(imageURI)
            Log.d("uuuu1", "사진들어오기"+imageURI)
            val requestfile : RequestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file)
            data = MultipartBody.Part.createFormData("profilePhoto", file.name, requestfile)
                Log.d("uuuu1", data.toString())
            val putUserPhotoResponse =
                networkService.putUserPhotoResponse(token, data)

            putUserPhotoResponse.enqueue(object : Callback<PutUserPhotoResponse> {
                override fun onFailure(call: Call<PutUserPhotoResponse>, t: Throwable) {
                    Log.e("write fail", t.toString())
                }
                override fun onResponse(call: Call<PutUserPhotoResponse>, response: Response<PutUserPhotoResponse>) {
                    if (response.isSuccessful) {
                        toast(response.body()!!.message)
                        //BoardActivity로 결과 보내기
                        setResult(Activity.RESULT_OK)
                        getUserResponse()
                        startActivity<MainActivity>()
                        finish()
                    }
                }
            })
        }
    }
// getUserResponse
    private fun putUserBackImagedResponse() {
        if (imageURI!= null) {
            //Multipart 형식은 String을 RequestBody 타입으로 바꿔줘야 합니다!
            val token = SharedPreferenceController.getAuthorization(this)
            //아래 3줄의 코드가 이미지 파일을 서버로 보내기 위해 MultipartBody.Part 형식으로 만드는 로직입니다.
            // imageURI는 앨범에서 선택한 이미지에 대한 절대 경로가 담겨있는 인스턴스 변수입니다.
            var data : MultipartBody.Part? = null

            val file : File = File(imageURI)
            Log.d("uuuu1", "사진들어오기"+imageURI)
            val requestfile : RequestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file)
            data = MultipartBody.Part.createFormData("backPhoto", file.name, requestfile)
            Log.d("uuuu1", data.toString())
            val putUserBackPhotoResponse =
                networkService.putUserBackPhotoResponse(token, data)

            putUserBackPhotoResponse.enqueue(object : Callback<PutUserPhotoResponse> {
                override fun onFailure(call: Call<PutUserPhotoResponse>, t: Throwable) {
                    Log.e("write fail", t.toString())
                }
                override fun onResponse(call: Call<PutUserPhotoResponse>, response: Response<PutUserPhotoResponse>) {
                    if (response.isSuccessful) {
                        toast(response.body()!!.message)
                        //BoardActivity로 결과 보내기
                        setResult(Activity.RESULT_OK)
                        getUserResponse()
                        finish()
                    }
                }
            })
        }
    }

    private fun getUserResponse() {
        if (FamilyData.token != null) {

            val getUserResponse =
                networkService.getUserResponse("application/json", token)

            getUserResponse.enqueue(object : Callback<GetUserResponse> {
                override fun onFailure(call: Call<GetUserResponse>, t: Throwable) {
                    Log.e("write fail", t.toString())
                }
                override fun onResponse(call: Call<GetUserResponse>, response: Response<GetUserResponse>) {
                    if (response.isSuccessful) {
                        when(FLAG){
                            1 ->{
                                if(response.body()!!.data.profilePhoto != null){
                                    FamilyData.profilePhoto = response.body()!!.data.profilePhoto
                                } else {
                                    FamilyData.profilePhoto = ""
                                }
                            }
                            2 -> {
                                if (response.body()!!.data.backPhoto != null){
                                    FamilyData.backPhoto = response.body()!!.data.backPhoto
                                } else {
                                    FamilyData.backPhoto = ""
                                }

                            }
                        }

                    }
                }
            })
        }
    }
}
