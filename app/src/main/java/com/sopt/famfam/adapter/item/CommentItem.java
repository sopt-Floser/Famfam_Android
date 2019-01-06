package com.sopt.famfam.adapter.item;

public class CommentItem {
    String profile;
    String name;
    String comment;
    String comment_time;

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getComment_time() {
        return comment_time;
    }

    public void setComment_time(String comment_time) {
        this.comment_time = comment_time;
    }

    public CommentItem(String profile, String name, String comment, String comment_time) {
        this.profile = profile;
        this.name = name;
        this.comment = comment;
        this.comment_time = comment_time;

    }
}
