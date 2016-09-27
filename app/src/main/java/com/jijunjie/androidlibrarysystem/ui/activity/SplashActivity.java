package com.jijunjie.androidlibrarysystem.ui.activity;

import android.content.Intent;
import android.os.Handler;

import com.jijunjie.androidlibrarysystem.R;
import com.jijunjie.myandroidlib.base.BaseActivity;

/**
 * Created by jijunjie on 16/3/2.
 * the launcher activity with an image
 */
public class SplashActivity extends BaseActivity {
    @Override
    protected void configTitleBar() {

    }

    @Override
    protected void setUpViews() {
        onActivityCreateCallBack();
    }

    @Override
    protected int getRootLayoutRes() {
        return R.layout.activity_splash;
    }

    @Override
    protected void getArgAndConfig() {
        setNeedShowTitle(false);
    }

    protected void onActivityCreateCallBack() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                launchActivity(SplashActivity.this, new Intent(SplashActivity.this  , HomeDrawerActivity.class));
            }
        }, 3000);

    }





}
