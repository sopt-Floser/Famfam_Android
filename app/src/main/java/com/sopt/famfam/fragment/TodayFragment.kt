package com.sopt.famfam.fragment

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.app.ActivityCompat
import com.sopt.famfam.R
import com.sopt.famfam.activity.AddPostActivity
import com.sopt.famfam.adapter.Permissions
import com.sopt.famfam.adapter.TodayAdapter
import com.sopt.famfam.adapter.item.TodayItem
import com.sopt.famfam.database.FamilyData
import com.sopt.famfam.database.User
import com.sopt.famfam.get.*
import com.sopt.famfam.network.ApplicationController
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception
import java.util.*

class TodayFragment : Fragment() {
    lateinit var fm: FragmentManager
    lateinit var recyclerView: RecyclerView
    lateinit var layoutManager: RecyclerView.LayoutManager

    //권한 갯수 확인
    fun checkPermissionsArray(permissions: Array<String>): Boolean {

        for (i in permissions.indices) {
            val check = permissions[i]
            if (!checkPermissions(check)) {
                return false
            }
        }
        return true
    }

    //권한 단일 요청
    fun checkPermissions(permission: String): Boolean {

        val permissionRequest = ActivityCompat.checkSelfPermission(context!!, permission)

        return if (permissionRequest != PackageManager.PERMISSION_GRANTED) {
            false
        } else {
            true
        }
    }
    companion object {

        private val VERIFY_PERMISSIONS_REQUEST = 1
    }
    //권한 한번에 요청
    fun verifyPermissions(permissions: Array<String>) {

        ActivityCompat.requestPermissions(
            activity as Activity,
            permissions,
            VERIFY_PERMISSIONS_REQUEST
        )
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //프래그먼트 이동
        val AllPhotoFragment = Fragment()
        val rootView = inflater.inflate(R.layout.fragment_today, container, false) as ViewGroup

        //리사이클러뷰안에 뷰페이저

        // 리사이클러뷰
        recyclerView = rootView.findViewById<View>(R.id.rv_today_feed) as RecyclerView
        recyclerView.setHasFixedSize(true)
        layoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = layoutManager

        val todayItemArrayList = ArrayList<TodayItem>()
        //        todayItemArrayList.add(new TodayItem(R.drawable.kim_fam_big, "김팸팸", "2019-01-17", R.drawable.famfeed_1, R.drawable.icon_emoticon, R.drawable.like, "엄마님 외 2명", "엄마랑 데이트", "댓글", "1개"));
        //        todayItemArrayList.add(new TodayItem(R.drawable.kim_fam_big, "김팸팸", "2019-01-17", R.drawable.famfeed_1, R.drawable.icon_emoticon, R.drawable.like, "엄마님 외 2명", "엄마랑 데이트", "댓글", "1개"));
        //        todayItemArrayList.add(new TodayItem(R.drawable.kim_fam_big, "김팸팸", "2019-01-17", R.drawable.famfeed_1, R.drawable.icon_emoticon, R.drawable.like, "엄마님 외 2명", "엄마랑 데이트", "댓글", "1개"));


        val btnAddPost = rootView.findViewById<View>(R.id.btn_today_addPost) as ImageView

        btnAddPost.setOnClickListener {
            if (checkPermissionsArray(com.sopt.famfam.adapter.Permissions.PERMISSIONS)) {
                val intent = Intent(activity, AddPostActivity::class.java)
                startActivity(intent)
            } else {
                verifyPermissions(Permissions.PERMISSIONS)
            }

        }

        // 사진 전체보기 버튼
        val btnAllPhoto = rootView.findViewById<View>(R.id.btn_allPhoto) as ImageView
        btnAllPhoto.setOnClickListener {
            val fragment = AllPhotoFragment()
            val fragmentManager = childFragmentManager
            val transaction = fragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_today1, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }

        getCotentListResponse()
        fm = childFragmentManager
        fragmentManager!!.removeOnBackStackChangedListener{
            getCotentListResponse()
            Log.d("asd","다시매핑")
        }
        fragmentManager!!.addOnBackStackChangedListener{
            getCotentListResponse()
            Log.d("asd","다시매핑")
        }
        return rootView
    }

