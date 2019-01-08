package com.sopt.famfam.activity;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import com.sopt.famfam.R;
import com.sopt.famfam.adapter.Permissions;

public class AddPostActivity extends AppCompatActivity {

    private static final int VERIFY_PERMISSIONS_REQUEST = 1;

    // 권한설정
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);
    //권한이 있는지 확인
        if(checkPermissionsArray(com.sopt.famfam.adapter.Permissions.PERMISSIONS)) {

        }else{
            verifyPermissions(Permissions.PERMISSIONS);
        }


        ImageView btn_back = findViewById(R.id.backArrow);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onBackPressed();    //뒤로가기
            }
        });
    }
    //권한 갯수 확인
    public boolean checkPermissionsArray(String[] permissions){

        for(int i = 0; i< permissions.length; i++){
            String check = permissions[i];
            if(!checkPermissions(check)){
                return false;
            }
        }
        return true;
    }
    //권한 단일 요청
    public boolean checkPermissions(String permission){

        int permissionRequest = ActivityCompat.checkSelfPermission(AddPostActivity.this, permission);

        if(permissionRequest != PackageManager.PERMISSION_GRANTED){
            return false;
        }
        else{
            return true;
        }
    }
    //권한 한번에 요청
    public void verifyPermissions(String[] permissions){

        ActivityCompat.requestPermissions(
                AddPostActivity.this,
                permissions,
                VERIFY_PERMISSIONS_REQUEST
        );
    }



}
