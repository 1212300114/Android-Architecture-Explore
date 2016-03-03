package com.jijunjie.myandroidlib.download;

/**
 * @说明 下载工具类
 * @作者 LY
 * @文件 OnDownloadListener.java
 * @时间 2015年6月15日 下午5:25:46
 * @版权 Copyright(c) 2014 LY-版权所有
 */
public interface OnDownloadListener {
    /**
     * 下载进度
     */
    void onDownloadUpdate(int percent);

    /**
     * 下载失败
     */
    void onDownloadError(Exception e);

    /**
     * 开始下载
     */
    void onDownloadConnect(int filesize);

    /**
     * 完成下载
     */
    void onDownloadComplete(Object result);
}
