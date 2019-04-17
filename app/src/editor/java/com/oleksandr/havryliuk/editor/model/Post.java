package com.oleksandr.havryliuk.editor.model;

import android.net.Uri;

import java.util.Date;

public class Post {

    public final static boolean ACTIVE = true;
    public final static boolean NOT_ACTIVE = false;

    public final static String NEWS = "News";
    public final static String AD = "AD";
    public final static String IMAGE = "Image";
    public final static String TEXT = "Text";
    public final static String ALL = "All";

    private String title;
    private String about;
    private Date createDate;
    private String type;
    private Uri imageUri;
    private String text;
    private boolean state;
    private long duration;

    public Post(String title, String about, Date createDate, String type, Uri imageUri, String text, boolean state, long duration) {
        this.title = title;
        this.about = about;
        this.createDate = createDate;
        this.type = type;
        this.imageUri = imageUri;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Uri getImageUri() {
        return imageUri;
    }

    public void setImageUri(Uri imageUri) {
        this.imageUri = imageUri;
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
