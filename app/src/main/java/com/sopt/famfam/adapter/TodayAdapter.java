
package com.sopt.famfam.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.sopt.famfam.R;


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

       ImageView profile, post, emotion_off, emotion_on, emotion_lay1, emotion_lay2;
       TextView username, posted_time, img_likes, cation, comment, comment_count;


        public ViewHolder(View view) {
            super(view);
            profile = view.findViewById(R.id.iv_profile_photo);
            username = view.findViewById(R.id.tv_username);
            posted_time = view.findViewById(R.id.tv_posted_time);
            post = view.findViewById(R.id.vp_post_img);
            emotion_off = view.findViewById(R.id.iv_emotion_off);
            emotion_on = view.findViewById(R.id.iv_emotion_on);
            emotion_lay1 = view.findViewById(R.id.iv_feel);
            emotion_lay2 = view.findViewById(R.id.iv_feel2);
            img_likes = view.findViewById(R.id.tv_image_likes);
            cation = view.findViewById(R.id.tv_post_caption);
            comment = view.findViewById(R.id.tv_comment);
            comment_count = view.findViewById(R.id.tv_comment_count);

        }



    }
}


