package com.oleksandr.havryliuk.tvcontentcontroller.editor;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.oleksandr.havryliuk.tvcontentcontroller.R;
import com.oleksandr.havryliuk.tvcontentcontroller.data.Post;
import com.oleksandr.havryliuk.tvcontentcontroller.editor.all_posts.AllPostsFragment;
import com.oleksandr.havryliuk.tvcontentcontroller.editor.main.MainPostsFragment;
import com.oleksandr.havryliuk.tvcontentcontroller.editor.new_edit_post.edit_post.EditPostFragment;
import com.oleksandr.havryliuk.tvcontentcontroller.editor.new_edit_post.new_post.NewPostFragment;
import com.oleksandr.havryliuk.tvcontentcontroller.utils.ActivityUtils;

public class MainActivity extends FragmentActivity implements View.OnClickListener {
    private final static String TAG = MainActivity.class.getSimpleName();

    private FrameLayout mainMenuLayout, newPostMenuLayout, allPostsMenuLayout;
    private FrameLayout currentMenuLayout;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initMenu();

        checkFilePermissions();
        openMainFragment();
    }

    private void initMenu() {
        mainMenuLayout = findViewById(R.id.main_menu_layout);
        newPostMenuLayout = findViewById(R.id.new_post_menu_layout);
        allPostsMenuLayout = findViewById(R.id.all_posts_menu_layout);

        mainMenuLayout.setOnClickListener(this);
        newPostMenuLayout.setOnClickListener(this);
        allPostsMenuLayout.setOnClickListener(this);

        currentMenuLayout = mainMenuLayout;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void checkFilePermissions() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            int permissionCheck = MainActivity.this.checkSelfPermission("Manifest.permission.READ_EXTERNAL_STORAGE");
            permissionCheck += MainActivity.this.checkSelfPermission("Manifest.permission.WRITE_EXTERNAL_STORAGE");
            if (permissionCheck != 0) {
                this.requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        android.Manifest.permission.READ_EXTERNAL_STORAGE}, 1001); //Any number
            }
        } else {
            Log.d(TAG, "checkBTPermissions: No need to check permissions. SDK version < LOLLIPOP.");
        }
    }

    public void openMainFragment() {
        if (ActivityUtils.isFragmentInBackstack(getSupportFragmentManager(),
                MainPostsFragment.class.getName())) {
            getSupportFragmentManager().popBackStackImmediate(MainPostsFragment.class.getName(), 0);
        } else {
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    new MainPostsFragment(), R.id.fragment, MainPostsFragment.class.getName());
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_menu_layout:
                if (currentMenuLayout != mainMenuLayout) {
                    currentMenuLayout = mainMenuLayout;
                    openMainFragment();
                }
                break;
            case R.id.new_post_menu_layout:
                if (currentMenuLayout != newPostMenuLayout) {
                    currentMenuLayout = newPostMenuLayout;
                    openNewPostFragment();
                }
                break;
            case R.id.all_posts_menu_layout:
                if (currentMenuLayout != allPostsMenuLayout) {
                    currentMenuLayout = allPostsMenuLayout;
                    openAllPostsFragment();
                }
                break;
        }
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

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            super.onBackPressed();
        } else {
            finish();
        }
    }
}
