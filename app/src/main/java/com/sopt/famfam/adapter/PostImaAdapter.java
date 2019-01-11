package com.sopt.famfam.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.sopt.famfam.R;
import com.sopt.famfam.adapter.item.PostImgItem;

import java.util.ArrayList;

public class PostImaAdapter extends RecyclerView.Adapter<PostImaAdapter.ViewHolder> {

    private ArrayList<PostImgItem> postImgItemArrayList;
    public PostImaAdapter(ArrayList<PostImgItem> postImgItemArrayList){
        this.postImgItemArrayList = postImgItemArrayList;
    }
    @NonNull
    @Override
    public PostImaAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post_img,parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PostImaAdapter.ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return postImgItemArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView img1, img2, img3;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            img1 = itemView.findViewById(R.id.iv_post_img1);
            img2 = itemView.findViewById(R.id.iv_post_img2);
            img3 = itemView.findViewById(R.id.iv_post_img3);

        }
    }
}
