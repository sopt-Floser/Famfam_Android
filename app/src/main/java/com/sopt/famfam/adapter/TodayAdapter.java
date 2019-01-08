
package com.sopt.famfam.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.sopt.famfam.R;
import com.sopt.famfam.adapter.item.TodayItem;
import com.sopt.famfam.database.FamilyData;
import com.sopt.famfam.fragment.PostFirstFragment;
import com.sopt.famfam.get.GetContentListResponse;
import com.sopt.famfam.get.Photos;
import com.sopt.famfam.network.ApplicationController;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.HashMap;


public class TodayAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private FragmentManager fragmentManager;
    HashMap<Integer, Integer> mViewPagerState = new HashMap<>();
    private Context context;
    public static  class TodayViewHolder extends RecyclerView.ViewHolder{

        ViewPager vp;
        ImageView profile, emotion_off, emotion_on, emotion_lay1, emotion_lay2;
        TextView username, posted_time, img_likes, cation, comment, comment_count;

        TodayViewHolder (@NonNull View itemView) {
            super(itemView);
            profile = itemView.findViewById(R.id.iv_profile_photo);
            username = itemView.findViewById(R.id.tv_username);
            posted_time = itemView.findViewById(R.id.tv_posted_time);
            vp = itemView.findViewById(R.id.vp_post_img);
            emotion_off = itemView.findViewById(R.id.iv_emotion_off);
            emotion_on = itemView.findViewById(R.id.iv_emotion_on);
            emotion_lay1 = itemView.findViewById(R.id.iv_feel);
            emotion_lay2 = itemView.findViewById(R.id.iv_feel2);
            img_likes = itemView.findViewById(R.id.tv_image_likes);
            cation = itemView.findViewById(R.id.tv_post_caption);
            comment = itemView.findViewById(R.id.tv_comment);
            comment_count = itemView.findViewById(R.id.tv_comment_count);
        }
    }

    private ArrayList<TodayItem> todayItemArrayList;
    public TodayAdapter(ArrayList<TodayItem> todayItemArrayList, Context context){
        this.todayItemArrayList = todayItemArrayList;
        this.context=context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_today,viewGroup,false);
        return new TodayViewHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {


        TodayViewHolder todayViewHolder = (TodayViewHolder) viewHolder;

        todayViewHolder.profile.setImageResource(todayItemArrayList.get(position).profile);
        todayViewHolder.username.setText(todayItemArrayList.get(position).name);
        todayViewHolder.posted_time.setText(todayItemArrayList.get(position).posted_time);
        getBoardListResponse( todayViewHolder.vp);
        todayViewHolder.emotion_off.setImageResource(todayItemArrayList.get(position).emotion);
        todayViewHolder.emotion_on.setImageResource(todayItemArrayList.get(position).emotion);
        todayViewHolder.emotion_lay1.setImageResource(todayItemArrayList.get(position).feel);
        todayViewHolder.emotion_lay2.setImageResource(todayItemArrayList.get(position).feel);
        todayViewHolder.img_likes.setText(todayItemArrayList.get(position).img_likes);
        todayViewHolder.cation.setText(todayItemArrayList.get(position).caption);
        todayViewHolder.comment.setText(todayItemArrayList.get(position).comment);
        todayViewHolder.comment_count.setText(todayItemArrayList.get(position).comment_count);

    }

    @Override
    public int getItemCount() {  return todayItemArrayList.size(); }

    private class PagerAdapter extends FragmentStatePagerAdapter {
        ArrayList<Fragment> frags =new ArrayList<Fragment>();
        Context context = null;
        ArrayList list;
        public PagerAdapter(FragmentManager fm,Context context,ArrayList<Photos> list) {
            super(fm);
            this.context=context;
            this.list=list;
            for(int i=0;i<list.size();i++)
            {
                frags.add(new PostFirstFragment());
                ((PostFirstFragment)frags.get(i)).setImageUri(list.get(i).component2());
            }

        }

        @Override
        public Fragment getItem(int position) {
            // 해당하는 page의 Fragment를 생성합니다.
            return frags.get(position);
        }

        @Override
        public int getCount() {
            return list.size();  // 총 5개의 page를 보여줍니다.
        }

    }
    private void getBoardListResponse(final ViewPager pager){
        Call<GetContentListResponse> getBoardListResponse = ApplicationController.instance.networkService.getContentListResponse(FamilyData.token, 0, 30);
        getBoardListResponse.enqueue(new Callback<GetContentListResponse>() {
            @Override
            public void onResponse(Call<GetContentListResponse> call, Response<GetContentListResponse> response) {
                if (response.isSuccessful()){
                    ArrayList<Photos> temp  = response.body().getData().component2();
                    if (temp.size() > 0){
                        pager.setAdapter(new PagerAdapter(fragmentManager,context,temp));
                    }
                }
            }
            @Override
            public void onFailure(Call<GetContentListResponse> call, Throwable t) {
                // Code...
            }
        });
    }
}


