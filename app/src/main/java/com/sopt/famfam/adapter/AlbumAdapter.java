package com.sopt.famfam.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;


public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.ViewHolder> {
    @NonNull
    @Override
    public AlbumAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull AlbumAdapter.ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {

        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View view) {
            super(view);
        }
    }
}
