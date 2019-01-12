package com.sopt.famfam.adapter.item;

import com.sopt.famfam.get.Photos;
import com.sopt.famfam.get.Types;

import java.util.ArrayList;

public class TodayItem {
    public int useridx;
    public String profile;
    public String name;
    public String posted_time;
    public ArrayList<Photos> post_img;
    public int emotion;
    public int feel;
    public String img_likes;
    public String caption;
    public String comment;
    public String comment_count;
    public Types types;

    public TodayItem(int useridx, String profile, String name, String posted_time, ArrayList<Photos>  post_img, int emotion, int feel, String img_likes, String caption, String comment, String comment_count) {
        this.profile = profile;
        this.useridx=useridx;
        this.name = name;
        this.posted_time = posted_time;
        this.post_img = post_img;
        this.emotion = emotion;
        this.feel = feel;
        this.img_likes = img_likes;
        this.caption = caption;
        this.comment = comment;
        this.comment_count = comment_count;
    }
}
