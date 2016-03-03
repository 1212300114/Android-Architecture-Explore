package com.jijunjie.myandroidlib.network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

/**
 * Created by jijunjie on 16/2/26.
 * to detect network change and give a callback to handle something
 */
public class InstantNetworkChangeManager {

    public static final String INSTANCE_NETWORK_STATE_ACTION = "Gary.Net.InstantNetWorkChangeReceiver";
    private InstantNetworkChangeManager instance;
    private Context context;
    private InstantNetWorkChangeReceiver receiver;

    /**
     * Hide the default construct method to use getInstance to get an singleton instance
     */
    private InstantNetworkChangeManager() {
        throw new UnsupportedOperationException("should use getInstance() to initialize");
    }

    /**
     * the singleton instance of the class
     *
     * @return singleton instance
     */
    public InstantNetworkChangeManager getInstance() {
        if (instance == null) {
            instance = new InstantNetworkChangeManager();
        }
        return instance;
    }

    /**
     * register a receiver with a context and callback to handle network change;
     *
     * @param context  who register the receiver;
     * @param callBack callback to handle network change;
     */
    public void registerReceiverWithCallback(Context context, InstantNetWorkChangeCallBack callBack) {
        IntentFilter filter = new IntentFilter();
        this.context = context;
        filter.addAction(INSTANCE_NETWORK_STATE_ACTION);
        receiver = new InstantNetWorkChangeReceiver(callBack);
        context.registerReceiver(receiver, filter);
    }

    public void unregisterInstantReceiver(Context context) {
        if (this.context != null) {
            if (this.context != context) {
                // should this cause problem?
            }
        }
        context.unregisterReceiver(receiver);

    }

    /**
     * the dynamic receiver to detect network change --- no register in the manifest
     */
    private class InstantNetWorkChangeReceiver extends BroadcastReceiver {

        private InstantNetWorkChangeCallBack callBack = null;

        public InstantNetWorkChangeReceiver(InstantNetWorkChangeCallBack callBack) {
            InstantNetWorkChangeReceiver.this.callBack = callBack;
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            boolean netWorkEnable;
            netWorkEnable = NetWorkState.isNetWorkEnable(context);
            if (intent.getAction().equals(InstantNetworkChangeManager.INSTANCE_NETWORK_STATE_ACTION)) {
                callBack.netWorkChange(netWorkEnable);
            }
        }
    }

    public interface InstantNetWorkChangeCallBack {
        void netWorkChange(boolean netWorkEnable);
    }
}
