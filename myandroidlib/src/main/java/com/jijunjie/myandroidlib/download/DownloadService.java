package com.jijunjie.myandroidlib.download;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;

import net.xinhuamm.temp.R;
import net.xinhuamm.temp.base.BaseActivity;
import net.xinhuamm.temp.utils.FileManagerUtils;
import net.xinhuamm.temp.utils.LogUtils;
import net.xinhuamm.temp.utils.ToastUtil;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.text.TextUtils;

import com.ypy.eventbus.EventBus;

/**
 * @说明 文件下载服务
 * @作者 LY
 * @文件 DownloadService.java
 * @时间 2015年6月23日 下午1:48:00
 * @版权 Copyright(c) 2015 LY-版权所有
 * @email dengdai.ly@qq.com
 */
public class DownloadService extends Service {
    protected Timer mTimer;
    protected Map<String, TaskDownload> map_downloadtask;
    protected NotificationManager mNotificationManager;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mTimer = new Timer();
        map_downloadtask = new HashMap<String, TaskDownload>();
        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null && !TextUtils.isEmpty(intent.getExtras().getString(DownLoadTypeAction.URLACTION))) {
            String mUrl = intent.getExtras().getString(DownLoadTypeAction.URLACTION);
            int mNotifyId = intent.getExtras().getInt(DownLoadTypeAction.NOTIFYIDACTION);
            boolean show = intent.getExtras().getBoolean(DownLoadTypeAction.SHOWNOTIFYIDACTION, true);
            String fileName = getFileName(mUrl);
            Notification mNotification = new NotificationDownload(this, R.drawable.common_download, "开始下载", System.currentTimeMillis(), fileName, BaseActivity.class);
            if (map_downloadtask.containsKey(mUrl)) {
                EventBus.getDefault().post("download_file_exist");
            } else {
                TaskDownload mDownloadTask = new TaskDownload();
                mDownloadTask.setUrl(mUrl);
                mDownloadTask.setNotifyID(mNotifyId);
                mDownloadTask.setFileName(fileName);
                if (show) {
                    mDownloadTask.setNotification(mNotification);
                }
                map_downloadtask.put(mUrl, mDownloadTask);
                Runnable mRunnable = new MyRunnable(mDownloadTask);
                new Thread(mRunnable).start();
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * 根据文件路径获取文件名称
     *
     * @param path 文件路径
     * @return 文件名称
     */
    public static String getFileName(String path) {
        String str = "";
        String paths[] = path.split("/");
        if (paths.length > 1) {
            str = paths[paths.length - 1];
        }
        return str;
    }

    class MyRunnable implements Runnable {
        private DownloadUtils mDownUtil = new DownloadUtils();
        private TaskDownload mDownTask;

        public MyRunnable(TaskDownload downTask) {
            super();
            this.mDownTask = downTask;
        }

        @Override
        public void run() {
            mDownUtil.setOnDownloadListener(onDownloadListenernew);
            mDownUtil.download(mDownTask.getUrl(), FileManagerUtils.getInstance().getAppPath() + "/" + mDownTask.getFileName());
        }

        OnDownloadListener onDownloadListenernew = new OnDownloadListener() {
            int fileSize = 0;

            @Override
            public void onDownloadUpdate(int percent) {
                if (fileSize > 0) {
                    float size = (float) percent * 100 / (float) fileSize;
                    DecimalFormat format = new DecimalFormat("0.00");
                    String progress = format.format(size);
                    for (int i = 0; i <= 100; i++) {
                        if (progress.equals(String.valueOf(i) + ".00")) {
                            if (mDownTask.getNotification() != null) {
                                ((Notification) mDownTask.getNotification()).contentView.setProgressBar(R.id.progressBar, 100, (int) size, false);
                                mNotificationManager.notify(mDownTask.getNotifyID(), ((Notification) mDownTask.getNotification()));
                            }
                        }
                    }
                }
            }

            @Override
            public void onDownloadError(Exception e) {
                map_downloadtask.remove(((TaskDownload) mDownTask).getUrl());
                EventBus.getDefault().post("download_file_error");
                stopService();
            }

            @Override
            public void onDownloadConnect(int filesize) {
                this.fileSize = filesize;
            }

            @Override
            public void onDownloadComplete(Object result) {
                if (mDownTask.getNotification() != null) {
                    mNotificationManager.cancel(mDownTask.getNotifyID());
                }
                Intent intent = new Intent();
                intent.setAction(DownLoadTypeAction.DOWNLOADTYPEACTION);
                intent.putExtra(DownLoadTypeAction.FILEPATHACTION, FileManagerUtils.getInstance().getAppPath() + "/" + mDownTask.getFileName());
                sendBroadcast(intent);
                stopService();
                map_downloadtask.remove(mDownTask.getUrl());// 移除已完成任务
            }
        };

        /**
         * 关闭下载服务
         */
        private void stopService() {
            if (map_downloadtask.isEmpty()) {
                stopSelf(-1);
            }
        }
    }
}
