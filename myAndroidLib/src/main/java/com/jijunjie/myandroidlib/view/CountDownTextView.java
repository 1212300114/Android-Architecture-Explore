package com.jijunjie.myandroidlib.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author Gary Ji
 * @description the auto count down text view
 * @date 2016/5/5 0005.
 */
public class CountDownTextView extends TextView {

    private static volatile String NOTICE_STRING = "请稍后";
    private volatile Timer timer = new Timer();
    private volatile long currentMillions = 3000;
    private TimerTask timerTask;


    private volatile boolean running = false;

    public boolean isRunning() {
        return running;
    }

    public CountDownTextView(Context context, AttributeSet attrs) {

        super(context, attrs);
    }

    public CountDownTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CountDownTextView(Context context) {
        super(context);
    }

    public void setCountDown(long currentMillions) {
        this.currentMillions = currentMillions;
        running = true;
        resetTimer();

        getTimer().schedule(timerTask, 0, 10);
    }


    public void stopCountDown() {
        running = false;
        setText(NOTICE_STRING);
        if (timerTask != null)
            timerTask.cancel();
        timerTask = null;
    }

    public void cancelTimer() {
        running = false;
        timer.cancel();
    }

    private void resetTimer() {
        timer = new Timer();
        timerTask = new MyTask(this);
    }

    private Timer getTimer() {
        if (timer == null)
            timer = new Timer();
        return timer;
    }

    public void setCurrentMillions(long currentMillions) {
        this.currentMillions = currentMillions;
        setText(createTimeString(CountDownTextView.this.currentMillions));
    }

    public static void setNoticeString(String noticeString) {
        NOTICE_STRING = noticeString;
    }

    private synchronized String createTimeString(long ms) {
        int mHour = (int) ((ms % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
        int mMinute = (int) ((ms % (1000 * 60 * 60)) / (1000 * 60));
        int mSecond = (int) ((ms % (1000 * 60)) / 1000);
        int mMillisecond = (int) (ms % 1000);
        String hour, minute, second, millisecond;
        if (mHour < 12) {
            hour = "0" + mHour;
        } else {
            hour = "" + mHour;
        }
        if (mMinute < 10) {
            minute = "0" + mMinute;
        } else {
            minute = "" + mMinute;
        }
        if (mSecond < 10) {
            second = "0" + mSecond;
        } else {
            second = "" + mSecond;
        }
        if (mMillisecond < 100) {
            millisecond = "0" + mMillisecond / 10;
        } else {
            millisecond = mMillisecond / 10 + "";
        }
        return minute + ":" + second + ":" + millisecond;
    }

    /**
     * user weak reference to avoid memory leak
     */

    private static class MyTask extends TimerTask {
        private WeakReference<CountDownTextView> weak;

        public MyTask(CountDownTextView reference) {
            this.weak = new WeakReference<>(reference);
        }

        @Override
        public void run() {
            if (weak.get() != null) {
                weak.get().currentMillions -= 10;
                if (weak.get().currentMillions <= 0) {
                    weak.get().running = false;
                    weak.get().post(new Runnable() {
                        @Override
                        public void run() {
                            if (weak.get() != null)
                                weak.get().setText(NOTICE_STRING);
                        }
                    });
                    this.cancel();
                }
                weak.get().post(new Runnable() {
                    @Override
                    public void run() {
                        if (weak.get() != null)
                            weak.get().setText(weak.get().createTimeString(weak.get().currentMillions));
                    }
                });
            }
        }
    }
}
