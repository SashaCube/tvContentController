package com.oleksandr.havryliuk.tvcontentcontroller.data.source.image_manager;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.oleksandr.havryliuk.tvcontentcontroller.R;
import com.oleksandr.havryliuk.tvcontentcontroller.utils.AppExecutors;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

public class ImageManager {

    private final static String TAG = ImageManager.class.getSimpleName();
    public final static String DIR = "posts_images";

    public static String uploadImage(Uri imageUri) {
        final StorageReference ref = FirebaseStorage.getInstance().getReference()
                .child(UUID.randomUUID().toString());
        UploadTask uploadTask = ref.putFile(imageUri);

        // Register observers to listen for when the download is done or if it fails
        uploadTask.addOnFailureListener(exception -> {
            Log.i(TAG, "failure uploaded " + ref.getPath());
            // Handle unsuccessful uploads
        }).addOnSuccessListener(taskSnapshot -> {
            // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
            Log.i(TAG, "success uploaded " + ref.getPath());
        });

        return ref.getPath();
    }

    public static Bitmap downloadImage(final Context context, final String path,
                                       final ImageView imageView, ProgressBar progressBar) {
        Log.i(TAG, "try to download image from firebase storage: " + path);
        try {
            final File localFile = File.createTempFile("path", ".png");

            final StorageReference ref = FirebaseStorage.getInstance().getReference()
                    .child(path);

            ref.getFile(localFile).addOnSuccessListener(taskSnapshot -> {

                Log.i(TAG, "success downloaded " + path);
                Glide.with(context).load(Uri.fromFile(localFile)).listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_fail_load));
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }
                }).into(imageView);

                Glide.with(context).asBitmap().load(Uri.fromFile(localFile)).into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, Transition<? super Bitmap> transition) {
                        new AppExecutors().diskIO().execute(() -> saveImage(resource, path));
                    }
                });

                Log.i(TAG, "image:" + path + " loaded");

            }).addOnFailureListener(exception -> {

                imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_fail_load));
                progressBar.setVisibility(View.GONE);

                Log.i(TAG, "failure downloaded " + path);
            });

            return BitmapFactory.decodeFile(localFile.getAbsolutePath());

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void loadInto(Context context, String path, ImageView imageView,
                                ProgressBar progressBar) {

        Glide.with(context).load(Uri.fromFile(new File(getAlbumStorageDir(DIR).getPath() + path + ".png"))).
                listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        downloadImage(context, path, imageView, progressBar);

                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        Log.i(TAG, "image:" + path + " loaded");
                        return false;
                    }
                }).into(imageView);
    }

    private static String saveImage(Bitmap image, String path) {
        String savedImagePath = null;

        String imageFileName = path + ".png";
        File storageDir = getAlbumStorageDir(DIR);

        boolean success = true;
        if (!storageDir.exists()) {
            success = storageDir.mkdirs();
        }
        if (success) {
            File imageFile = new File(storageDir, imageFileName);
            savedImagePath = imageFile.getAbsolutePath();
            try {
                OutputStream fOut = new FileOutputStream(imageFile);
                image.compress(Bitmap.CompressFormat.PNG, 100, fOut);
                fOut.close();
                Log.i(TAG, "image:" + imageFile.getAbsolutePath() + " saved");
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return savedImagePath;
    }

    private static File getAlbumStorageDir(String albumName) {
        return new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), albumName);
    }


}

