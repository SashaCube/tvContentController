package com.oleksandr.havryliuk.editor.data.source.image_manager;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.UUID;

import static com.oleksandr.havryliuk.editor.data.source.image_manager.ImageSaver.DIR;

public class ImageManager {

    private final static String TAG = ImageManager.class.getSimpleName();

    public static String uploadImage(Uri imageUri) {
        final StorageReference ref = FirebaseStorage.getInstance().getReference()
                .child(UUID.randomUUID().toString());
        UploadTask uploadTask = ref.putFile(imageUri);

        // Register observers to listen for when the download is done or if it fails
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.i(TAG, "failure uploaded " + ref.getPath());
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                Log.i(TAG, "success uploaded " + ref.getPath());
            }
        });

        return ref.getPath();
    }

    public static Bitmap downloadImage(final Context context, final String path, final ImageView imageView) {
        Log.i(TAG, "try to download image from firebase storage: " + path);
        try {
            final File localFile = File.createTempFile("temp_image", ".png");

            final StorageReference ref = FirebaseStorage.getInstance().getReference()
                    .child(path);

            ref.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Log.i(TAG, "success downloaded " + path);
                    Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                    new ImageSaver(context).setFileName(path + ".png")
                            .save(bitmap);
                    imageView.setImageBitmap(bitmap);
                    Log.i(TAG, "image:" + path + " loaded");
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    Log.i(TAG, "failure downloaded " + path);
                }
            });

            return BitmapFactory.decodeFile(localFile.getAbsolutePath());

        } catch (IOException e) {
            e.printStackTrace();

            // Handle exception
        }

        return null;
    }

    public static Bitmap loadFromLocalStorage(Context context, String path) {
        Log.i(TAG, "try to load image from local storage: " + path);
        Bitmap bitmap = new ImageSaver(context).setFileName(path + ".png").load();

        if (bitmap == null) {
            Log.i(TAG, "image:" + path + " not exist in local storage");
        } else {
            Log.i(TAG, "image:" + path + " exist in local storage");
        }


        return bitmap;
    }

    public static void loadInto(Context context, String path, ImageView imageView) {
        Bitmap bitmap = loadFromLocalStorage(context, path);

        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
            Log.i(TAG, "image:" + path + " loaded");
        } else {
            downloadImage(context, path, imageView);
        }
    }


}

