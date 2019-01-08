package com.sopt.famfam.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.sopt.famfam.R;
import com.sopt.famfam.adapter.CommentAdapter;
import com.sopt.famfam.adapter.item.CommentItem;
import com.sopt.famfam.indicator.CircleAnimIndicator;

import java.util.ArrayList;
import java.util.List;

public class PostFragment extends Fragment {
    ViewPager viewPager;
    private List<String> numberList;
    private CircleAnimIndicator circleAnimIndicator;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RelativeLayout btn_comment;
    ImageView btn_emotion;
    int MAX_PAGE=3;
    Fragment cur_fragment = new Fragment();

    /*
    private Button edit_post;
    private Button emotion;
    */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_post, container, false);
        btn_comment = (RelativeLayout)view.findViewById(R.id.btn_open_comment);

        //포스트 사진 뷰페이저
        viewPager = (ViewPager)view.findViewById(R.id.vp_post_img);
        viewPager.setAdapter(new adapter(getChildFragmentManager()));


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

/*
        ImageView btn_back = (ImageView)view.findViewById(R.id.backArrow);
        ImageView edit_post = (ImageView)view.findViewById(R.id.btn_edit_post);
        ImageView emotion = (ImageView)view.findViewById(R.id.iv_emotion_off);
 */

        btn_comment.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                recyclerView.setVisibility(View.VISIBLE);
            }
        });


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



    //뷰페이저 인디케이터


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