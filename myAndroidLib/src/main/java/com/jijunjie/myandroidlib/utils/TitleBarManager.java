package com.jijunjie.myandroidlib.utils;

import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.jijunjie.myandroidlib.R;


/**
 * @author Gary Ji
 * @date 2016/3/21
 * @description  this class is used for handler title bar ,
 * the title bar has three elements left button,title and the right button(for better UI I use text view instead)
 */
public class TitleBarManager {
    private ImageButton btnNavigationLeft;
    private TextView tvTitle, tvNavigationRight;
    private boolean leftEnabled, rightEnabled;
    private String title, rightTitle;
    private View barView;

    /**
     * Instantiates a new View title bar helper.
     *
     * @param barView      the bar view
     * @param leftEnabled  the left enabled
     * @param rightEnabled the right enabled
     */
    public TitleBarManager(View barView, boolean leftEnabled, boolean rightEnabled) {
        this.barView = barView;
        btnNavigationLeft = (ImageButton) barView.findViewById(R.id.btnNavigationLeft);
        tvTitle = (TextView) barView.findViewById(R.id.tvTitle);
        tvNavigationRight = (TextView) barView.findViewById(R.id.tvNavigationRight);
        this.leftEnabled = leftEnabled;
        if (this.leftEnabled) {
            btnNavigationLeft.setVisibility(View.VISIBLE);
        } else {
            btnNavigationLeft.setVisibility(View.GONE);
        }
        this.rightEnabled = rightEnabled;
        if (this.rightEnabled) {
            tvNavigationRight.setVisibility(View.VISIBLE);
        } else {
            tvNavigationRight.setVisibility(View.GONE);
        }
    }

    /**
     * Enable left with callback.
     *
     * @param onClickListener the on click listener set for left button
     */
    public void enableLeftWithCallback(View.OnClickListener onClickListener) {
        this.leftEnabled = true;
        btnNavigationLeft.setVisibility(View.VISIBLE);
        if (onClickListener != null)
            btnNavigationLeft.setOnClickListener(onClickListener);
    }

    /**
     * Disable left.
     */
    public void disableLeft() {
        this.leftEnabled = false;
        btnNavigationLeft.setVisibility(View.GONE);
    }

    /**
     * Enable right with callback.
     *
     * @param onClickListener the on click listener
     * @param rightTitle      the right title
     */
    public void enableRightWithCallback(View.OnClickListener onClickListener, @Nullable String rightTitle) {
        this.rightEnabled = true;
        tvNavigationRight.setVisibility(View.VISIBLE);
        tvNavigationRight.setOnClickListener(onClickListener);
        if (rightTitle != null) {
            tvNavigationRight.setText(rightTitle);
            this.rightTitle = rightTitle;
            if (rightTitle.length() > 4) {
                tvNavigationRight.setTextSize(14);
            }
        } else {
            tvNavigationRight.setText("");
        }
    }

    /**
     * Disable right.
     */
    public void disableRight() {
        this.rightEnabled = false;
        tvNavigationRight.setVisibility(View.GONE);
        this.rightTitle = "";
    }

    /**
     * Sets title.
     *
     * @param title the title
     */
    public void setTitle(@NonNull String title) {
        tvTitle.setText(title);
        this.title = title;
    }

    /**
     * Disable title.
     */
    public void disableTitle() {
        tvTitle.setVisibility(View.GONE);
        this.title = "";
    }

    /**
     * Gets title.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Gets right title.
     *
     * @return the right title
     */
    public String getRightTitle() {
        return rightTitle;
    }

    public void setBarViewBackColor(@ColorRes int colorRes) {
        this.barView.setBackgroundColor(barView.getResources().getColor(colorRes));
    }

    public void setRightIcon(@DrawableRes int rightIcon) {
        this.tvNavigationRight.setBackgroundResource(rightIcon);
    }
}
