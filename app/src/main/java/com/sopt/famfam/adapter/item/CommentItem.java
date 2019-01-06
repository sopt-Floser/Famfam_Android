package com.sopt.famfam.adapter.item;

public class CommentItem {
    String profile;
    String name;
    String comment;
    String comment_time;

    public CommentItem(String profile, String name, String comment, String comment_time) {
        this.profile = profile;
        this.name = name;
        this.comment = comment;
        this.comment_time = comment_time;
    }
}
