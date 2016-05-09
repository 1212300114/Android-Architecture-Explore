package com.jijunjie.androidlibrarysystem.ui.activity;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;

import com.jijunjie.androidlibrarysystem.R;
import com.jijunjie.myandroidlib.base.BaseActivity;

/**
 * Created by jijunjie on 16/3/2.
 * the launcher activity with an image
 */
public class SplashActivity extends BaseActivity {
    @NonNull
    @Override
    protected int toGetContentResId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void onActivityCreateCallBack() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                launchActivityWithAnimation(SplashActivity.this, HomeDrawerActivity.class, null, 0);
            }
        }, 3000);

    }

    @Nullable
    @Override
    protected Toolbar toGetToolBar() {
        return null;
    }


    @Override
    public void onBackPressed() {
    }
}
