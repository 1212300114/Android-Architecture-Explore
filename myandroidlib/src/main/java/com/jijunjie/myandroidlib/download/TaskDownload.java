package com.jijunjie.myandroidlib.download;

import android.app.Notification;

/**
 * @说明 下载模型
 * @作者 LY
 * @文件 TaskDownload.java
 * @时间 2015年6月15日 下午5:26:08
 * @版权 Copyright(c) 2014 LY-版权所有
 */
public class TaskDownload {
	private String url;
	private int notifyID;
	private String fileName;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	private Notification notification;

	public TaskDownload() {
	}

	public Notification getNotification() {
		return notification;
	}

	public void setNotification(Notification notification) {
		this.notification = notification;
	}

	public int getNotifyID() {
		return notifyID;
	}

	public void setNotifyID(int notifyID) {
		this.notifyID = notifyID;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
