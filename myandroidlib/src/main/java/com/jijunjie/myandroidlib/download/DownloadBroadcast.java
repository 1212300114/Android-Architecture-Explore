package com.jijunjie.myandroidlib.download;

import net.xinhuamm.temp.utils.AppUtils;
import net.xinhuamm.temp.utils.LogUtils;
import net.xinhuamm.temp.utils.ToastUtil;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * @说明 下载广播
 * @作者 LY
 * @文件 DownloadBroadcast.java
 * @时间 2015年6月15日 下午8:48:28
 * @版权 Copyright(c) 2014 LY-版权所有
 */
public class DownloadBroadcast extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action.equals(DownLoadTypeAction.DOWNLOADTYPEACTION)) {
            String filePath = intent.getStringExtra(DownLoadTypeAction.FILEPATHACTION);
            if (filePath.endsWith(".apk")) {
                AppUtils.installApk(context, filePath);
            } else {
                LogUtils.i("===>下载完成");
            }
        } else if (action.equals(DownLoadTypeAction.DOWNLOADFILESIZEACTION)) {
            LogUtils.i(intent.getStringExtra(DownLoadTypeAction.DOWNLOADFILESIZEPERCENT) + "%");
        }
    }
}
