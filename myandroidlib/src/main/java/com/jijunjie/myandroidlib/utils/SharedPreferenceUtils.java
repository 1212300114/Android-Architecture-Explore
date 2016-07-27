package com.jijunjie.myandroidlib.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author Gary Ji
 * @description method class to handle shared preference I/O
 * @date 16/2/29.
 */
public class SharedPreferenceUtils {
    //the key of preference
    public static final String SP_KEY = "setting";
    public static final String ALL_VALUE = "all";

    private SharedPreferenceUtils() {
        throw new UnsupportedOperationException("method class can't be instantiated");
    }

    /**
     * method to save params to shared preference
     *
     * @param context generally activity
     * @param key     the key to store
     * @param value   the value to store
     */
    public static void put(Context context, String key, Object value) {
        SharedPreferences sharedPreference = context.getSharedPreferences(SP_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreference.edit();
        if (value instanceof String) {
            editor.putString(key, (String) value);
        } else if (value instanceof Integer) {
            editor.putInt(key, (Integer) value);
        } else if (value instanceof Boolean) {
            editor.putBoolean(key, (Boolean) value);
        } else if (value instanceof Float) {
            editor.putFloat(key, (Float) value);
        } else if (value instanceof Long) {
            editor.putLong(key, (Long) value);
        } else {
            editor.putString(key, value.toString());
        }
        editor.apply();
    }

    /**
     * method to get value from preference
     *
     * @param context      generally activity
     * @param key          the key to get  you can pass ALL_VALUE to get all
     * @param defaultValue the default value of this key
     * @return the value for key
     */

    public static Object get(Context context, String key, Object defaultValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SP_KEY, Context.MODE_PRIVATE);
        if (defaultValue instanceof String) {
            return sharedPreferences.getString(key, (String) defaultValue);
        } else if (defaultValue instanceof Integer) {
            return sharedPreferences.getInt(key, (Integer) defaultValue);
        } else if (defaultValue instanceof Boolean) {
            return sharedPreferences.getBoolean(key, (Boolean) defaultValue);
        } else if (defaultValue instanceof Float) {
            return sharedPreferences.getFloat(key, (Float) defaultValue);
        } else if (defaultValue instanceof Long) {
            return sharedPreferences.getLong(key, (Long) defaultValue);
        } else if (key.equals(ALL_VALUE)) {
            return sharedPreferences.getAll();
        }
        return null;
    }

    /**
     * to remove a key - value from the shared preferences
     *
     * @param context generally  activity
     * @param key     key to remove
     */
    public static void remove(Context context, String key) {
        SharedPreferences sharedPreference = context.getSharedPreferences(SP_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.remove(key);
        editor.apply();
    }

    /**
     * to clear all the data inside preference
     *
     * @param context generally activity
     */
    public static void clear(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SP_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

    public static boolean contains(Context context, String key) {

        return context.getSharedPreferences(SP_KEY, Context.MODE_PRIVATE).contains(key);
    }
}

