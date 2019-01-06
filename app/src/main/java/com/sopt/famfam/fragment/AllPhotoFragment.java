/*

package com.sopt.famfam.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.sopt.famfam.R;

import java.util.ArrayList;

public class AllPhotoFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_allphoto, container, false);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rv_allphoto);

        recyclerView.setAdapter(new AllPhotoFragmentRecyclerViewAdatper());
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));

        return view;
    }

  //      private ArrayList<ContentDTO> contentDTOs;

        public AllPhotoFragmentRecyclerViewAdatper() {

 //           contentDTOs = new ArrayList<>();

        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            //현재 사이즈 뷰 화면 크기의 가로 크기의 1/3값을 가지고 오기
            int width = getResources().getDisplayMetrics().widthPixels / 3;

            ImageView imageView = new ImageView(parent.getContext());
            imageView.setLayoutParams(new LinearLayoutCompat.LayoutParams(width, width));

            return new CustomViewHolder(imageView);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        }

        private class CustomViewHolder extends RecyclerView.ViewHolder {
            public ImageView imageView;

            public CustomViewHolder(ImageView imageView) {
                super(imageView);
                this.imageView = imageView;
            }
        }
    }
}
*/
