package com.sopt.famfam.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.sopt.famfam.R;

public class PostFirstFragment extends Fragment {
    String uri;
    public void setImageUri(String uri)
    {
        this.uri=uri;
    }
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_post_first, container, false);
        ImageView image = view.findViewById(R.id.iv_post_mainimage);
        Glide.with(this).load(uri).into(image);
        return view;
    }

}
