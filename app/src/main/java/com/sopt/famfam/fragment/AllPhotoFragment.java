package com.sopt.famfam.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.sopt.famfam.R;
import com.sopt.famfam.adapter.AllPhotoAdapter;
import com.sopt.famfam.data.SampleDate;

public class AllPhotoFragment extends Fragment {

    AllPhotoAdapter adapter = new AllPhotoAdapter();

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.fragment_allphoto, container, false);

        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.rv_allphoto);

        recyclerView.setAdapter(adapter);
        //열(column)의 개수가 3인 그리드 레이아웃
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),3));

        adapter.setItems(new SampleDate().getItems());
        return view;

    }
}