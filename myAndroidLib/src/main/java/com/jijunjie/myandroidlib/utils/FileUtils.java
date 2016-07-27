package com.jijunjie.myandroidlib.utils;

import android.content.Context;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Administrator on 2016/6/1 0001.
 */
public class FileUtils {

    public static void saveFile(Context context, String fileName, String content, int mode, boolean showToast) {
        try {
            FileOutputStream outputStream = context.openFileOutput(fileName, mode);
            outputStream.write(content.getBytes());
            outputStream.flush();
            outputStream.close();
            if (showToast) ToastUtils.showToast(context, "文件保存成功");
        } catch (IOException e) {
            if (showToast) ToastUtils.showToast(context, "文件保存失败");
            e.printStackTrace();
        }
    }

    public static String readFile(Context context, String fileName, boolean showToast) {
        try {
            FileInputStream fileStream = context.openFileInput(fileName);
            InputStreamReader reader = new InputStreamReader(fileStream);
            BufferedReader buffReader = new BufferedReader(reader);
            StringBuilder builder = new StringBuilder();
            String line;
            //分行读取
            while ((line = buffReader.readLine()) != null) {
                builder.append(line);
            }
            fileStream.close();
            reader.close();
            buffReader.close();
            if (showToast) ToastUtils.showToast(context, "文件打开成功");
            return builder.toString();
        } catch (IOException e) {
            if (showToast) ToastUtils.showToast(context, "文件打开失败");
            return null;
        }
    }

    public static void deleteFile(Context context, String fileName) {
        context.deleteFile(fileName);
    }

    /**
     * md5 encode String data
     * @param val data
     * @return encoded string
     * @throws NoSuchAlgorithmException
     */
    public static String getMD5(String val) throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.update(val.getBytes());
        byte[] m = md5.digest();//加密
        return getString(m);
    }

    private static String getString(byte[] b) {
        StringBuilder sb = new StringBuilder();
        for (byte aB : b) {
            sb.append(aB);
        }
        return sb.toString();
    }
}
