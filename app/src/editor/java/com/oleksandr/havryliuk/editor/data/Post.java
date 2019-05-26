package com.oleksandr.havryliuk.editor.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.Objects;
import java.util.UUID;

@Entity(tableName = "posts")
public class Post {

    public final static boolean ACTIVE = true;
    public final static boolean NOT_ACTIVE = false;

    public final static String NEWS = "News";
    public final static String AD = "AD";
    public final static String IMAGE = "Image";
    public final static String TEXT = "Text";
    public final static String ALL = "All";

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "post_id")
    private String id;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "about")
    private String about;

    @ColumnInfo(name = "create_date")
    private String createDate;

    @ColumnInfo(name = "type")
    private String type;

    @ColumnInfo(name = "image_path")
    private String imagePath;

    @ColumnInfo(name = "text")
    private String text;

    @ColumnInfo(name = "state")
    private boolean state;

    @ColumnInfo(name = "duration")
    private long duration;

    public Post(String title, String about, String createDate, String type, String imagePath, String text, boolean state, long duration) {
        this.id = UUID.randomUUID().toString();
        this.title = title;
        this.about = about;
        this.createDate = createDate;
        this.type = type;
        this.imagePath = imagePath;
        this.text = text;
        this.state = state;
        this.duration = duration;
    }

    @Ignore
    public Post(String id, String title, String about, String createDate, String type, String imagePath, String text, boolean state, long duration) {
        this.id = id;
        this.title = title;
        this.about = about;
        this.createDate = createDate;
        this.type = type;
        this.imagePath = imagePath;
        this.text = text;
        this.state = state;
        this.duration = duration;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
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

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post task = (Post) o;
        return Objects.equals(id, task.id) &&
                Objects.equals(title, task.title);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Post with title " + title;
    }
}
