package com.jijunjie.androidlibrarysystem.ui.activity;

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

    }

    @Override
    protected int getRootLayoutRes() {
        return R.layout.activity_splash;
    }

    @Override
    protected void getArgAndConfig() {

    }

//    @Override
//    protected void onActivityCreateCallBack() {
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                finishSelfWithAnimation(0);
//                launchActivityWithAnimation(SplashActivity.this, HomeDrawerActivity.class, null, 0);
//            }
//        }, 3000);
//
//    }
//
//    @Nullable
//    @Override
//    protected Toolbar toGetToolBar() {
//        return null;
//    }
//
//
//    @Override
//    public void onBackPressed() {
//    }
}
