package com.sopt.famfam.fragment;

import android.os.Bundle;
import android.widget.ImageView;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.sopt.famfam.R;
import com.sopt.famfam.adapter.AlbumAdapter;
import com.sopt.famfam.adapter.item.AlbumItem;
import com.sopt.famfam.database.FamilyData;
import com.sopt.famfam.database.User;
import com.sopt.famfam.get.GetPhotoListResponse;
import com.sopt.famfam.get.Photos;
import com.sopt.famfam.network.ApplicationController;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;

public class AlbumFragment extends Fragment {
    int idx = -1;
    AlbumAdapter albumAdapter;
    RecyclerView recyclerView;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        idx = getArguments().getInt("useridx", -1);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_album, container, false);
        ScrollView scrollView = (ScrollView) view.findViewById(R.id.sv_album);
        scrollView.smoothScrollTo(0, 0);// scroll to top of screen

        //앨범 리사이클러뷰
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_album);
        //열(column)의 개수가 3인 그리드 레이아웃
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        albumAdapter = new AlbumAdapter();
        recyclerView.setAdapter(albumAdapter);

        TextView username = view.findViewById(R.id.username);
        username.setText(getUserDate(idx).getUserName());

        TextView display_name = view.findViewById(R.id.display_name);
        display_name.setText(getUserDate(idx).getUserName());

        ImageView iv_profile = view.findViewById(R.id.iv_profile_photo);
        Glide.with(getContext()).load(getUserDate(idx).getProfilePhoto()).into(iv_profile);

        TextView tv_album_statusmessage = view.findViewById(R.id.tv_album_statusmessage);
        tv_album_statusmessage.setText(getUserDate(idx).getUserName());

        TextView edit_profile = (TextView) view.findViewById(R.id.tv_edit_profile);
        edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 프로필 수정 눌렀을 때 발생할 이벤트

            }
        });
        //아이템 로드
       // albumAdapter.setItems(new SampleData().getItems());
        getPhotoListResponse();
        return view;
    }
    private User getUserDate(int idx )
    {
        for(int i=0;i< FamilyData.users.size();i++)
        {
            if(FamilyData.users.get(i).getUserIdx()==idx)
                return FamilyData.users.get(i);
        }
        return FamilyData.users.get(0);
    }
    private void getPhotoListResponse() {
        Call<GetPhotoListResponse> getBoardListResponse = ApplicationController.instance.networkService.getPhotoListResponse(FamilyData.token, idx,0,20);
        getBoardListResponse.enqueue(new Callback<GetPhotoListResponse>() {
            @Override
            public void onResponse(Call<GetPhotoListResponse> call, Response<GetPhotoListResponse> response) {
                if (response.isSuccessful()) {
                    ArrayList<Photos> list = response.body().getData().getPhotos();
                    ArrayList<AlbumItem> list2 = new ArrayList<>();
                    for(int i =0;i<list.size();i++)
                        list2.add(new AlbumItem(list.get(i).getPhotoName()));
                    albumAdapter.setItems(list2);
                    recyclerView.setAdapter(albumAdapter);

                }
            }

            @Override
            public void onFailure(Call<GetPhotoListResponse> call, Throwable t) {
                // Code...
            }
        });
    }
}
