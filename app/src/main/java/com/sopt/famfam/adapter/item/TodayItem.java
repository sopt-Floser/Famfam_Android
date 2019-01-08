package com.sopt.famfam.adapter.item;

public class TodayItem {
    public int profile;
    public String name;
    public String posted_time;
    public int post_img;
    public int emotion;
    public int feel;
    public String img_likes;
    public String caption;
    public String comment;
    public String comment_count;

    public TodayItem(int profile, String name, String posted_time, int post_img, int emotion, int feel, String img_likes, String caption, String comment, String comment_count) {
        this.profile = profile;
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
