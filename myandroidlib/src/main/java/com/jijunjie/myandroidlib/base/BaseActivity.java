package com.jijunjie.myandroidlib.base;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Window;

import com.jijunjie.myandroidlib.R;

/**
 * Created by jijunjie on 16/2/25.
 * the base activity of all activities of the application
 * do
 */
public abstract class BaseActivity extends AppCompatActivity {
    private int defaultLauncherId = R.anim.default_push_left_in;
    private int defaultQuitId = R.anim.default_push_right_out;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
        setContentView(toGetContentResId());
        setSupportActionBar(toGetToolBar());
        onActivityCreateCallBack();
    }

    /**
     * Return the content layout id abstract method have to be implement in subclass
     * can't return nothing
     */
    @NonNull
    protected abstract int toGetContentResId();

    /**
     * Callback of onCreate subclass should not override onCreate but can do some additional things
     * in this method;The method have to be implement in subclass;
     */

    protected abstract void onActivityCreateCallBack();

    /**
     * Return the toolbar of activity if needed have to be implement in subclass
     * can't return nothing
     */
    @Nullable
    protected abstract Toolbar toGetToolBar();

    /**
     * To launcher an activity with an default animation which can be customised
     *
     * @param context           the content which wants to start the activity
     * @param targetActivity    the target activity to launcher
     * @param extras            the extra data to transport the target activity
     * @param pushInAnimationId the launcher animation id
     */
    public void launchActivityWithAnimation(@NonNull Context context, @NonNull Class targetActivity, @Nullable Bundle extras, @Nullable int pushInAnimationId) {
        Intent intent = new Intent(context, targetActivity);
        intent.putExtras(extras);
        startActivity(intent);
        if (pushInAnimationId != 0) {
            defaultLauncherId = pushInAnimationId;
        }
        overridePendingTransition(defaultLauncherId, 0);
    }

    /**
     * To quit current activity with an default animation which can be customised
     *
     * @param pushOutAnimationId the quit animation id
     */

    public void finishSelfWithAnimation(@Nullable int pushOutAnimationId) {
        this.finish();
        if (pushOutAnimationId != 0) {
            defaultQuitId = pushOutAnimationId;
        }
        overridePendingTransition(0, defaultQuitId);
    }

}
