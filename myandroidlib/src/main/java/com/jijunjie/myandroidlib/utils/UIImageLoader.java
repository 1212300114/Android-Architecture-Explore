package com.jijunjie.myandroidlib.utils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;

import cn.finalteam.galleryfinal.widget.GFImageView;

/**
 * Created by jijunjie on 16/5/7.
 */
public class UIImageLoader implements cn.finalteam.galleryfinal.ImageLoader{

        private Bitmap.Config mImageConfig;

        public UIImageLoader() {
            this(Bitmap.Config.RGB_565);
        }

        public UIImageLoader(Bitmap.Config config) {
            this.mImageConfig = config;
        }

        @Override
        public void displayImage(Activity activity, String path, GFImageView imageView, Drawable defaultDrawable, int width, int height) {
            DisplayImageOptions options = new DisplayImageOptions.Builder()
                    .cacheOnDisk(false)
                    .cacheInMemory(false)
                    .bitmapConfig(mImageConfig)
                    .build();
            ImageSize imageSize = new ImageSize(width, height);
            ImageLoader.getInstance().displayImage("file://" + path, new ImageViewAware(imageView), options, imageSize, null, null);
        }

        @Override
        public void clearMemoryCache() {

        }
}
