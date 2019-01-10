package com.sopt.famfam.activity

import android.app.Activity
import android.content.ContentUris
import android.content.Context
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView

import com.sopt.famfam.R
import com.sopt.famfam.adapter.Permissions
import com.sopt.famfam.database.FamilyData
import com.sopt.famfam.database.SharedPreferenceController
import com.sopt.famfam.network.ApplicationController
import com.sopt.famfam.network.NetworkService
import com.sopt.famfam.post.PostWriteContentResponse

import org.w3c.dom.Text

import gun0912.tedbottompicker.TedBottomPicker
import kotlinx.android.synthetic.main.activity_add_post.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

import java.io.File
import java.net.URI
import java.util.ArrayList

class AddPostActivity : AppCompatActivity() {
    private var photoSize = 0
    private var list: ArrayList<Uri>? = null
    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }
    // 권한설정
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_post)
        //권한이 있는지 확인
        if (checkPermissionsArray(com.sopt.famfam.adapter.Permissions.PERMISSIONS)) {

        } else {
            verifyPermissions(Permissions.PERMISSIONS)
        }


        val btn_back = findViewById<ImageView>(R.id.btn_backArrow)
        btn_back.setOnClickListener {
            onBackPressed()    //뒤로가기
        }
        val bottomSheetDialogFragment = TedBottomPicker.Builder(this@AddPostActivity)
                .setOnMultiImageSelectedListener { uriList ->
                    // here is selected uri list
                    list=uriList
                    photoSize = list!!.size
                    if (photoSize > 0) {
                        (findViewById<View>(R.id.tv_add_post_count) as TextView).text = photoSize.toString() + "+"
                        (findViewById<View>(R.id.iv_add_post) as ImageView).setImageURI(uriList[0])
                    }

                }
                .setPeekHeight(3000)
                .showTitle(false)
                .setCompleteButtonText("완료")
                .setEmptySelectionText("선택 없음")
                .setSelectMaxCount(5)
                .create()
        bottomSheetDialogFragment.show(supportFragmentManager)


        // 전송 버튼
        findViewById<View>(R.id.tvNext).setOnClickListener { getWriteBoardResponse(list!!)}

    }

    fun getPathFromUri(uri: Uri): String {
        val cursor = contentResolver.query(uri, null, null, null, null)
        cursor!!.moveToNext()
        val path = cursor.getString(cursor.getColumnIndex("_data"))
        cursor.close()
        return path
    }

    private fun getWriteBoardResponse(list : ArrayList<Uri>) {
        val input_contents = tv_add_post.text.toString()
        if (input_contents.isNotEmpty()) {
            //Multipart 형식은 String을 RequestBody 타입으로 바꿔줘야 합니다!
            val token = SharedPreferenceController.getAuthorization(this)
            var contents = RequestBody.create(MediaType.parse("text/plain"), input_contents)

            var images = ArrayList<MultipartBody.Part>()
            //images.add(MultipartBody.Part.createFormData("contents",input_contents))
            for (index in list) {
                val file  = File(getPath(this,index));
                val surveyBody = RequestBody.create(MediaType.parse("image/*"), file)
                images.add(MultipartBody.Part.createFormData("photos", file.name, surveyBody))
            }
            Log.d("asd","업로드"+FamilyData.token)
            val postWriteBoardResponse = networkService.postWriteContentResponse(token, contents,images)
            postWriteBoardResponse.enqueue(object : Callback<PostWriteContentResponse> {
                override fun onFailure(call: Call<PostWriteContentResponse>, t: Throwable) {
                    Log.e("asd", t.toString())
                }
                override fun onResponse(call: Call<PostWriteContentResponse>, response: Response<PostWriteContentResponse>) {
                    Log.d("asd","asdhhh"+response.body().toString())
                    if (response.isSuccessful) {
                        toast(response.body()!!.message)
                        finish()
                    } else {
                         toast(response.body().toString())
                    }
                }
            })
        }
    }

    //권한 갯수 확인
    fun checkPermissionsArray(permissions: Array<String>): Boolean {

        for (i in permissions.indices) {
            val check = permissions[i]
            if (!checkPermissions(check)) {
                return false
            }
        }
        return true
    }

    //권한 단일 요청
    fun checkPermissions(permission: String): Boolean {

        val permissionRequest = ActivityCompat.checkSelfPermission(this@AddPostActivity, permission)

        return if (permissionRequest != PackageManager.PERMISSION_GRANTED) {
            false
        } else {
            true
        }
    }

    //권한 한번에 요청
    fun verifyPermissions(permissions: Array<String>) {

        ActivityCompat.requestPermissions(
                this@AddPostActivity,
                permissions,
                VERIFY_PERMISSIONS_REQUEST
        )
    }

    companion object {

        private val VERIFY_PERMISSIONS_REQUEST = 1
    }
    fun getPath(context: Context, uri: Uri): String? {

        val isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            if (isExternalStorageDocument(uri)) {
                val docId = DocumentsContract.getDocumentId(uri)
                val split = docId.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                val type = split[0]
                if ("primary".equals(type, ignoreCase = true)) {
                    return Environment.getExternalStorageDirectory().toString() + "/" + split[1]
                }
            } else if (isDownloadsDocument(uri)) {
                val id = DocumentsContract.getDocumentId(uri)
                val contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), java.lang.Long.valueOf(id))
                return getDataColumn(context, contentUri, null, null)
            } else if (isMediaDocument(uri)) {
                val docId = DocumentsContract.getDocumentId(uri)
                val split = docId.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                val type = split[0]

                var contentUri: Uri? = null
                if ("image" == type) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                } else if ("video" == type) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                } else if ("audio" == type) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
                }

                val selection = "_id=?"
                val selectionArgs = arrayOf(split[1])

                return getDataColumn(context, contentUri, selection, selectionArgs)
            }// MediaProvider
        } else if ("content".equals(uri.getScheme(), ignoreCase = true)) {
            return getDataColumn(context, uri, null, null)
        } else if ("file".equals(uri.getScheme(), ignoreCase = true)) {
            return uri.getPath()
        }

        return null
    }

    fun getDataColumn(context: Context, uri: Uri?, selection: String?, selectionArgs: Array<String>?): String? {
        var cursor: Cursor? = null
        val column = "_data"
        val projection = arrayOf(column)
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null)
            if (cursor != null && cursor!!.moveToFirst()) {
                val column_index = cursor!!.getColumnIndexOrThrow(column)
                return cursor!!.getString(column_index)
            }
        } finally {
            if (cursor != null)
                cursor!!.close()
        }
        return null
    }

    fun isExternalStorageDocument(uri: Uri): Boolean {
        return "com.android.externalstorage.documents" == uri.getAuthority()
    }

    fun isDownloadsDocument(uri: Uri): Boolean {
        return "com.android.providers.downloads.documents" == uri.getAuthority()
    }

    fun isMediaDocument(uri: Uri): Boolean {
        return "com.android.providers.media.documents" == uri.getAuthority()
    }

}

