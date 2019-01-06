
package com.sopt.famfam.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;


public class TodayAdapter extends RecyclerView.Adapter<TodayAdapter.ViewHolder> {

/*    recyclerView = (RecyclerView)view.findViewById(R.id.rv_today_feed);
    recyclerView = new Adapter(this, list);
    recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
    recyclerView.setAdapter(adapter);*/

   public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

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


