package com.sopt.famfam.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.sopt.famfam.R;
import com.sopt.famfam.activity.AddPostActivity;
import com.sopt.famfam.adapter.item.TodayItem;

import java.util.ArrayList;

public class TodayFragment extends Fragment {

    private RecyclerView rvc;
    private RecyclerView.Adapter revAdapter;
    private ArrayList<TodayItem> list = new ArrayList<>();
    private static final String ARG_PARAM1 = "param1";
    private int mParam1;
    int img;

    public static TodayFragment newInstance(int param1) {

        TodayFragment fragment = new TodayFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getInt(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        Fragment AllPhotoFragment = new Fragment();

        View view = inflater.inflate(R.layout.fragment_today, container, false);



        // 게시물 올리기
        ImageView btnAddPost = (ImageView)view.findViewById(R.id.btn_today_addPost);
        btnAddPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddPostActivity.class);
                startActivity(intent);            }
        });
        // 사진 전체보기
        ImageView btnAllPhoto = (ImageView)view.findViewById(R.id.btn_allPhoto);
        btnAllPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Fragment();
            }
        });

        return view;
    }
}