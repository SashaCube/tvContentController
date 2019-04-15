package com.oleksandr.havryliuk.editor.model;

import java.util.Date;

public class Post {

    public static boolean ACTIVE = true;
    public static boolean NOT_ACTIVE = false;

    private String title;
    private String about;
    private Date createDate;
    private Type type;
    private String imageURL;
    private String text;
    private boolean state;
    private long duration;

    public Post(String title, String about, Date createDate, Type type, String imageURL, String text, boolean state, long duration) {
        this.title = title;
        this.about = about;
        this.createDate = createDate;
        this.type = type;
        this.imageURL = imageURL;
        this.text = text;
        this.state = state;
        this.duration = duration;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }
}
