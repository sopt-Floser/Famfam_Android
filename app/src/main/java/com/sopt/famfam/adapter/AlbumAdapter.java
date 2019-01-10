package com.sopt.famfam.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.sopt.famfam.R;
import com.sopt.famfam.adapter.item.AlbumItem;

import java.util.ArrayList;


public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.ViewHolder> {

    private ArrayList <AlbumItem> items = new ArrayList<>();

    @NonNull
    @Override
    public AlbumAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_album,parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AlbumAdapter.ViewHolder viewHolder, int position) {

        AlbumItem item = items.get(position);

        Glide.with(viewHolder.itemView.getContext())
                .load(item.getUrl())
                .into(viewHolder.iv_album);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(ArrayList<AlbumItem> items){
        this.items = items;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView iv_album;

        public ViewHolder(View view) {
            super(view);

            iv_album = itemView.findViewById(R.id.iv_album);
        }
    }
}