    override fun onResume() {
        super.onResume()
        getCotentListResponse()
        Log.d("asd","다시매핑")
    }

    override fun onStart() {
        super.onStart()
        getCotentListResponse()
        Log.d("asd","다시매핑")
    }
    private fun getUserDate(idx: Int): User {
        for (i in FamilyData.users.indices) {
            if (FamilyData.users[i].userIdx == idx)
                return FamilyData.users[i]
        }
        return FamilyData.users[0]
    }
    private fun getCotentListResponse() {
        try{
            Log.d("asd",FamilyData.token);
        }catch (e : Exception)
        {
            return
        }

        val getBoardListResponse =
            ApplicationController.instance.networkService.getContentListResponse(FamilyData.token, 0, 30)
        getBoardListResponse.enqueue(object : Callback<GetContentListResponse> {
            override fun onResponse(call: Call<GetContentListResponse>, response: Response<GetContentListResponse>) {
                if (response.isSuccessful) {
                    Log.d("asd", response.body().toString())
                    val conn = response.body()?.data
                    when(conn){
                        null -> return
                    }
                    var con = response.body()!!.data.contents
                    //  int id
                    val todayItemArrayList = ArrayList<TodayItem>()
                    for (i in con.indices) {
                        val userId = con[i].content.userIdx
                        var profile = "test"
                        if(con[i].userProfile == null)
                            profile=""
                        else
                            profile = con[i].userProfile
                        todayItemArrayList.add(
                            TodayItem(
                                con[i].content.userIdx, profile,
                                con[i].userName, con[i].content.createdAt,
                                con[i].photos,
                                R.drawable.icon_emoticon,
                                R.drawable.like,
                                "",
                                con[i].content.content,
                                "댓글",
                                con[i].content.commentCount.toString()+"개"
                            )
                        )

                    }
                    val todayAdapter = TodayAdapter(fm, todayItemArrayList, context)
                    recyclerView.adapter = todayAdapter
                    todayAdapter.notifyDataSetChanged();
                }
            }

            override fun onFailure(call: Call<GetContentListResponse>, t: Throwable) {
                // Code...
            }
        })
    }

