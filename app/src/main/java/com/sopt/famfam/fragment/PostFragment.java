package com.sopt.famfam.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.bumptech.glide.Glide;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sopt.famfam.R;
import com.sopt.famfam.activity.EditPostActivity;
import com.sopt.famfam.adapter.CommentAdapter;
import com.sopt.famfam.adapter.item.CommentItem;
import com.sopt.famfam.adapter.item.TodayItem;
import com.sopt.famfam.database.FamilyData;
import com.sopt.famfam.database.User;
import com.sopt.famfam.get.Comments;
import com.sopt.famfam.get.GetCommentListResponse;
import com.sopt.famfam.get.GetFeelResponse;
import com.sopt.famfam.get.Photos;
import com.sopt.famfam.indicator.CircleAnimIndicator;
import com.sopt.famfam.network.ApplicationController;
import com.sopt.famfam.network.NetworkService;
import com.sopt.famfam.post.PostFeelResponse;
import com.sopt.famfam.post.PostWriteCommentResponse;
import org.json.JSONException;
import org.json.JSONObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class PostFragment extends Fragment implements View.OnClickListener {
    ViewPager viewPager;
    int MAX_PAGE=3;
    Fragment cur_fragment = new Fragment();
    CircleAnimIndicator circleAnimIndicator;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    ScrollView scrollView;
    RelativeLayout emotionbar;
    ImageView emotion_on, emotion_off, emotion_smile, emotion_sad, emotion_amazing, emotion_like, iv_photo, feel1,feel2;
    TextView post_comment, username, tv_username, tv_caption;
    EditText editText;
    PopupWindow popupWindow;
    int count=0;
    TodayItem item;
    private User getUserDate(int idx )
    {
        for(int i=0;i< FamilyData.users.size();i++)
        {
            if(FamilyData.users.get(i).getUserIdx()==idx)
                return FamilyData.users.get(i);
        }
        return FamilyData.users.get(0);
    }
    public void setItem(TodayItem item)
    {
        this.item=item;
        Log.d("asdpost",item.name);
    }

    private void postFeelResponse(int contentIdx,int feel) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("feelType", feel);
        JsonObject gsonObject = (JsonObject) new JsonParser().parse(jsonObject.toString());
        Call<PostFeelResponse> getBoardListResponse = ApplicationController.instance.networkService.postFeelResponse("application/json",FamilyData.token, contentIdx , gsonObject);
        getBoardListResponse.enqueue(new Callback<PostFeelResponse>() {
            @Override
            public void onResponse(Call<PostFeelResponse> call, Response<PostFeelResponse> response) {
                if (response.isSuccessful()) {
                    Log.d("성공", response.message());
                }else {
                    Log.e("에러", response.message());
                }
            }
            @Override
            public void onFailure(Call<PostFeelResponse> call, Throwable t) {
                // Code...
            }
        });
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
                        Glide.with(getContext()).load(getEmo(response.body().getData().getFeelTypes().get(0).getFeelType())).into(emo2);
                        Glide.with(getContext()).load(getEmo(response.body().getData().getFeelTypes().get(1).getFeelType())).into(emo1);
                    }
                    else {
                        tv_count.setText(response.body().getData().getFirstUserName() + "님");
                        Glide.with(getContext()).load(getEmo(response.body().getData().getFeelTypes().get(0).getFeelType())).into(emo2);
                        emo1.setVisibility(View.INVISIBLE);
                    }
                }
            }

            @Override
            public void onFailure(Call<GetFeelResponse> call, Throwable t) {
                // Code...
            }
        });
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_post, container, false);

        scrollView = (ScrollView)view.findViewById(R.id.sv_post_body);
        scrollView.smoothScrollTo(0,0);// scroll to top of screen

        username = view.findViewById(R.id.username);
        username.setText(item.name);
        tv_username = view.findViewById(R.id.tv_username);
        tv_username.setText(item.name);

        feel1 =view.findViewById(R.id.iv_feel);
        feel2 =view.findViewById(R.id.iv_feel2);
        TextView tv_act = view.findViewById(R.id.tv_image_likes);
        getFeelResponse(item.post_img.get(0).getContentIdx(),tv_act,feel1,feel2);
        iv_photo = view.findViewById(R.id.iv_profile_photo);
        Glide.with(getContext()).load(item.profile).into(iv_photo);

        tv_caption=view.findViewById(R.id.tv_post_caption);
        tv_caption.setText(item.caption);
        emotion_on = (ImageView)view.findViewById(R.id.iv_emotion_on);
        emotion_off = (ImageView)view.findViewById(R.id.iv_emotion_off);
        emotionbar = (RelativeLayout)view.findViewById(R.id.emotion_bar);
        emotion_smile = (ImageView)view.findViewById(R.id.iv_emotion_smile);
        emotion_sad = (ImageView)view.findViewById(R.id.iv_emotion_sad);
        emotion_amazing = (ImageView)view.findViewById(R.id.iv_emotion_amazing);
        emotion_like = (ImageView)view.findViewById(R.id.iv_emotion_like);
        emotion_on.setOnClickListener(this);
        emotion_off.setOnClickListener(this);
        emotion_smile.setOnClickListener(this);
        emotion_sad.setOnClickListener(this);
        emotion_amazing.setOnClickListener(this);
        emotion_like.setOnClickListener(this);



        editText = view.findViewById(R.id.et_comment);
        view.findViewById(R.id.btn_post_comment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if(editText.getText().toString().equals("")==false)
                        getWriteCommentResponse(item.post_img.get(0).getContentIdx(),editText.getText().toString());
                    Log.d("comment",editText.getText().toString());
                    editText.setText("");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        //게시물 수정,삭제
        ImageButton popup = (ImageButton) view.findViewById(R.id.ib_edit_post);
        popup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View popupView = getLayoutInflater().inflate(R.layout.item_editpost_popup, null);
                popupWindow = new PopupWindow(popupView, RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                popupWindow.setFocusable(true);
                popupWindow.showAtLocation(popupView,Gravity.TOP,100,0);
                Button delete_post = (Button) popupView.findViewById(R.id.btn_delete_post);
                delete_post.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                        View popupView2 = getLayoutInflater().inflate(R.layout.item_editpost_delete_popup, null);
                        popupWindow = new PopupWindow(popupView2, ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
                        popupWindow.setFocusable(true);
                        popupWindow.showAtLocation(popupView2,Gravity.CENTER,0,0);
                        Button cancel = (Button) popupView2.findViewById(R.id.btn_cancel);
                        cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) { popupWindow.dismiss(); }
                            });
                        Button delete = (Button) popupView2.findViewById(R.id.btn_delete);
                        delete.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                // 게시물 삭제버튼 클릭시 실행할 내용

                                popupWindow.dismiss();
                                Toast.makeText(getContext(), "삭제되었습니다", Toast.LENGTH_SHORT).show();
                                }});
                        }});
                Button edit_post = (Button) popupView.findViewById(R.id.btn_edit_post);
                edit_post.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                        Intent intent = new Intent(getActivity(), EditPostActivity.class);
                        startActivity(intent);}
                    });
                }
                });

        //포스트 사진 뷰페이저
        viewPager = (ViewPager)view.findViewById(R.id.vp_post_img);
        viewPager.setAdapter(new PagerAdapter(getChildFragmentManager(),getContext(),item.post_img));
        viewPager.setOffscreenPageLimit(5);
        viewPager.addOnPageChangeListener(onPageChangeListener);

        //뷰페이저 인디케이터
        circleAnimIndicator = (CircleAnimIndicator)view.findViewById(R.id.ci_post_vp);
        initIndicator();

        // 댓글 리사이클러뷰
        recyclerView = (RecyclerView)view.findViewById(R.id.rv_comment);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date date=new Date();
        try {
            date = sim.parse(item.posted_time);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ((TextView)view.findViewById(R.id.tv_posted_time)).setText((1900+date.getYear())+"."+(date.getMonth()+1)+"."+date.getDate());

//        ArrayList<CommentItem> commentItemArrayList = new ArrayList<>();
//        commentItemArrayList.add(new CommentItem(R.drawable.mom, "엄마", "재밌었어?", "2019-01-07"));
//        commentItemArrayList.add(new CommentItem(R.drawable.mom, "엄마", "재밌었어?", "2019-01-07"));
//        commentItemArrayList.add(new CommentItem(R.drawable.mom, "엄마", "재밌었어?", "2019-01-07"));
//
//        CommentAdapter commentAdapter = new CommentAdapter(commentItemArrayList);
//        recyclerView.setAdapter(commentAdapter);

        //댓글 받아오기
        post_comment = (TextView)view.findViewById(R.id.btn_post_comment);
        editText =(EditText)view.findViewById(R.id.et_comment);
        editText.getText().toString();
        getCommentListResponse(item.post_img.get(0).getContentIdx());

        if (editText.getText().toString().length()==0){
            // 댓글 공백일 때 처리할 내용
        } else {
            // 댓글 공백 아닐 때 처리할 내용
        }

/*
       ImageView btn_back = (ImageView)view.findViewById(R.id.backArrow);
        ImageView edit_post = (ImageView)view.findViewById(R.id.btn_edit_post);
 */


/*
        edit_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view1.setVisibility(View.GONE);
                view2.setVisibility(View.VISIBLE);
            }
        });
 */
        return view;
    }


    // 감정표현
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_emotion_off:
                emotion_off.setVisibility(View.INVISIBLE);
                emotion_on.setVisibility(View.VISIBLE);
                emotionbar.setVisibility(View.VISIBLE);
                //감정표현 수 ++
                break;
            case R.id.iv_emotion_on:
                emotion_off.setVisibility(View.VISIBLE);
                emotion_on.setVisibility(View.INVISIBLE);
                emotionbar.setVisibility(View.INVISIBLE);
                //감정표현 수 --
                break;
            case R.id.iv_emotion_smile:
                // 스마일 이미지 클릭시 처리할 내용
                // feel type int 0
                try {
                    postFeelResponse(item.post_img.get(0).getContentIdx(),0);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                emotionbar.setVisibility(View.INVISIBLE);
                break;
            case R.id.iv_emotion_sad:
                // 우는 이미지 클릭시 처리할 내용
                // feel type int 1
                try {
                    postFeelResponse(item.post_img.get(0).getContentIdx(),1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                emotionbar.setVisibility(View.INVISIBLE);
                break;
            case R.id.iv_emotion_amazing:
                // 놀라는 이미지 클릭시 처리할 내용
                // feel type int 2
                try {
                    postFeelResponse(item.post_img.get(0).getContentIdx(),2);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                emotionbar.setVisibility(View.INVISIBLE);
                break;
            case R.id.iv_emotion_like:
                // 하트 이미지 클릭시 처리할 내용
                // feel type int 4
                try {
                    postFeelResponse(item.post_img.get(0).getContentIdx(),3);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                emotionbar.setVisibility(View.INVISIBLE);
                break;
        }
    }
    private class PagerAdapter extends FragmentPagerAdapter {
        ArrayList<Fragment> frags =new ArrayList<Fragment>();
        Context context = null;
        ArrayList<Photos>  list;
        public PagerAdapter(FragmentManager fm,Context context,ArrayList<Photos> list) {
            super(fm);
            this.context=context;
            this.list=list;
            Log.d("asd","어뎁터"+list.size());
            for(int i=0;i<list.size();i++)
            {
                Log.d("asd","어뎁터 반복"+list.get(i).getPhotoName());
                frags.add(new PostFirstFragment());
                TodayItem t =  new TodayItem(0,"","asd","asd",null,0,0,"asd","asd","asd","asd");
                ((PostFirstFragment)frags.get(i)).setImageUri(list.get(i).getPhotoName(),fm,t);
            }

        }

        @Override
        public Fragment getItem(int position) {
            // 해당하는 page의 Fragment를 생성합니다.
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
    // 뷰페이저 어댑터
    public class adapter extends FragmentPagerAdapter {
        public adapter(FragmentManager fragmentManager) { super(fragmentManager);  }

        @Override
        public Fragment getItem(int position) {
            if(position<0 || MAX_PAGE<=position)        //가리키는 페이지가 0 이하거나 MAX_PAGE보다 많을 시 null로 리턴
                return null;
            switch (position){              //포지션에 맞는 Fragment찾아서 cur_fragment변수에 대입
                case 0:
                    cur_fragment=new PostFirstFragment();
                    break;

                case 1:
                    cur_fragment=new PostSecondFragment();
                    break;

                case 2:
                    cur_fragment=new PostThirdFragment();
                    break;
            }

            return cur_fragment;
        }

        @Override
        public int getCount() { return MAX_PAGE; }
    }
    private void getCommentListResponse(int contentIdx){
        Call<GetCommentListResponse> getBoardListResponse = ApplicationController.instance.networkService.getCommentListResponse(FamilyData.token, contentIdx);
        getBoardListResponse.enqueue(new Callback<GetCommentListResponse>() {
            @Override
            public void onResponse(Call<GetCommentListResponse> call, Response<GetCommentListResponse> response) {
                if (response.isSuccessful()){
                    Log.d("asd",response.body().toString());
                    if(response.body().getData()==null)
                        return;
                    ArrayList<CommentItem> list = new ArrayList<>();
                    ArrayList<Comments> tmp  = response.body().getData();
                    for(int i=0;i<tmp.size();i++)
                    {
                        list.add(new CommentItem(tmp.get(i).getUserProfile(),
                                tmp.get(i).getUserName(),
                                tmp.get(i).getContent(),
                                tmp.get(i).getCreatedAt()));
                    }
                    recyclerView.setAdapter(new CommentAdapter(list,getContext()));
                }
            }
            @Override
            public void onFailure(Call<GetCommentListResponse> call, Throwable t) {
                // Code...
            }
        });
    }
    private void getWriteCommentResponse(final int contentIdx, String message) throws JSONException {

        JSONObject jsonObject  = new JSONObject();
        jsonObject.put("content", message);
        JsonObject gsonObject = (JsonObject)(new JsonParser().parse(jsonObject.toString()));
        Call<PostWriteCommentResponse> getBoardListResponse = ApplicationController.instance.networkService.postWriteCommentResponse(FamilyData.token, "application/json",contentIdx,gsonObject);
        getBoardListResponse.enqueue(new Callback<PostWriteCommentResponse>() {
            @Override
            public void onResponse(Call<PostWriteCommentResponse> call, Response<PostWriteCommentResponse> response) {
                if (response.isSuccessful()){
                    Log.d("asd",response.body().getMessage());
                    getCommentListResponse(contentIdx);
                    if(response.body().getData()==null)
                        return;

                }
            }
            @Override
            public void onFailure(Call<PostWriteCommentResponse> call, Throwable t) {
                // Code...
            }
        });
    }

    //뷰페이저 인디케이터
    private void initIndicator(){

        //원사이의 간격
        circleAnimIndicator.setItemMargin(10);
        //애니메이션 속도
        circleAnimIndicator.setAnimDuration(300);
        //indicator 생성
        circleAnimIndicator.createDotPanel(item.post_img.size(), R.drawable.indicator_dot_off_squareimg , R.drawable.indicator_dot_on_squareimg);
    }


    //ViewPager 전환시 호출되는 메서드
    private ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

        @Override
        public void onPageSelected(int position) { circleAnimIndicator.selectDot(position); }

        @Override
        public void onPageScrollStateChanged(int state) { }
    };



    /* Backstack으로 뒤로가기
    transaction.addToBackStack(null);

    @Override
    public void onBackKey() {
        MainActivity activity = (MainActivity) getActivity();
        activity.setOnKeyBackPressedListener(null);
        activity.onBackPressed();
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ((MainActivity)context).setOnKeyBackPressedListener(this);
    }*/




 /*   if (getdata() != null) {

        uid = getdata().getString("destinationUid");

        // 본인 계정인 경우 ->  Toolbar 설정
        if (uid != null && uid.equals(currentUserUid)) {
            binding.accountBtnFollowSignout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {   signOut();   }
            });
            activity.setToolbarDefault();
        }

        // 본인 계정이 아닌 경우 -> Toolbar 설정 변경(뒤로 버튼, UserId 표시)
        else {
            binding.accountBtnFollowSignout.setText(getString(R.string.follow));
            binding.accountBtnFollowSignout.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    requestFollow();
                }
            });
            activity.getBinding().toolbarTitleImage.setVisibility(View.GONE);
            activity.getBinding().toolbarBtnBack.setVisibility(View.VISIBLE);
            activity.getBinding().toolbarUsername.setVisibility(View.VISIBLE);
            activity.getBinding().toolbarUsername.setText(getArguments().getString("userId"));
            activity.getBinding().toolbarBtnBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    activity.getBinding().bottomNavigation.setSelectedItemId(R.id.action_home);
                }
            });
        }
    }*/


}