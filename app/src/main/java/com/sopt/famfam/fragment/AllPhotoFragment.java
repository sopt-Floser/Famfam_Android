package com.sopt.famfam.fragment;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.sopt.famfam.R;
import com.sopt.famfam.adapter.AllPhotoAdapter;
import com.sopt.famfam.data.SampleData2;


public class AllPhotoFragment extends Fragment {

    AllPhotoAdapter allPhotoAdapter;
    RecyclerView recyclerView;

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
                TodayFragment fragment = new TodayFragment();
                FragmentManager fragmentManager = getChildFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.fragment_allphoto, fragment);
                transaction.commit();
                }
               // popBackStack();
        });

//        getPhotoListResponse();
        return view;
    }

//    private void getPhotoListResponse() {
//        Call<GetPhotoListResponse> getBoardListResponse = ApplicationController.instance.networkService.getPhotoListResponse(FamilyData.token);
//        getBoardListResponse.enqueue(new Callback<GetPhotoListResponse>() {
//            @Override
//            public void onResponse(Call<GetPhotoListResponse> call, Response<GetPhotoListResponse> response) {
//                if (response.isSuccessful()) {
//
//                    ArrayList<Photos> list = response.body().getData().getPhotos();
//                    ArrayList<AllPhotoItem> list2 = new ArrayList<>();
//
//                    allPhotoAdapter.setItems(list2);
//                    recyclerView.setAdapter(allPhotoAdapter);
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<GetPhotoListResponse> call, Throwable t) {
//                // Code...
//            }
//        });
//    }
}