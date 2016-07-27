package com.jijunjie.myandroidlib.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.jijunjie.myandroidlib.R;
import com.jijunjie.myandroidlib.utils.PhotoLoader;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.finalteam.galleryfinal.CoreConfig;
import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.ThemeConfig;

/**
 * Created by Gary Ji on 16/2/26.
 * Base application to do some initialization here
 */
public abstract class BaseApplication extends Application {
    protected boolean networkEnable = true;
    protected int netWorkType;
    protected static BaseApplication sharedApplication;

        public static List<Activity> activities = new LinkedList<>();
    private RefWatcher refWatcher;

    public static RefWatcher getRefWatcher(Context context) {
        BaseApplication application = (BaseApplication) context.getApplicationContext();
        return application.refWatcher;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        //init bmob cloud database
        Bmob.initialize(this, "eeea90c06c19494c0c2610bb59c23e44");
        sharedApplication = this;
        initImageLoader();
        initGallery();
        onCreateCallback();
        refWatcher = LeakCanary.install(this);
    }

    //
    protected abstract void onCreateCallback();

    /**
     * add activity to the collection
     *
     * @param activity the
     */
    public static void addActivity(Activity activity) {
        activities.add(activity);
    }

    /**
     * exit application
     */
    public static void exit() {
        for (Activity activity : activities) {
            finishSelfWithAnimation(activity);
        }
        System.exit(0);
    }

    public static void finishSelfWithAnimation(Activity activity) {
        activity.finish();
        activity.overridePendingTransition(0, R.anim.default_quit_anim);
    }

    /**
     * To init image loader when the application is created
     */
    private void initImageLoader() {
        File cacheDir = StorageUtils.getOwnCacheDirectory(this, "yyxy/imageloader/Cache");
        ImageLoaderConfiguration.Builder loaderConfig = new ImageLoaderConfiguration.Builder(this);
        loaderConfig.threadPoolSize(5)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .diskCacheSize(50 * 1024 * 1024)
                .diskCache(new UnlimitedDiskCache(cacheDir))
                .memoryCacheSize(1024 * 1024)
                .memoryCache(new UsingFreqLimitedMemoryCache(1024 * 1024))
                .imageDownloader(new BaseImageDownloader(this, 5 * 1000, 30 * 1000))
                .tasksProcessingOrder(QueueProcessingType.LIFO);
//                .writeDebugLogs();
        if (!ImageLoader.getInstance().isInited()) {
            ImageLoader.getInstance().init(loaderConfig.build());
        }
    }

    private void initGallery() {
        //设置主题
        //ThemeConfig.CYAN
//        ThemeConfig theme = new ThemeConfig.Builder().setTitleBarBgColor(getResources().getColor(R.color.baseOrange)).set
//                .build();
        //配置功能
        FunctionConfig functionConfig = new FunctionConfig.Builder()
                .setEnableCamera(true)
                .setEnableEdit(true)
                .setEnableCrop(true)
                .setEnableRotate(true)
                .setCropSquare(true)
                .setEnablePreview(true)
                .build();

        //配置imageloader
        PhotoLoader photoLoader = new PhotoLoader();
        // 配置核心属性
        CoreConfig coreConfig = new CoreConfig.Builder(this, photoLoader, ThemeConfig.ORANGE)
                .setFunctionConfig(functionConfig)
                .build();
        GalleryFinal.init(coreConfig);
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
