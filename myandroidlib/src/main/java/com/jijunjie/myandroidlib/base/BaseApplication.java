package com.jijunjie.myandroidlib.base;

import android.app.Application;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;

import cn.bmob.v3.Bmob;

/**
 * Created by jijunjie on 16/2/26.
 * Base application to do some initialization here
 */
public class BaseApplication extends Application {
    private boolean networkEnable = true;
    private int netWorkType ;
    private static BaseApplication sharedApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        Bmob.initialize(this, "eeea90c06c19494c0c2610bb59c23e44");
        sharedApplication = this;
        initImageLoader();
    }

    /**
     * To init image loader when the application is created
     */
    private void initImageLoader() {
        ImageLoaderConfiguration.Builder loaderConfig = new ImageLoaderConfiguration.Builder(this);
        loaderConfig.threadPoolSize(5)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .diskCacheSize(50 * 1024 * 1024)
                .memoryCacheSize(1024 * 1024)
                .memoryCache(new UsingFreqLimitedMemoryCache(1024 * 1024))
                .imageDownloader(new BaseImageDownloader(this, 5 * 1000, 30 * 1000))
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .writeDebugLogs();
        if (!ImageLoader.getInstance().isInited()) {
            ImageLoader.getInstance().init(loaderConfig.build());
        }
    }

    public boolean isNetworkEnable() {
        return networkEnable;
    }

    public void setNetworkEnable(boolean networkEnable) {
        this.networkEnable = networkEnable;
    }

    public static BaseApplication getSharedApplication() {
        return sharedApplication;
    }

    public int getNetWorkType() {
        return netWorkType;
    }

    public void setNetWorkType(int netWorkType) {
        this.netWorkType = netWorkType;
    }
}
