package com.jijunjie.myandroidlib.base;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.AnimRes;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;

import com.jijunjie.myandroidlib.R;
import com.jijunjie.myandroidlib.utils.TitleBarManager;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.umeng.analytics.MobclickAgent;
import com.zhy.http.okhttp.OkHttpUtils;

import butterknife.ButterKnife;

/**
 * @author Gary Ji
 * @description the base class of the application all activities should inherit this class
 * @date 2016/6/6
 */

public abstract class BaseActivity extends AppCompatActivity {

    // the tint color resource
    @ColorRes
    private int tintColorRes = R.color.appThemeColorTitle;
    // whether to show title bar
    private boolean needShowTitle = false;
    // the title bar helper class
    protected TitleBarManager helper;

    public void setNeedShowTitle(boolean needShowTitle) {
        this.needShowTitle = needShowTitle;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //进行替换
        getArgAndConfig();
        setContentView(getRootLayoutRes());
        ButterKnife.bind(this);
        setUpTitleBar();
        setTintBar(0);
        if (needShowTitle)
            configTitleBar();
        setUpViews();
        onCreateFinish();
        BaseApplication.addActivity(this);
    }

    /**
     * if you want to do some extra things after all other things done   do it here
     */
    protected void onCreateFinish() {
    }

    // all umeng statistics init in base
    @Override
    protected void onResume() {
        MobclickAgent.onResume(this);
        super.onResume();
    }

    @Override
    protected void onPause() {
        MobclickAgent.onPause(this);
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkHttpUtils.getInstance().cancelTag(this);
    }

    //all back events are over written with finish and common animation
    @Override
    public void onBackPressed() {
        finishActivity(this);
    }

    // config tint color
    protected void setTintColor(@ColorRes int colorRes) {
        if (colorRes != 0)
            this.tintColorRes = colorRes;
    }

    /**
     * to set status bar color
     *
     * @param colorRes the bar color res
     */

    protected void setTintBar(@ColorRes int colorRes) {
        setTintColor(colorRes);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        // enable status bar tint
        tintManager.setStatusBarTintEnabled(true);
        // enable navigation bar tint
        tintManager.setNavigationBarTintEnabled(false);
        // set a custom tint color for all system bars
        tintManager.setTintColor(getResources().getColor(tintColorRes));
    }

    /**
     * set up the title bar
     */
    private void setUpTitleBar() {
        if (needShowTitle) {
            View homeBar = findViewById(R.id.homeBar);
            helper = new TitleBarManager(homeBar, true, false);
            enableLeftWithCallback(null);
        }
    }

    /**
     * disable left and hide it
     */
    protected void disableLeft() {
        helper.disableLeft();
    }

    /**
     * to setup left Click
     */
    protected void enableLeftWithCallback(View.OnClickListener l) {
        if (helper == null)
            return;
        if (l != null) {
            helper.enableLeftWithCallback(l);
            return;
        }
        helper.enableLeftWithCallback(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseActivity.this.onBackPressed();
            }
        });
    }

    /**
     * to setup right click
     */

    protected void enableRightWithCallback(View.OnClickListener l, String rightTitle) {
        if (helper == null)
            return;
        helper.enableRightWithCallback(l, rightTitle);
    }

    /**
     * to set the right button icon
     *
     * @param rightIcon the right button resource
     */

    protected void setRightIcon(@DrawableRes int rightIcon) {
        if (helper == null)
            return;
        if (rightIcon != 0) {
            helper.setRightIcon(rightIcon);
        }
    }

    /**
     * set the title of the title bar
     */
    protected void setTitle(String title) {
        if (helper == null)
            return;
        helper.setTitle(title);
    }


    /**
     * set the background color of the title bar ;
     */
    protected void setTitleBackground(@ColorRes int colorRes) {
        helper.setBarViewBackColor(colorRes);
    }

    protected abstract void configTitleBar();

    /**
     * set up activity views
     */
    protected abstract void setUpViews();

    /**
     * @return the root layout resource
     */
    protected abstract int getRootLayoutRes();

    /**
     * config and get arguments of the activity during onCreate ,this method is call first in onCreate;
     * <p/>
     * property must be config is need show title
     */
    protected abstract void getArgAndConfig();


    /**
     * finish activity with animation slide out to right
     *
     * @param activity the activity to finish
     */
    public static void finishActivity(Activity activity) {
        finishActivity(activity, 0);
    }

    /**
     * finish activity from the fragment
     *
     * @param fragment the fragment call finish
     */
    public static void finishActivity(Fragment fragment) {
        finishActivity(fragment, 0);
    }

    /**
     * fragment call finish activity with certain animation
     *
     * @param fragment the fragment call finish
     * @param quitAnim the quit animation resource id
     */
    public static void finishActivity(Fragment fragment, @AnimRes int quitAnim) {
        finishActivity(fragment.getActivity(), quitAnim);
    }

    /**
     * call finish activity with certain animation
     *
     * @param activity the activity call finish
     * @param quitAnim the quit animation resource id
     */
    public static void finishActivity(Activity activity, @AnimRes int quitAnim) {
        activity.finish();
        if (quitAnim != 0) activity.overridePendingTransition(0, quitAnim);
        else activity.overridePendingTransition(0, R.anim.default_quit_anim);
    }

    /**
     * launcher an activity
     */
    public static void launchActivity(Activity activity, Intent intent) {
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.default_start_left_in_anim, R.anim.default_start_right_out_anim);
    }

    public static void launchActivity(Fragment fragment, Intent intent) {
        fragment.startActivity(intent);
        fragment.getActivity().overridePendingTransition(R.anim.default_start_left_in_anim, R.anim.default_start_right_out_anim);
    }

    public static void launchActivity(Context activity, Intent intent) {
        activity.startActivity(intent);
        ((Activity) activity).overridePendingTransition(R.anim.default_start_left_in_anim, R.anim.default_start_right_out_anim);
    }

}
