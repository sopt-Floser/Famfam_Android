package com.sopt.famfam.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.sopt.famfam.R;
import com.sopt.famfam.activity.AddPostActivity;
import com.sopt.famfam.adapter.TodayAdapter;
import com.sopt.famfam.adapter.item.TodayItem;
import gun0912.tedbottompicker.TedBottomPicker;

import java.util.ArrayList;

public class TodayFragment extends Fragment {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //프래그먼트 이동
        Fragment AllPhotoFragment = new Fragment();
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_today, container, false);

        //리사이클러뷰안에 뷰페이저


        // 리사이클러뷰
        recyclerView = (RecyclerView) rootView.findViewById(R.id.rv_today_feed);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);


        ArrayList<TodayItem> todayItemArrayList = new ArrayList<>();
        todayItemArrayList.add(new TodayItem(R.drawable.kim_fam_big, "김팸팸", "2019-01-17", R.drawable.famfeed_1, R.drawable.icon_emoticon, R.drawable.like, "엄마님 외 2명", "엄마랑 데이트", "댓글", "1개"));
        todayItemArrayList.add(new TodayItem(R.drawable.kim_fam_big, "김팸팸", "2019-01-17", R.drawable.famfeed_1, R.drawable.icon_emoticon, R.drawable.like, "엄마님 외 2명", "엄마랑 데이트", "댓글", "1개"));
        todayItemArrayList.add(new TodayItem(R.drawable.kim_fam_big, "김팸팸", "2019-01-17", R.drawable.famfeed_1, R.drawable.icon_emoticon, R.drawable.like, "엄마님 외 2명", "엄마랑 데이트", "댓글", "1개"));

        TodayAdapter todayAdapter = new TodayAdapter(todayItemArrayList,getContext());
        recyclerView.setAdapter(todayAdapter);


        ImageView btnAddPost = (ImageView) rootView.findViewById(R.id.btn_today_addPost);

        btnAddPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddPostActivity.class);
                startActivity(intent);

            }
        });
        // 사진 전체보기 버튼
        ImageView btnAllPhoto = (ImageView) rootView.findViewById(R.id.btn_allPhoto);
        btnAllPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Fragment();
            }
        });

        return rootView;
    }
/*
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getInt(ARG_PARAM1);
        }
    }


    //스크롤했을때 사라진 뷰페이저의 상태를 저장
    @Override
    public void onViewRecycled(ViewHolder holder) {
        mViewPagerState.put(holder.getAdapterPosition(), holder.vp.getCurrentItem());
        super.onViewRecycled(holder);
    }
*/


}