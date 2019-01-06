package com.sopt.famfam.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.sopt.famfam.R;
import com.sopt.famfam.adapter.CommentAdapter;
import com.sopt.famfam.adapter.item.CommentItem;

import java.util.ArrayList;

public class CommentActivity extends AppCompatActivity{

    private CommentAdapter adapter = new CommentAdapter();

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        RecyclerView recyclerView = findViewById(R.id.rv_comment);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        ArrayList<CommentItem> items = new ArrayList<>();
        items.add(new CommentItem("","","",""));
        items.add(new CommentItem("","","",""));
        items.add(new CommentItem("","",".",""));
        items.add(new CommentItem("","",".",""));

        ImageView btn_back = findViewById(R.id.backArrow);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onBackPressed();    //뒤로가기
            }
        });
        TextView btn = findViewById(R.id.btn_post_comment);
        btn.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                //댓글 등록시 발생하는 내용

            }

        });

    }


}