    private fun getCommentListResponse(contentIdx: Int) {
        val getBoardListResponse =
            ApplicationController.instance.networkService.getCommentListResponse(FamilyData.token, contentIdx)
        getBoardListResponse.enqueue(object : Callback<GetCommentListResponse> {
            override fun onResponse(call: Call<GetCommentListResponse>, response: Response<GetCommentListResponse>) {
                if (response.isSuccessful) {
                }
            }

            override fun onFailure(call: Call<GetCommentListResponse>, t: Throwable) {
                // Code...
            }
        })
    }
}
//
//package com.sopt.famfam.fragment;
//
//import android.content.Intent;
//import android.net.Uri;
//import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentManager;
//import android.support.v4.view.ViewPager;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import com.sopt.famfam.R;
//import com.sopt.famfam.activity.AddPostActivity;
//import com.sopt.famfam.adapter.TodayAdapter;
//import com.sopt.famfam.adapter.item.TodayItem;
//import com.sopt.famfam.database.FamilyData;
//import com.sopt.famfam.get.*;
//import com.sopt.famfam.network.ApplicationController;
//import gun0912.tedbottompicker.TedBottomPicker;
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//
//import java.util.ArrayList;
//
//public class TodayFragment extends Fragment {
//    FragmentManager fm;
//    RecyclerView recyclerView;
//    RecyclerView.LayoutManager layoutManager;
//
//    @Override
//    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        //프래그먼트 이동
//        Fragment AllPhotoFragment = new Fragment();
//        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_today, container, false);
//
//        //리사이클러뷰안에 뷰페이저
//
//
//        // 리사이클러뷰
//        recyclerView = (RecyclerView) rootView.findViewById(R.id.rv_today_feed);
//        recyclerView.setHasFixedSize(true);
//        layoutManager = new LinearLayoutManager(getActivity());
//        recyclerView.setLayoutManager(layoutManager);
//
//
//        ArrayList<TodayItem> todayItemArrayList = new ArrayList<>();
//        todayItemArrayList.add(new TodayItem(R.drawable.kim_fam_big, "김팸팸", "2019-01-17", R.drawable.famfeed_1, R.drawable.icon_emoticon, R.drawable.like, "엄마님 외 2명", "엄마랑 데이트", "댓글", "1개"));
//        todayItemArrayList.add(new TodayItem(R.drawable.kim_fam_big, "김팸팸", "2019-01-17", R.drawable.famfeed_1, R.drawable.icon_emoticon, R.drawable.like, "엄마님 외 2명", "엄마랑 데이트", "댓글", "1개"));
//        todayItemArrayList.add(new TodayItem(R.drawable.kim_fam_big, "김팸팸", "2019-01-17", R.drawable.famfeed_1, R.drawable.icon_emoticon, R.drawable.like, "엄마님 외 2명", "엄마랑 데이트", "댓글", "1개"));
//
//
//
//
//        ImageView btnAddPost = (ImageView) rootView.findViewById(R.id.btn_today_addPost);
//
//        btnAddPost.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), AddPostActivity.class);
//                startActivity(intent);
//
//            }
//        });
//        // 사진 전체보기 버튼
//        ImageView btnAllPhoto = (ImageView) rootView.findViewById(R.id.btn_allPhoto);
//        btnAllPhoto.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                new Fragment();
//            }
//        });
//        getCotentListResponse();
//        fm = getChildFragmentManager();
//        return rootView;
//    }
//
//    /*
//        @Override
//        public void onCreate(Bundle savedInstanceState) {
//            super.onCreate(savedInstanceState);
//            if (getArguments() != null) {
//                mParam1 = getArguments().getInt(ARG_PARAM1);
//            }
//        }
//        //스크롤했을때 사라진 뷰페이저의 상태를 저장
//        @Override
//        public void onViewRecycled(ViewHolder holder) {
//            mViewPagerState.put(holder.getAdapterPosition(), holder.vp.getCurrentItem());
//            super.onViewRecycled(holder);
//        }
//    */
//    private void getCotentListResponse() {
//        Call<GetContentListResponse> getBoardListResponse = ApplicationController.instance.networkService.getContentListResponse(FamilyData.token, 0, 30);
//        getBoardListResponse.enqueue(new Callback<GetContentListResponse>() {
//            @Override
//            public void onResponse(Call<GetContentListResponse> call, Response<GetContentListResponse> response) {
//                if (response.isSuccessful()) {
//                    Log.d("asd",response.body().toString());
//                    ArrayList<Contents> con = response.body().getData().component2();
//                    //  int id
//                    ArrayList<TodayItem> todayItemArrayList = new ArrayList<>();
//                    for(int i =0;i<con.size();i++)
//                    {
//                        int userId = con.get(i).component3().getUserIdx();
//                        String profile="test";
//                        for(int j=0;j<FamilyData.users.size();j++)
//                        {
//                           if( FamilyData.users.get(i).getUserIdx()==userId)
//                               profile=FamilyData.users.get(i).getProfilePhoto();
//                        }
//                        todayItemArrayList.add(new TodayItem(con.get(i).getContent().getUserIdx(),profile,
//                            con.get(i).getUserName()
//                            ,con.get(i).getContent().getCreatedAt(),
//                            con.get(i).getPhotos(),
//                            R.drawable.icon_emoticon,
//                            R.drawable.like,
//                            "엄마님 외 2명",
//                            con.get(i).getContent().getContent(),
//                            "댓글",
//                            "1개"));
//
//                    }
//                    TodayAdapter todayAdapter = new TodayAdapter(fm,todayItemArrayList, getContext());
//                    recyclerView.setAdapter(todayAdapter);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<GetContentListResponse> call, Throwable t) {
//                // Code...
//            }
//        });
//    }
//    private void getCommentListResponse(int contentIdx){
//        Call<GetCommentListResponse> getBoardListResponse = ApplicationController.instance.networkService.getCommentListResponse(FamilyData.token, contentIdx);
//        getBoardListResponse.enqueue(new Callback<GetCommentListResponse>() {
//            @Override
//            public void onResponse(Call<GetCommentListResponse> call, Response<GetCommentListResponse> response) {
//                if (response.isSuccessful()){
//                }
//            }
//            @Override
//            public void onFailure(Call<GetCommentListResponse> call, Throwable t) {
//                // Code...
//            }
//        });
//    }
//}