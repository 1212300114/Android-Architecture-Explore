package com.jijunjie.myandroidlib.utils;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

/**
 * Created by Administrator on 2016/3/29 0029.
 */
public class ToastUtils {
    private ToastUtils() {
        throw new UnsupportedOperationException("method class can't be instantiated");
    }

    public static void showToast(Context context, String msg) {
        if (context != null && !TextUtils.isEmpty(msg))
            Toast.makeText(context.getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
