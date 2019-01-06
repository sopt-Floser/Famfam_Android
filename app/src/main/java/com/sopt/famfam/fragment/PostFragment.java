package com.sopt.famfam.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.*;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.sopt.famfam.R;
import com.sopt.famfam.activity.CommentActivity;

import java.util.ArrayList;
import java.util.List;

public class PostFragment extends Fragment {
    ViewPager viewPager;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_post, container, false);
        viewPager = (ViewPager) view.findViewById(R.id.vp_post_img);



        ImageView btn_back = (ImageView)view.findViewById(R.id.backArrow);

        RelativeLayout btnViewComment = (RelativeLayout) view.findViewById(R.id.btn_open_comment);
        btnViewComment.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CommentActivity.class);
                startActivity(intent);            }
        });


        return view;
    }
/*
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.)
    }*/


    @Override
    public void onActivityCreated( Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());

        viewPagerAdapter.addFragment(new PostFirstFragment(), "First");
        viewPagerAdapter.addFragment(new PostSecondFragment(), "Second");
        viewPagerAdapter.addFragment(new PostThirdFragment(), "Login");

        viewPager.setAdapter(viewPagerAdapter);
    }

    private class ViewPagerAdapter extends FragmentPagerAdapter {

        List<Fragment> fragmentList = new ArrayList<>();
        List<String> fragmentTitles = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitles.get(position);
        }

        public void addFragment(Fragment fragment, String name) {
            fragmentList.add(fragment);
            fragmentTitles.add(name);
        }
    }

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

        // 본인 계정이 아닌 경우 -> 팔로우, Toolbar 설정 변경(뒤로 버튼, UserId 표시)
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