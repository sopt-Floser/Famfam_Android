
package com.sopt.famfam.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.sopt.famfam.R;
import com.sopt.famfam.adapter.item.AllPhotoItem;

import java.util.ArrayList;


public class AllPhotoAdapter extends RecyclerView.Adapter<AllPhotoAdapter.ViewHolder> {

    private ArrayList<AllPhotoItem> items = new ArrayList<>();


    @NonNull
    @Override
    public AllPhotoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_all_photo, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AllPhotoAdapter.ViewHolder viewHolder, int position) {

        AllPhotoItem item = items.get(position);

        Glide.with(viewHolder.itemView.getContext())
                .load(item.getUrl())
                .into(viewHolder.iv_allphoto);

    }

    @Override
    public int getItemCount() { return items.size(); }

    public void setItems(ArrayList<AllPhotoItem> items){ this.items = items;}


    class ViewHolder extends RecyclerView.ViewHolder{

        ImageView iv_allphoto;

        ViewHolder(View itemView){
            super(itemView);

            iv_allphoto = itemView.findViewById(R.id.iv_allphoto);
        }
    }
}
