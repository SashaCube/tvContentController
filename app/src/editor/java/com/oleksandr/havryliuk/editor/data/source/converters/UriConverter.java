package com.oleksandr.havryliuk.editor.data.source.converters;

import android.arch.persistence.room.TypeConverter;
import android.net.Uri;

public class UriConverter {

    @TypeConverter
    public static Uri toUri(String str) {
        if(str == null) {
            return null;
        }


        Uri.Builder builder = new Uri.Builder();
        return builder.appendPath(str).build();
    }

    @TypeConverter
    public static String toString(Uri uri) {
        return uri == null ? null : uri.toString();
    }
}
