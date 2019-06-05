package com.oleksandr.havryliuk.tvcontentcontroller.utils;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ActivityUtils {
    public static void addFragmentToActivity(@NonNull FragmentManager fragmentManager,
                                             @NonNull Fragment fragment, int frameId, String tag) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(frameId, fragment);
        transaction.addToBackStack(tag);
        transaction.commit();
    }

    public static boolean isFragmentInBackstack(final FragmentManager fragmentManager,
                                                final String fragmentTagName) {
        for (int entry = 0; entry < fragmentManager.getBackStackEntryCount(); entry++) {
            if (fragmentTagName.equals(fragmentManager.getBackStackEntryAt(entry).getName())) {
                return true;
            }
        }
        return false;
    }

    public static String dateTimeConverter(Date date) {
        DateFormat format = new SimpleDateFormat(" yyyy:MM.dd HH:mm", Locale.ENGLISH);
        return format.format(date);
    }

    public static String UriPath(Uri uri) {
        if (uri == null) {
            return null;
        }
        return uri.getPath();
    }
}
