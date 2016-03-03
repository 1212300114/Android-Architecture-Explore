package com.jijunjie.myandroidlib.download;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import android.os.Handler;
import android.os.Message;
import android.webkit.URLUtil;

/**
 * @说明 下载文件工具类
 * @作者 LY
 * @文件 DownloadUtils.java
 * @时间 2015年6月15日 下午5:24:36
 * @版权 Copyright(c) 2014 LY-版权所有
 */
public class DownloadUtils {
    private final int DOWN_START = 1; // Handler消息类型(开始下载)
    private final int DOWN_POSITION = 2; // Handler消息类型(下载位置)
    private final int DOWN_COMPLETE = 3; // Handler消息类型(下载完成)
    private final int DOWN_ERROR = 4; // Handler消息类型(下载失败)
    private OnDownloadListener onDownloadListener;

    public void setOnDownloadListener(OnDownloadListener onDownloadListener) {
        this.onDownloadListener = onDownloadListener;
    }

    /**
     * 下载文件
     *
     * @param url      文件路径
     * @param filepath 保存地址
     */
    public void download(String url, String filepath) {
        MyRunnable mr = new MyRunnable();
        mr.url = url;
        mr.filepath = filepath;
        new Thread(mr).start();
    }

    @SuppressWarnings("unused")
    private void sendMsg(int what) {
        sendMsg(what, null);
    }

    private void sendMsg(int what, Object mess) {
        Message m = myHandler.obtainMessage();
        m.what = what;
        m.obj = mess;
        m.sendToTarget();
    }

    Handler myHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case DOWN_START: // 开始下载
                    int filesize = (Integer) msg.obj;
                    onDownloadListener.onDownloadConnect(filesize);
                    break;
                case DOWN_POSITION: // 下载位置
                    int pos = (Integer) msg.obj;
                    onDownloadListener.onDownloadUpdate(pos);
                    break;
                case DOWN_COMPLETE: // 下载完成
                    String url = (String) msg.obj;
                    onDownloadListener.onDownloadComplete(url);
                    break;
                case DOWN_ERROR: // 下载失败
                    Exception e = (Exception) msg.obj;
                    e.printStackTrace();
                    onDownloadListener.onDownloadError(e);
                    break;
            }
            super.handleMessage(msg);
        }
    };

    class MyRunnable implements Runnable {
        private String url = "";
        private String filepath = "";

        @Override
        public void run() {
            try {
                doDownloadTheFile(url, filepath);
            } catch (Exception e) {
                sendMsg(DOWN_ERROR, e);
            }
        }
    }

    /**
     * 下载文件
     *
     * @param url      下载路劲
     * @param filepath 保存路径
     * @throws Exception
     */
    private void doDownloadTheFile(String url, String filepath) throws Exception {
        if (!URLUtil.isNetworkUrl(url)) {
            sendMsg(DOWN_ERROR, new Exception("不是有效的下载地址：" + url));
            return;
        }
        URL myUrl = new URL(url);
        URLConnection conn = myUrl.openConnection();
        conn.connect();
        InputStream is = null;
        int filesize = 0;
        try {
            is = conn.getInputStream();
            filesize = conn.getContentLength();// 根据响应获取文件大小
            sendMsg(DOWN_START, filesize);
        } catch (Exception e) {
            sendMsg(DOWN_ERROR, new Exception(new Exception("无法获取文件")));
            return;
        }
        FileOutputStream fos = new FileOutputStream(filepath); // 创建写入文件内存流，
        // 通过此流向目标写文件
        byte buf[] = new byte[1024];
        int numread = 0;
        int temp = 0;
        while ((numread = is.read(buf)) != -1) {
            fos.write(buf, 0, numread);
            fos.flush();
            temp += numread;
            sendMsg(DOWN_POSITION, temp);
        }
        is.close();
        fos.close();
        sendMsg(DOWN_COMPLETE, filepath);
    }
}
