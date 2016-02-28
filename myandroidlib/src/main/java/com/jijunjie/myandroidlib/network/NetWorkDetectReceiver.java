package com.jijunjie.myandroidlib.network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;

import com.jijunjie.myandroidlib.base.BaseApplication;

/**
 * Created by jijunjie on 16/2/26.
 * To detect net work change should be write inside the manifest
 */
public class NetWorkDetectReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
            BaseApplication.getSharedApplication().setNetworkEnable(NetWorkState.isNetWorkEnable(context));
            BaseApplication.getSharedApplication().setNetWorkType(NetWorkState.getNetWorkType(context));
        }
    }

}
