package com.sopt.famfam.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.sopt.famfam.R;
import com.sopt.famfam.activity.EditPostActivity;
import com.sopt.famfam.adapter.CommentAdapter;
import com.sopt.famfam.adapter.item.CommentItem;
import com.sopt.famfam.indicator.CircleAnimIndicator;

import java.util.ArrayList;

public class PostFragment extends Fragment implements View.OnClickListener {
    ViewPager viewPager;
    int MAX_PAGE=3;
    Fragment cur_fragment = new Fragment();
    CircleAnimIndicator circleAnimIndicator;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    ScrollView scrollView;
    RelativeLayout emotionbar;
    ImageView emotion_on, emotion_off, emotion_smile, emotion_sad, emotion_amazing, emotion_like, edit_post;
    TextView post_comment;
    EditText editText;
    PopupWindow popupWindow;
    int count=0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_post, container, false);
        scrollView = (ScrollView)view.findViewById(R.id.sv_post_body);
        scrollView.smoothScrollTo(0,0);// scroll to top of screen

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
        viewPager.setAdapter(new adapter(getChildFragmentManager()));
        viewPager.addOnPageChangeListener(onPageChangeListener);

        //뷰페이저 인디케이터
        circleAnimIndicator = (CircleAnimIndicator)view.findViewById(R.id.ci_post_vp);
        initIndicator();

        // 댓글 리사이클러뷰
        recyclerView = (RecyclerView)view.findViewById(R.id.rv_comment);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        ArrayList<CommentItem> commentItemArrayList = new ArrayList<>();
        commentItemArrayList.add(new CommentItem(R.drawable.mom, "엄마", "재밌었어?", "2019-01-07"));
        commentItemArrayList.add(new CommentItem(R.drawable.mom, "엄마", "재밌었어?", "2019-01-07"));
        commentItemArrayList.add(new CommentItem(R.drawable.mom, "엄마", "재밌었어?", "2019-01-07"));

        CommentAdapter commentAdapter = new CommentAdapter(commentItemArrayList);
        recyclerView.setAdapter(commentAdapter);

        //댓글 받아오기
        post_comment = (TextView)view.findViewById(R.id.btn_post_comment);
        editText =(EditText)view.findViewById(R.id.et_comment);
        editText.getText().toString();

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
                emotionbar.setVisibility(View.INVISIBLE);
                break;
            case R.id.iv_emotion_sad:
                // 우는 이미지 클릭시 처리할 내용
                // feel type int 1
                emotionbar.setVisibility(View.INVISIBLE);
                break;
            case R.id.iv_emotion_amazing:
                // 놀라는 이미지 클릭시 처리할 내용
                // feel type int 2
                emotionbar.setVisibility(View.INVISIBLE);
                break;
            case R.id.iv_emotion_like:
                // 하트 이미지 클릭시 처리할 내용
                // feel type int 4
                emotionbar.setVisibility(View.INVISIBLE);
                break;
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
//    private void getCommentListResponse(int contentIdx){
//        Call<GetCommentListResponse> getBoardListResponse = ApplicationController.instance.networkService.getCommentListResponse(FamilyData.token, contentIdx);
//        getBoardListResponse.enqueue(new Callback<GetCommentListResponse>() {
//            @Override
//            public void onResponse(Call<GetCommentListResponse> call, Response<GetCommentListResponse> response) {
//                if (response.isSuccessful()){
//
//                }
//            }
//            @Override
//            public void onFailure(Call<GetCommentListResponse> call, Throwable t) {
//                // Code...
//            }
//        });
//    }


    //뷰페이저 인디케이터
    private void initIndicator(){

        //원사이의 간격
        circleAnimIndicator.setItemMargin(10);
        //애니메이션 속도
        circleAnimIndicator.setAnimDuration(300);
        //indicator 생성
        circleAnimIndicator.createDotPanel(MAX_PAGE, R.drawable.indicator_dot_off_squareimg , R.drawable.indicator_dot_on_squareimg);
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