package com.oleksandr.havryliuk.editor;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.MenuItem;

import com.oleksandr.havryliuk.editor.all_posts.fragments.AllPostsFragment;
import com.oleksandr.havryliuk.editor.data.Post;
import com.oleksandr.havryliuk.editor.main.MainFragment;
import com.oleksandr.havryliuk.editor.new_edit_post.edit_post.EditPostFragment;
import com.oleksandr.havryliuk.editor.new_edit_post.new_post.NewPostFragment;
import com.oleksandr.havryliuk.tvcontentcontroller.R;
import com.oleksandr.havryliuk.tvcontentcontroller.utils.ActivityUtils;

public class MainActivity extends FragmentActivity {
    private final static String TAG = MainActivity.class.getSimpleName();

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_main:
                                openMainFragment();
                                break;
                            case R.id.action_new_post:
                                openNewPostFragment();
                                break;
                            case R.id.action_all_posts:
                                openAllPostsFragment();
                                break;
                        }
                        return true;
                    }
                });

        checkFilePermissions();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void checkFilePermissions() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            int permissionCheck = MainActivity.this.checkSelfPermission("Manifest.permission.READ_EXTERNAL_STORAGE");
            permissionCheck += MainActivity.this.checkSelfPermission("Manifest.permission.WRITE_EXTERNAL_STORAGE");
            if (permissionCheck != 0) {
                this.requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.READ_EXTERNAL_STORAGE}, 1001); //Any number
            }
        } else {
            Log.d(TAG, "checkBTPermissions: No need to check permissions. SDK version < LOLLIPOP.");
        }
    }

    public void openMainFragment() {
        if (ActivityUtils.isFragmentInBackstack(getSupportFragmentManager(),
                MainFragment.class.getName())) {
            getSupportFragmentManager().popBackStackImmediate(MainFragment.class.getName(), 0);
        } else {
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    new MainFragment(), R.id.fragment, MainFragment.class.getName());
        }
    }

    public void openAllPostsFragment() {
        if (ActivityUtils.isFragmentInBackstack(getSupportFragmentManager(),
                AllPostsFragment.class.getName())) {
            getSupportFragmentManager().popBackStackImmediate(AllPostsFragment.class.getName(), 0);
            AllPostsFragment fragment = (AllPostsFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
            if (fragment != null) {
//                fragment.update();
            }
        } else {
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    new AllPostsFragment(), R.id.fragment, AllPostsFragment.class.getName());
        }
    }

    public void openNewPostFragment() {
        if (ActivityUtils.isFragmentInBackstack(getSupportFragmentManager(),
                NewPostFragment.class.getName())) {
            getSupportFragmentManager().popBackStackImmediate(NewPostFragment.class.getName(), 0);
        } else {
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    new NewPostFragment(), R.id.fragment, NewPostFragment.class.getName());
        }
    }

    public void openEditPostFragment(Post post) {
        EditPostFragment fragment = new EditPostFragment();
        fragment.init(post);
        ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                fragment, R.id.fragment, EditPostFragment.class.getName());

    }

    public interface IImagePicker {
        void setUri(Uri uri);

        void setPath(String path);
    }


    private final static int GALLERY_REQUEST_CODE = 1;
    private IImagePicker imagePicker;


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri selectedImage;

        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case GALLERY_REQUEST_CODE:
                    selectedImage = data.getData();
                    imagePicker.setUri(selectedImage);
                    break;
            }
        }
    }

    public void pickImageFromGallery(IImagePicker imagePicker) {
        this.imagePicker = imagePicker;
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        String[] mimeTypes = {"image/jpeg", "image/png"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
        startActivityForResult(intent, GALLERY_REQUEST_CODE);
    }
}
