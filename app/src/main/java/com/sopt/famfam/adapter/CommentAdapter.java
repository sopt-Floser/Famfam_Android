package com.sopt.famfam.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.sopt.famfam.R;
import com.sopt.famfam.adapter.item.CommentItem;

import java.util.ArrayList;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {

    private ArrayList<CommentItem> items = new ArrayList<>();
    //View.OnClickListener clickListener;

    @NonNull
    @Override
    public CommentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);

        //클릭이벤트 추가 view.setOnClickListener(clickListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CommentAdapter.ViewHolder viewHolder, int position) {

       CommentItem item = items.get(position);

/*   URL 받아오기

        Glide.with(viewHolder.itemView.getContext())
                .load(item.get())
                .into(viewHolder.ivMovie);


        viewHolder.tvTitle.setText(item.getTitle());
        viewHolder.tvContent.setText(item.getContent());
        viewHolder.tvGenre.setText(item.getGenre());
        viewHolder.tvGenre.setText(item.getGenre());

    }


    public void setItems(ArrayList<CommentItem> items) {
        this.items = items;
    }

 */}
    @Override
    public int getItemCount() {
        return items.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivProfile;
        TextView tvName, tvComment, tvCommenttime;

        public ViewHolder(View itemView) {
            super(itemView);

            ivProfile = itemView.findViewById(R.id.iv_comment_profile);
            tvName = itemView.findViewById(R.id.tv_comment_username);
            tvComment = itemView.findViewById(R.id.tv_comment);
            tvCommenttime = itemView.findViewById(R.id.tv_comment_time);
        }
    }
}

