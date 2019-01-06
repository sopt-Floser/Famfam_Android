package com.sopt.famfam.adapter.item;

public class TodayItem {

    String profile;
    String name;
    String posted_time;
    String post_img;
    int emotion;
    int feel;
    String img_likes;
    String caption;
    int comment_count;

    public TodayItem(String profile, String name, String posted_time, String post_img, int emotion, int feel, String img_likes, String caption, int comment_count) {
        this.profile = profile;
        this.name = name;
        this.posted_time = posted_time;
        this.post_img = post_img;
        this.emotion = emotion;
        this.feel = feel;
        this.img_likes = img_likes;
        this.caption = caption;
        this.comment_count = comment_count;
    }
}
