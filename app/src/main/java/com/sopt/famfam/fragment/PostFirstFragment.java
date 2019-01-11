package com.sopt.famfam.fragment;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.sopt.famfam.R;
import com.sopt.famfam.adapter.item.TodayItem;

public class PostFirstFragment extends Fragment {
    String uri;
    TodayItem item;
    FragmentManager fm;
    ImageView image=null;
    RequestOptions options = new RequestOptions();
    PostFragment frag = new PostFragment();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d("asdoncreat",item.name);
    }

    public void setImageUri(String uri, FragmentManager fm, TodayItem item)
    {
        this.uri=uri;
        this.fm = fm;
        this.item=item;
        frag.setItem(item);
        if(image!=null)
            Glide.with(this).load(uri).apply(options).into(image);
        Log.d("asdpf",item.name);
    }

    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_post_first, container, false);
        image = view.findViewById(R.id.iv_post_mainimage);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().replace(R.id.fragment_today1,frag).addToBackStack(null).commit();
            }
        });
        Glide.with(this).load(uri).apply(options).into(image);
        Log.d("asd","photo"+uri);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        options.diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true);
        image = view.findViewById(R.id.iv_post_mainimage);

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().replace(R.id.fragment_today1,frag).addToBackStack(null).commit();
            }
        });
        Glide.with(this).load(uri).apply(options).into(image);
        Log.d("asdviewcreat","aaa"+uri);
    }

    public static PostFirstFragment newInstance(String uri, TodayItem item) {
        PostFirstFragment fragment = new PostFirstFragment();
        fragment.uri=uri;
        fragment.item=item;
        return fragment;
    }
}
