package com.sopt.famfam.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.sopt.famfam.R;
import com.sopt.famfam.adapter.AllPhotoAdapter;
import com.sopt.famfam.data.SampleData2;


public class AllPhotoFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, @Nullable Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.fragment_allphoto, container, false);

        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.rv_allphoto);
        //열(column)의 개수가 3인 그리드 레이아웃
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),3));
        AllPhotoAdapter allPhotoAdapter = new AllPhotoAdapter();
        recyclerView.setAdapter(allPhotoAdapter);

        allPhotoAdapter.setItems(new SampleData2().getItems());

        ImageView btn_back = (ImageView)view.findViewById(R.id.btn_backArrow);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               // popBackStack();
            }
        });

        return view;
    }
}