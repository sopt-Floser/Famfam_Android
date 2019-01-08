package com.sopt.famfam.adapter.item;

public class CommentItem {
    public int profile;
    public String name;
    public String comment;
    public String comment_time;

    public CommentItem(int profile, String name, String comment, String comment_time) {
        this.profile = profile;
        this.name = name;
        this.comment = comment;
        this.comment_time = comment_time;
    }

}
