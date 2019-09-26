package com.oleksandr.havryliuk.tvcontentcontroller.client;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.oleksandr.havryliuk.tvcontentcontroller.R;
import com.oleksandr.havryliuk.tvcontentcontroller.client.bottom_bar.BottomBarFragment;
import com.oleksandr.havryliuk.tvcontentcontroller.client.content.ContentFragment;
import com.oleksandr.havryliuk.tvcontentcontroller.utils.ActivityUtils;

public class MainActivity extends AppCompatActivity {

    private final static String TAG = MainActivity.class.getName();

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkFilePermissions();

        showContentFragment();
        showBottomBarFragment();
    }

    private void showContentFragment() {
        if (ActivityUtils.isFragmentInBackstack(getSupportFragmentManager(),
                ContentFragment.class.getName())) {
            getSupportFragmentManager().popBackStackImmediate(ContentFragment.class.getName(), 0);
        } else {
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    new ContentFragment(), R.id.main_fragment, ContentFragment.class.getName());
        }
    }

    private void showBottomBarFragment() {
        if (ActivityUtils.isFragmentInBackstack(getSupportFragmentManager(),
                BottomBarFragment.class.getName())) {
            getSupportFragmentManager().popBackStackImmediate(BottomBarFragment.class.getName(), 0);
        } else {
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    new BottomBarFragment(), R.id.bottom_fragment, BottomBarFragment.class.getName());
        }
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
}
