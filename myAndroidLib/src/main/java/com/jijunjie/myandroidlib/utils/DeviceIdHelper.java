package com.jijunjie.myandroidlib.utils;

import android.content.Context;
import android.provider.Settings;
import android.telephony.TelephonyManager;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

/**
 * @author Gary Ji
 * @description the uuid creator if device id or android id is available use them to generate uuid
 * if not use random uuid once uuid is generated store it and don't generate again
 * @date 2016/5/7 0007.
 */
public class DeviceIdHelper {
    protected static final String PREFS_DEVICE_ID = "device_id";

    protected static UUID uuid;


    public static String createUUID(Context context) {
        final String id = (String) SharedPreferenceUtils.get(context, PREFS_DEVICE_ID, null);
        if (id != null) {
            // Use the ids previously computed and stored in the prefs file
            uuid = UUID.fromString(id);

        } else {

            final String androidId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);

            // Use the Android ID unless it's broken, in which case fallback on deviceId,
            // unless it's not available, then fallback on a random number which we store
            // to a prefs file
            try {
                if (!"9774d56d682e549c".equals(androidId)) {
                    uuid = UUID.nameUUIDFromBytes(androidId.getBytes("utf8"));
                } else {
                    final String deviceId = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
                    uuid = deviceId != null ? UUID.nameUUIDFromBytes(deviceId.getBytes("utf8")) : UUID.randomUUID();
                }
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
            LogUtils.e( "----------------" + uuid.toString());
            // Write the value out to the prefs file
            SharedPreferenceUtils.put(context, PREFS_DEVICE_ID, uuid.toString());
        }

        return uuid.toString();
    }
}
