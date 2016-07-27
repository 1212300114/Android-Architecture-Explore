package com.jijunjie.myandroidlib.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Checkable;
import android.widget.FrameLayout;

/**
 * Created by Administrator on 2016/4/5 0005.
 */
public class CheckableFrameLayout extends FrameLayout implements Checkable {
    private static final int[] CHECKED_STATE_SET = {android.R.attr.state_checked};

    private boolean checked = false;

    public CheckableFrameLayout(Context context) {
        this(context, null);
    }

    public CheckableFrameLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CheckableFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void toggle() {
        setChecked(!checked);
    }

    @Override
    public boolean isChecked() {
        return checked;
    }

    @Override
    public void setChecked(boolean checked) {
        if (this.checked != checked) {
            this.checked = checked;
            refreshDrawableState();
            for (int i = 0, len = getChildCount(); i < len; i++) {
                View child = getChildAt(i);
                if (child instanceof Checkable) {
                    ((Checkable) child).setChecked(checked);
                }
            }
        }
    }

    @Override
    protected int[] onCreateDrawableState(int extraSpace) {
        final int[] drawableState = super.onCreateDrawableState(extraSpace + 1);
        if (isChecked()) {
            mergeDrawableStates(drawableState, CHECKED_STATE_SET);
        }
        return drawableState;

    }
}
