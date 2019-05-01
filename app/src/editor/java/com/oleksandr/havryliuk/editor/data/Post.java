package com.oleksandr.havryliuk.editor.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.oleksandr.havryliuk.editor.data.source.converters.DateConverter;
import com.oleksandr.havryliuk.editor.data.source.converters.UriConverter;

import java.util.Date;
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

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "post_id")
    private String id;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "about")
    private String about;

    @TypeConverters(DateConverter.class)
    @ColumnInfo(name = "create_date")
    private Date createDate;

    @ColumnInfo(name = "type")
    private String type;

    @TypeConverters(UriConverter.class)
    @ColumnInfo(name = "image_uri")
    private Uri imageUri;

    @ColumnInfo(name = "text")
    private String text;

    @ColumnInfo(name = "state")
    private boolean state;

    @ColumnInfo(name = "duration")
    private long duration;

    public Post(String title, String about, Date createDate, String type, Uri imageUri, String text, boolean state, long duration) {
        this.id = UUID.randomUUID().toString();
        this.title = title;
        this.about = about;
        this.createDate = createDate;
        this.type = type;
        this.imageUri = imageUri;
        this.text = text;
        this.state = state;
        this.duration = duration;
    }

    @Ignore
    public Post(String id, String title, String about, Date createDate, String type, Uri imageUri, String text, boolean state, long duration) {
        this.id = id;
        this.title = title;
        this.about = about;
        this.createDate = createDate;
        this.type = type;
        this.imageUri = imageUri;
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
