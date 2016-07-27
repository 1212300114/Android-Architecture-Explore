package com.jijunjie.myandroidlib.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Checkable;
import android.widget.ImageView;

/**
 * Created by Administrator on 2016/6/20 0020.
 */
public class CheckableImageView extends ImageView implements Checkable {
    private static final int[] CHECKED_STATE_SET = {android.R.attr.state_checked};

    private boolean checked = false;

    public CheckableImageView(Context context) {
        super(context);
    }

    public CheckableImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CheckableImageView(Context context, AttributeSet attrs, int defStyleAttr) {
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
        }
    }

    @Override
    public int[] onCreateDrawableState(int extraSpace) {
        final int[] drawableState = super.onCreateDrawableState(extraSpace + 1);
        if (isChecked()) {
            mergeDrawableStates(drawableState, CHECKED_STATE_SET);
        }
        return drawableState;

    }

}
