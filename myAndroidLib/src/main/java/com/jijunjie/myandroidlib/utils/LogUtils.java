package com.jijunjie.myandroidlib.utils;

import android.util.Log;

/**
 * @author Gary Ji
 * @date 2016/4/29 0029.
 * @description the log helper class when String is longer than 4000 logcat can't print it
 */
public class LogUtils {
    // 使用Log来显示调试信息,因为log在实现上每个message有4k字符长度限制
    // 所以这里使用自己分节的方式来输出足够长度的message
    public static void show(String str) {
        int index = 0;
        int maxLength = 4000;
        String sub;
        while (index < str.length()) {
            // java的字符不允许指定超过总的长度end
            if (str.length() <= index + maxLength) {
                sub = str.substring(index);
            } else {
                sub = str.substring(index, index + maxLength);
            }

            index += maxLength;
            LogUtils.i(sub);
        }
    }

    public static boolean isShow = true;

    public static void setIsShow(boolean isShow) {
        LogUtils.isShow = isShow;
    }

    //ye工程师打出来的log
    public static void d(String msg) {
        if (isShow) {
            Log.d("yyxy", msg);
        }
    }

    public static void i(String msg) {
        if (isShow)
            Log.i("yyxy", msg);
    }

    public static void e(String msg) {
        if (isShow)
            Log.e("yyxy", msg);
    }
}
