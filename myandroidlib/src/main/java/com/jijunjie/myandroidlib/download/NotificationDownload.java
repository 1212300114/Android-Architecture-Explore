package com.jijunjie.myandroidlib.download;

import net.xinhuamm.temp.R;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

/**
 * @说明 下载通知栏进度
 * @作者 LY
 * @文件 NotificationDownload.java
 * @时间 2015年7月31日 下午1:20:03
 */
public class NotificationDownload extends Notification {
	private Context mContext;

	@SuppressWarnings("deprecation")
	public <T> NotificationDownload(Context context, int icon, CharSequence tickerText, long when, String fileName, Class<T> c) {
		super(icon, tickerText, when);
		this.mContext = context;
		this.flags = Notification.FLAG_AUTO_CANCEL;
		RemoteViews mRemoteView = new RemoteViews(mContext.getPackageName(), R.layout.common_download_notification);
		this.contentView = mRemoteView;
		mRemoteView.setTextViewText(R.id.download_name, fileName);
		Intent intent = new Intent(mContext, c);
		intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		PendingIntent pIntent = PendingIntent.getActivity(mContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		this.contentIntent = pIntent;
	}

}
