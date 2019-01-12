
package com.sopt.famfam.adapter;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.sopt.famfam.R;
import com.sopt.famfam.adapter.item.AlbumItem;
import com.sopt.famfam.adapter.item.TodayItem;
import com.sopt.famfam.database.FamilyData;
import com.sopt.famfam.fragment.AlbumFragment;
import com.sopt.famfam.fragment.PostFirstFragment;
import com.sopt.famfam.get.GetFeelResponse;
import com.sopt.famfam.get.GetPhotoListResponse;
import com.sopt.famfam.get.Photos;
import com.sopt.famfam.network.ApplicationController;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;


public class TodayAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private FragmentManager fragmentManager;
    HashMap<Integer, Integer> mViewPagerState = new HashMap<>();
    private Context context;
    public static  class TodayViewHolder extends RecyclerView.ViewHolder{
        int i=0;
        ViewPager vp;
        ImageView profile, emotion_off, emotion_on, emotion_lay1, emotion_lay2;
        TextView username, posted_time, img_likes, cation, comment, comment_count;

        TodayViewHolder (@NonNull View itemView) {
            super(itemView);
            profile = itemView.findViewById(R.id.iv_profile_photo);
            username = itemView.findViewById(R.id.tv_username);
            posted_time = itemView.findViewById(R.id.tv_posted_time);
            vp = itemView.findViewById(R.id.vp_post_img1);
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
    public TodayAdapter(FragmentManager fragmentManager,ArrayList<TodayItem> todayItemArrayList, Context context){
        this.todayItemArrayList = todayItemArrayList;
        this.context=context;
        this.fragmentManager = fragmentManager;

    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public void onViewAttachedToWindow(@NonNull RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        TodayViewHolder todayViewHolder = (TodayViewHolder) holder;
        todayViewHolder.vp.getAdapter().notifyDataSetChanged();
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
        todayViewHolder.setIsRecyclable(false);
        todayViewHolder.i=position;
        final int p=position;
        if (todayItemArrayList.get(position).profile.equals("")==false)
            Glide.with(context).load(todayItemArrayList.get(position).profile).into(todayViewHolder.profile);
        Log.d("today",todayItemArrayList.get(position).profile);
        todayViewHolder.profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("today","onclick");
                Fragment frag = new AlbumFragment();
                Bundle data = new Bundle();
                data.putInt("useridx", todayItemArrayList.get(p).useridx);
                frag.setArguments(data);
                fragmentManager.beginTransaction().add(R.id.fragment_today1, frag).addToBackStack(null).commit();
                //fragmentManager.beginTransaction().attach(frag);
            }
        });
        todayViewHolder.username.setText(todayItemArrayList.get(position).name);
        SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date date=new Date();
        try {
            date = sim.parse(todayItemArrayList.get(position).posted_time);
        } catch (Exception e) {
            e.printStackTrace();
        }
        todayViewHolder.posted_time.setText((1900+date.getYear())+"."+(date.getMonth()+1)+"."+date.getDate());

        PagerAdapter adap = new PagerAdapter(fragmentManager,context, todayItemArrayList.get(position).post_img,position);
        todayViewHolder.vp.setAdapter(adap);
        adap.notifyDataSetChanged();
        Random rand = new Random();
        todayViewHolder.vp.setId(1+position+rand.nextInt(50000));
        todayViewHolder.vp.setOffscreenPageLimit(5);
//        todayViewHolder.emotion_off.setImageResource(todayItemArrayList.get(position).emotion);
//        todayViewHolder.emotion_on.setImageResource(todayItemArrayList.get(position).emotion);

        todayViewHolder.emotion_lay1.setImageResource(todayItemArrayList.get(position).feel);
        todayViewHolder.emotion_lay2.setImageResource(todayItemArrayList.get(position).feel);
        todayViewHolder.img_likes.setText(todayItemArrayList.get(position).img_likes);

        getFeelResponse(todayItemArrayList.get(position).post_img.get(0).getContentIdx(),  todayViewHolder.img_likes,todayViewHolder.emotion_lay1,todayViewHolder.emotion_lay2);
        if(todayViewHolder.cation.equals("")||todayViewHolder.cation==null)
            todayViewHolder.cation.setText("내용 없음");
        else
            todayViewHolder.cation.setText(todayItemArrayList.get(position).caption);
            todayViewHolder.comment.setText(todayItemArrayList.get(position).comment);
            todayViewHolder.comment_count.setText(todayItemArrayList.get(position).comment_count);

    }

    @Override
    public void onViewRecycled(RecyclerView.ViewHolder holder) {
        mViewPagerState.put(holder.getAdapterPosition(), ((TodayViewHolder)holder).vp.getCurrentItem());
        super.onViewRecycled(holder);
    }
    @Override
    public int getItemCount() {  return todayItemArrayList.size(); }



    private class PagerAdapter extends FragmentPagerAdapter {
        ArrayList<Fragment> frags =new ArrayList<Fragment>();
        Context context = null;
        ArrayList<Photos>  list;

        @Override
        public int getItemPosition(@NonNull Object object) {
            return POSITION_NONE;
        }

        public PagerAdapter(FragmentManager fm, Context context, ArrayList<Photos> list, int position) {
            super(fm);
            this.context=context;
            this.list=list;
            Log.d("asd","어뎁터"+list.size());
            for(int i=0;i<list.size();i++)
            {
                Log.d("asd","어뎁터 반복"+list.get(i).getPhotoName());
                frags.add(new PostFirstFragment());
                ((PostFirstFragment)frags.get(i)).setImageUri(list.get(i).getPhotoName(),fm,todayItemArrayList.get(position));
            }

        }

        @Override
        public Fragment getItem(int position) {
//            // 해당하는 page의 Fragment를 생성합니다.
           // return PostFirstFragment.newInstance(list.get(position).getPhotoName(),todayItemArrayList.get(position));

            return frags.get(position);
        }
//        @Override
//        public Fragment getItem(int position) {
//            return BlankFragment.newInstance(position);
//        }
        @Override
        public int getCount() {
            return list.size();  // 총 5개의 page를 보여줍니다.
        }

    }
    private int getEmo(int num)
    {
        if(num ==0)
            return R.drawable.smile;
        else if(num ==1)
            return R.drawable.sad;
        else if(num ==2)
            return R.drawable.amazing;
        else
            return R.drawable.like;
    }
    private void getFeelResponse(int idx, final TextView tv_count, final ImageView emo1, final ImageView emo2) {
        Call<GetFeelResponse> getBoardListResponse = ApplicationController.instance.networkService.getFeelResponse(FamilyData.token, idx);
        getBoardListResponse.enqueue(new Callback<GetFeelResponse>() {
            @Override
            public void onResponse(Call<GetFeelResponse> call, Response<GetFeelResponse> response) {
                if (response.isSuccessful()) {
                    Log.d("asd",response.body().toString());
                    if(response.body().getStatus()==204) {

                        emo1.setVisibility(View.INVISIBLE);
                        emo2.setVisibility(View.INVISIBLE);
                        return;
                    }
                    int count =response.body().getData().getFeelCount();
                    if(count==0) {
                        emo1.setVisibility(View.INVISIBLE);
                        emo2.setVisibility(View.INVISIBLE);

                        return;
                    }
                    if(count>1)
                    {
                        tv_count.setText(response.body().getData().getFirstUserName()+"님 외 "+(count-1)+"명");
                        Glide.with(context).load(getEmo(response.body().getData().getFeelTypes().get(0).getFeelType())).into(emo2);
                        Glide.with(context).load(getEmo(response.body().getData().getFeelTypes().get(1).getFeelType())).into(emo1);
                    }
                    else {
                        tv_count.setText(response.body().getData().getFirstUserName() + "님");
                        Glide.with(context).load(getEmo(response.body().getData().getFeelTypes().get(0).getFeelType())).into(emo2);
                        emo2.setVisibility(View.INVISIBLE);
                    }

                }
            }

            @Override
            public void onFailure(Call<GetFeelResponse> call, Throwable t) {
                // Code...
            }
        });
    }
}


