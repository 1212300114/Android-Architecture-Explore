package com.jijunjie.myandroidlib.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

/**
 * Created by jijunjie on 16/2/26.
 * to checkout network state
 */
public class NetWorkState {
    //enum of network type;
    public static final int TYPE_WIFI = 0;
    public static final int TYPE_2G = 1;
    public static final int TYPE_3G = 2;

    private NetWorkState() {
        throw new UnsupportedOperationException("this class can't be instantiated");
    }

    /**
     * To checkout network enable
     *
     * @param context who check
     * @return network enable
     */
    public static boolean isNetWorkEnable(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (manager != null) {

            NetworkInfo info = manager.getActiveNetworkInfo();
            if (info != null) {
                return info.isAvailable();
            }
        }
        return false;
    }

    /**
     * to get the network type such as wifi
     *
     * @param context who check
     * @return type -1 means error
     */
    public static int getNetWorkType(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        if (info != null && info.isAvailable())
            if (info.getType() == ConnectivityManager.TYPE_WIFI) {
                return TYPE_WIFI;
            } else if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
                if (info.getSubtype() == TelephonyManager.NETWORK_TYPE_GPRS ||//lian tong
                        info.getSubtype() == TelephonyManager.NETWORK_TYPE_CDMA ||//dian xin
                        info.getSubtype() == TelephonyManager.NETWORK_TYPE_EDGE) {//yi dong
                    return TYPE_2G;
                } else {
                    return TYPE_3G;
                }
            }
        return -1;
    }

}
