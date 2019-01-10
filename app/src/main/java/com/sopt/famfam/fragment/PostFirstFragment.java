package com.sopt.famfam.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.sopt.famfam.R;

public class PostFirstFragment extends Fragment {
    String uri;
    FragmentManager fm;
    ImageView image;
    public void setImageUri(String uri, FragmentManager fm)
    {
        this.uri=uri;
        this.fm = fm;
    }

    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_post_first, container, false);
        image = view.findViewById(R.id.iv_post_mainimage);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().replace(R.id.fragment_today1,new PostFragment()).addToBackStack(null).commit();
            }
        });
        Glide.with(this).load(uri).into(image);
        Log.d("asd","photo"+uri);
        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        Glide.with(this).load(uri).into(image);
    }
    @Override
    public void onResume() {
        super.onResume();

        Glide.with(this).load(uri).into(image);
    }

    @Override
    public void onStop() {
        super.onStop();

        Glide.with(this).load(uri).into(image);
    }

    @Override
    public void onStart() {
        super.onStart();
        Glide.with(this).load(uri).into(image);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Glide.with(this).load(uri).into(image);
    }

    public static PostFirstFragment newInstance(String uri) {
        PostFirstFragment fragment = new PostFirstFragment();
        fragment.uri=uri;
        return fragment;
    }
}
