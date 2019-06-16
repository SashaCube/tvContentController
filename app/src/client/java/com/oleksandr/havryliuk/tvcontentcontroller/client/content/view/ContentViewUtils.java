package com.oleksandr.havryliuk.tvcontentcontroller.client.content.view;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.widget.ImageView;

import com.oleksandr.havryliuk.tvcontentcontroller.client.data.local.room.MyWeather;
import com.oleksandr.havryliuk.tvcontentcontroller.client.data.remote.api.APIInterface;
import com.oleksandr.havryliuk.tvcontentcontroller.data.Post;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.oleksandr.havryliuk.tvcontentcontroller.client.utils.Utils.getUpToDateWeather;
import static com.oleksandr.havryliuk.tvcontentcontroller.data.Post.AD;

public class ContentViewUtils {

    public static void animation(ImageView view) {
        ObjectAnimator scaleDownX = ObjectAnimator.ofFloat(view, "scaleX", 0.5f);
        ObjectAnimator scaleDownY = ObjectAnimator.ofFloat(view, "scaleY", 0.5f);
        scaleDownX.setDuration(1000);
        scaleDownY.setDuration(1000);

        ObjectAnimator scaleUpX = ObjectAnimator.ofFloat(view, "scaleX", 0.7f);
        ObjectAnimator scaleUpY = ObjectAnimator.ofFloat(view, "scaleY", 0.7f);
        scaleUpX.setDuration(1000);
        scaleUpY.setDuration(1000);

        AnimatorSet scale = new AnimatorSet();
        scale.play(scaleDownX).with(scaleDownY).before(scaleUpX).before(scaleUpY);

        scale.start();
    }

    public static String getIconUrl(String iconId) {
        return APIInterface.BASE_URL + "img/w/" + iconId + ".png";
    }

    public static List<Post> getActivePostsWithoutAD(List<Post> allPosts) {

        if(allPosts == null){
            return null;
        }

        ArrayList<Post> activePosts = new ArrayList<>();

        for (Post p : allPosts) {
            if (p.isState()) {
                if (!(p.getType().equals(AD))) {
                    activePosts.add(p);
                }
            }
        }

        return activePosts;
    }

    public static List<Post> getActivePosts(List<Post> allPosts) {

        if(allPosts == null){
            return null;
        }

        ArrayList<Post> activePosts = new ArrayList<>();

        for (Post p : allPosts) {
            if (p.isState()) {

            }
        }

        return activePosts;
    }

    public static List<MyWeather> getFutureWeather(List<MyWeather> weatherList) {
        ArrayList<MyWeather> futureWeather = new ArrayList<>();
        Date date = new Date();

        for (int i = 0; i < 3; i++) {
            date = new Date(date.getTime() + 24 * 60 * 60 * 1000);
            date.setHours(12);
            futureWeather.add(getUpToDateWeather(weatherList, date));
        }

        return futureWeather;
    }
}
