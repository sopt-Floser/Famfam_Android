package com.sopt.famfam.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import com.sopt.famfam.R;
import com.sopt.famfam.adapter.AlbumAdapter;
import com.sopt.famfam.data.SampleData;

public class AlbumFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_album, container, false);
        ScrollView scrollView = (ScrollView)view.findViewById(R.id.sv_album);
        scrollView.smoothScrollTo(0,0);// scroll to top of screen

        //앨범 리사이클러뷰
        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.rv_album);
        //열(column)의 개수가 3인 그리드 레이아웃
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),3));
        AlbumAdapter albumAdapter = new AlbumAdapter();
        recyclerView.setAdapter(albumAdapter);

        TextView edit_profile = (TextView)view.findViewById(R.id.tv_edit_profile);
        edit_profile.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {

                                                // 프로필 수정 눌렀을 때 발생할 이벤트

                                            }
                                        });


                //아이템 로드
        albumAdapter.setItems(new SampleData().getItems());

        return view;
    }
}
