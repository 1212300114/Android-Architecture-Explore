package com.jijunjie.myandroidlib.utils;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;

import java.io.File;

/**
 * SD card check method class
 * Created by jijunjie on 16/2/29.
 */
public class SDCardUtils {
    private SDCardUtils() {
        throw new UnsupportedOperationException("method class should not be instantiated");
    }

    /**
     * check if the phone has sd card
     *
     * @return has sd card or not;
     */
    public static boolean isSDCardEnabled() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }


    /**
     * get the path of sd card
     *
     * @return the path of the sd card
     */
    public static String getSDCardPath() {
        return Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator;
    }

    /**
     * get the remain si of the sd card
     *
     * @return long   - the size of remain
     */

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static long getSDCardRemain() {
        long bytesAvailable = 0;
        if (isSDCardEnabled()) {
            StatFs stat = new StatFs(Environment.getExternalStorageDirectory().getPath());
            bytesAvailable = stat.getBlockSizeLong() * stat.getBlockCountLong();
        }
        return bytesAvailable;
    }
}