//
//package com.sopt.famfam.activity;
//
//import android.content.pm.PackageManager;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.net.Uri;
//import android.os.Bundle;
//import android.support.v4.app.ActivityCompat;
//import android.support.v7.app.AppCompatActivity;
//import android.util.Log;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.sopt.famfam.R;
//import com.sopt.famfam.adapter.Permissions;
//import com.sopt.famfam.database.SharedPreferenceController;
//
//import org.w3c.dom.Text;
//
//import gun0912.tedbottompicker.TedBottomPicker;
//import okhttp3.MediaType;
//import okhttp3.MultipartBody;
//import okhttp3.RequestBody;
//
//import java.io.File;
//import java.util.ArrayList;
//
//public class AddPostActivity extends AppCompatActivity {
//
//    private static final int VERIFY_PERMISSIONS_REQUEST = 1;
//    private int photoSize=0;
//    private ArrayList<String> list;
//    // 권한설정
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_add_post);
//        //권한이 있는지 확인
//        if(checkPermissionsArray(com.sopt.famfam.adapter.Permissions.PERMISSIONS)) {
//
//        }else{
//            verifyPermissions(Permissions.PERMISSIONS);
//        }
//
//
//        ImageView btn_back = findViewById(R.id.btn_backArrow);
//        btn_back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                onBackPressed();    //뒤로가기
//            }
//        });
//        TedBottomPicker bottomSheetDialogFragment = new TedBottomPicker.Builder(AddPostActivity.this)
//                .setOnMultiImageSelectedListener(new TedBottomPicker.OnMultiImageSelectedListener() {
//                    @Override
//                    public void onImagesSelected(ArrayList<Uri> uriList) {
//                        // here is selected uri list
//                        for(int i =0;i<uriList.size();i++)
//                        {
//                            list = new ArrayList<String>();
//                            list.add(uriList.get(i).toString());
//                        }
//                        photoSize= list.size();
//                        if(photoSize>0)
//                        {
//                            ((TextView)findViewById(R.id.tv_add_post_count)).setText(photoSize+"+");
//                            ((ImageView)findViewById(R.id.iv_add_post)).setImageURI(uriList.get(0));
//                        }
//
//                    }
//
//                })
//                .setPeekHeight(3000)
//                .showTitle(false)
//                .setCompleteButtonText("완료")
//                .setEmptySelectionText("선택 없음")
//                .setSelectMaxCount(5)
//                .create();
//        bottomSheetDialogFragment.show(getSupportFragmentManager());
//
//
//        // 전송 버튼
//        findViewById(R.id.tvNext).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//
//            }
//        });
//
//    }
//
//
//
//    //권한 갯수 확인
//    public boolean checkPermissionsArray(String[] permissions){
//
//        for(int i = 0; i< permissions.length; i++){
//        String check = permissions[i];
//        if(!checkPermissions(check)){
//            return false;
//        }
//    }
//        return true;
//    }
//    //권한 단일 요청
//    public boolean checkPermissions(String permission){
//
//        int permissionRequest = ActivityCompat.checkSelfPermission(AddPostActivity.this, permission);
//
//        if(permissionRequest != PackageManager.PERMISSION_GRANTED){
//            return false;
//        }
//        else{
//            return true;
//        }
//    }
//    //권한 한번에 요청
//    public void verifyPermissions(String[] permissions){
//
//        ActivityCompat.requestPermissions(
//                AddPostActivity.this,
//                permissions,
//                VERIFY_PERMISSIONS_REQUEST
//        );
//    }
//
//
//
//}
