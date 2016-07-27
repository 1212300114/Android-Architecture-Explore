package com.jijunjie.myandroidlib.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.jijunjie.myandroidlib.R;
import com.jijunjie.myandroidlib.utils.LogUtils;
import com.jijunjie.myandroidlib.utils.ScreenUtils;

/**
 * Created by Administrator on 2016/6/24 0024.
 */
public class CustomSeekBar extends FrameLayout {
    private ProgressBar bar;
    private int progress = 0;
    private ImageView ivThumb;
    private int progressSize = 0;

    public CustomSeekBar(Context context) {
        this(context, null);
    }

    public CustomSeekBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomSeekBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews();
    }

    private void initViews() {
        progressSize = (ScreenUtils.getScreenWidth(getContext()) / 2) - ScreenUtils.dp2px(getContext(), 28);
        this.setClipChildren(false);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.seek_layout, this, false);
        bar = (ProgressBar) view.findViewById(R.id.progressBar);
        ivThumb = (ImageView) view.findViewById(R.id.ivThumb);
        this.addView(view);
    }

    public void setProgress(int progress) {
        // to make the content full of the screen

        if (progress > 98)
            progress = 98;
        this.progress = progress;
        bar.setProgress(progress);
        ivThumb.setX(progressSize * progress / 100);
        requestLayout();
        ivThumb.requestLayout();
        LogUtils.e("progress = " + progress + "size = " + progressSize);
    }

    public void setProgressSize(int progressSize) {
        this.progressSize = progressSize;
    }
}
