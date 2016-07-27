package com.jijunjie.myandroidlib.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.media.ExifInterface;
import android.os.Build;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.jijunjie.myandroidlib.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * @author nate
 * @ClassName: ImageUtils
 * @Description: 图片展示工具类
 * @date 2015-5-26 下午4:09:56
 */
public class ImageUtils {

    private ImageUtils() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public static DisplayImageOptions DISPLAY_OPTIONS =
            new DisplayImageOptions.Builder().showImageForEmptyUri(R.drawable.common_images_normal)
                    .showImageOnFail(R.drawable.common_images_normal)
                    .showImageOnLoading(R.drawable.common_images_normal)
                    .cacheInMemory(true)
                    .cacheOnDisk(true)
                    .imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                    .bitmapConfig(Config.RGB_565)
                    .build();

    public static DisplayImageOptions ICON_OPTIONS = new DisplayImageOptions.Builder().showImageForEmptyUri(R.drawable.ic_launcher)
            .showImageOnFail(R.drawable.ic_default_user)
            .showImageOnLoading(R.drawable.ic_default_user)
            .cacheInMemory(true)
            .cacheOnDisk(true)
            .imageScaleType(ImageScaleType.IN_SAMPLE_INT)
            .bitmapConfig(Config.RGB_565)
            .build();


    public static void displayIconOnNet(final ImageView img, final String url) {
        ImageLoader.getInstance().displayImage(url,
                img,
                ICON_OPTIONS,
                new AnimateFirstDisplayRoundListener());
    }

    /**
     * 加载网络圆形图片
     *
     * @param img image view to be set
     * @param url image address at network
     */
    public static void displayRoundImgOnNet(final ImageView img, final String url) {
        ImageLoader.getInstance().displayImage(url,
                img,
                DISPLAY_OPTIONS,
                new AnimateFirstDisplayRoundListener());
    }

    /**
     * 加载网络圆角图片
     *
     * @param img image view to be set
     * @param url image address at network
     */
    public static void displayRoundCornerImgOnNet(final ImageView img, final String url) {
        ImageLoader.getInstance().displayImage(url,
                img,
                DISPLAY_OPTIONS,
                new AnimateFirstDisplayRoundCornerListener());
    }

    /**
     * 本地圆角图片 ---drawable
     *
     * @param img        image view to be set
     * @param resourceId bitmap resource id
     */
    public static void disPlayLocRoundCornerImg(final ImageView img, int resourceId) {
        Bitmap mBitmap = BitmapFactory.decodeResource(img.getContext().getResources(), resourceId);
        img.setImageBitmap(toRoundCorner(mBitmap));
    }

    /**
     * 本地圆形图片 ---drawable
     *
     * @param img        image view to be set
     * @param resourceId bitmap resource id
     */
    public static void disPlayLocRoundImg(final ImageView img, int resourceId) {
        Bitmap mBitmap = BitmapFactory.decodeResource(img.getContext().getResources(), resourceId);
        img.setImageBitmap(toRoundBitmap(mBitmap));
    }

    /**
     * 显示本地正常图片 --file
     *
     * @param img image view to be set
     * @param url local file url
     */
    public static void displayLocImg(ImageView img, String url) {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        if (!url.startsWith("file:")) {
            url = "file:/" + url;
        }
        ImageLoader.getInstance().displayImage(url, img, DISPLAY_OPTIONS, new AnimateFirstDisplayListener());
    }

    /**
     * 本地圆角图片 ---file
     *
     * @param img image view to be set
     * @param url local file url
     */
    public static void disPlayLocRoundCornerImg(ImageView img, String url) {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        if (!url.startsWith("file:")) {
            url = "file://" + url;
        }
        ImageLoader.getInstance().displayImage(url, img, DISPLAY_OPTIONS, new AnimateFirstDisplayRoundCornerListener());
    }

    /**
     * 本地圆形图片 ---file
     *
     * @param img image view to be set
     * @param url local file url
     */
    public static void disPlayLocRoundImg(ImageView img, String url) {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        if (!url.startsWith("file:")) {
            url = "file:/" + url;
        }
        ImageLoader.getInstance().displayImage(url, img, DISPLAY_OPTIONS, new AnimateFirstDisplayRoundListener());
    }

    /**
     * 用于显示普通的网络图片
     *
     * @param img image view to be set
     * @param url network address
     */
    public static void displayNormalImgOnNet(final ImageView img, final String url) {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        ImageLoader.getInstance().displayImage(url, new ImageViewAware(img, false), DISPLAY_OPTIONS, new AnimateFirstDisplayListener());
    }


    /**
     * 显示根据布局自适应大小的网络正常图片
     *
     * @param imageView image view to be set
     * @param url
     */
    public static void displayAutoImgOnNet(final ImageView imageView, final String url) {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        ImageLoader.getInstance().displayImage(url,
                imageView,
                DISPLAY_OPTIONS,
                new SimpleImageLoadingListener() {
                    final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());

                    @Override
                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                        super.onLoadingComplete(imageUri, view, loadedImage);
                        float[] cons = ScreenUtils.getBitmapConfiguration(loadedImage, imageView, 1.0f);
                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams((int) cons[0], (int) cons[1]);
                        layoutParams.setMargins(0, 0, 0, ScreenUtils.dp2px(imageView.getContext(), 8));
                        imageView.setLayoutParams(layoutParams);
                        boolean firstDisplay = !displayedImages.contains(imageUri);
                        if (firstDisplay) {
                            FadeInBitmapDisplayer.animate(imageView, 500);
                            displayedImages.add(imageUri);
                        } else {
                            imageView.setImageBitmap(loadedImage);
                        }
                    }
                });
    }

    /**
     * 获取圆角位图的方法
     */
    public static Bitmap toRoundCorner(Bitmap bitmap) {
        float roundPx = 5;
        if (null == bitmap || bitmap.isRecycled()) {
            return null;
        }
        try {
            Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getWidth(), Config.ARGB_8888);
            Canvas canvas = new Canvas(output);
            final int color = 0xff424242;
            final Paint paint = new Paint();
            final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getWidth());
            final RectF rectF = new RectF(rect);
            paint.setAntiAlias(true);
            canvas.drawARGB(0, 0, 0, 0);
            paint.setColor(color);
            canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG
                    | Paint.FILTER_BITMAP_FLAG));
            roundPx = bitmap.getWidth() / 10;// 此处如除以2了 则就是圆图了 就不是圆角了
            canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
            paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
            canvas.drawBitmap(bitmap, rect, rect, paint);
            return output;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 产生一个fadeIn动画显示Round图片
     */
    public static class AnimateFirstDisplayRoundListener extends SimpleImageLoadingListener {
        static final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());

        @Override
        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
            ImageView imageView = (ImageView) view;
            if (loadedImage != null) {
                boolean firstDisplay = !displayedImages.contains(imageUri);
                if (firstDisplay) {
                    displayedImages.add(imageUri);
                    FadeInDisplay(imageView, toRoundBitmap(loadedImage));
                } else {
                    imageView.setImageBitmap(toRoundBitmap(loadedImage));
                }
                if (TextUtils.isEmpty(imageUri)) {
                    Bitmap bitmap = BitmapFactory.decodeResource(view.getResources(), R.drawable.ic_launcher);
                    imageView.setImageBitmap(toRoundBitmap(bitmap));
                }
            } else {
                Bitmap bitmap = BitmapFactory.decodeResource(view.getResources(), R.drawable.ic_launcher);
                imageView.setImageBitmap(toRoundBitmap(bitmap));
            }
        }

        @Override
        public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
            LogUtils.e("failreason = " + failReason);
            super.onLoadingFailed(imageUri, view, failReason);
        }

        @Override
        public void onLoadingStarted(String imageUri, View view) {
            super.onLoadingStarted(imageUri, view);
        }
    }


    /**
     * 产生一个fadeIn动画显示Corner图片
     */
    public static class AnimateFirstDisplayRoundCornerListener extends SimpleImageLoadingListener {
        static final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());

        @Override
        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
            if (loadedImage != null) {
                ImageView imageView = (ImageView) view;
                boolean firstDisplay = !displayedImages.contains(imageUri);
                if (firstDisplay) {
                    displayedImages.add(imageUri);
                    FadeInDisplay(imageView, toRoundCorner(loadedImage));
                } else {
                    imageView.setImageBitmap(toRoundCorner(loadedImage));
                }
            }
        }

    }

    /**
     * 产生一个fadeIn动画显示正常图片
     */
    private static class AnimateFirstDisplayListener extends SimpleImageLoadingListener {
        static final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());

        @Override
        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
            if (loadedImage != null) {
                ImageView imageView = (ImageView) view;
                boolean firstDisplay = !displayedImages.contains(imageUri);
                if (firstDisplay) {
                    FadeInBitmapDisplayer.animate(imageView, 500);
                    displayedImages.add(imageUri);
                } else {
                    imageView.setImageBitmap(loadedImage);
                }
            }
        }
    }

    /**
     * 这是为了能够对Bitmap进行处理之后再展示
     *
     * @param imageView
     * @param bitmap
     */
    public static void FadeInDisplay(ImageView imageView, Bitmap bitmap) {
        final TransitionDrawable transitionDrawable =
                new TransitionDrawable(new Drawable[]{new ColorDrawable(imageView.getContext()
                        .getResources().getColor(android.R.color.transparent)),
                        new BitmapDrawable(imageView.getResources(), bitmap)});
        transitionDrawable.startTransition(500);
        imageView.setImageDrawable(transitionDrawable);
    }


    /**
     * 读取图片属性：旋转的角度
     *
     * @param path
     * @return
     */
    public static int readPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation =
                    exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;

    }

    /**
     * 旋转图片一定角度 rotaingImageView
     *
     * @param angle
     * @param bitmap
     * @return
     */
    public static Bitmap rotaingImageView(int angle, Bitmap bitmap) {
        // 旋转图片 动作
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        // 创建新的图片
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return resizedBitmap;
    }

    /**
     * 将图片转化为圆形头像
     *
     * @param bitmap
     * @return
     */
    private static Bitmap toRoundBitmap(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float roundPx;
        float left, top, right, bottom, dst_left, dst_top, dst_right, dst_bottom;
        if (width <= height) {
            roundPx = width / 2;
            left = 0;
            top = 0;
            right = width;
            bottom = width;
            height = width;
            dst_left = 0;
            dst_top = 0;
            dst_right = width;
            dst_bottom = width;
        } else {
            roundPx = height / 2;
            float clip = (width - height) / 2;
            left = clip;
            right = width - clip;
            top = 0;
            bottom = height;
            width = height;
            dst_left = 0;
            dst_top = 0;
            dst_right = height;
            dst_bottom = height;
        }
        Bitmap output = Bitmap.createBitmap(width, height, Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final Paint paint = new Paint();
        final Rect src = new Rect((int) left, (int) top, (int) right, (int) bottom);
        final Rect dst = new Rect((int) dst_left, (int) dst_top, (int) dst_right, (int) dst_bottom);
        final RectF rectF = new RectF(dst);

        paint.setAntiAlias(true);// 设置画笔无锯齿
        canvas.drawARGB(0, 0, 0, 0); // 填充整个Canvas
        // 以下有两种方法画圆,drawRounRect和drawCircle
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);// 画圆角矩形，第一个参数为图形显示区域，第二个参数和第三个参数分别是水平圆角半径和垂直圆角半径。
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));// 设置两张图片相交时的模式,参考http://trylovecatch.iteye.com/blog/1189452
        canvas.drawBitmap(bitmap, src, dst, paint); // 以Mode.SRC_IN模式合并bitmap和已经draw了的Circle
        return output;
    }

    /**
     * 以最省内存的方式读取本地资源的图片 hdpi文件夹下
     */
    @SuppressWarnings("deprecation")
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
    private static Bitmap readBitMap(Context context, int resId) {
        Bitmap bmp = null;
        BitmapFactory.Options opt = new BitmapFactory.Options();
        try {
            opt.inPreferredConfig = Config.RGB_565;
            opt.inPurgeable = true;
            opt.inInputShareable = true;
            opt.inJustDecodeBounds = true;// 只返回宽高,不返回bitmap的Byte
            // 获取资源图片
            InputStream is = context.getResources().openRawResource(resId);
            bmp = BitmapFactory.decodeStream(is, null, opt);
            int hi = (opt.outHeight / ScreenUtils.getScreenHeight(context));// 以屏幕高度作为显示依据
            int wi = (opt.outWidth / ScreenUtils.getScreenWidth(context));// 以屏幕高度作为显示依据
            int be = 0;
            if (wi > hi)
                be = hi;
            else
                be = wi;
            if (be > 0)
                opt.inSampleSize = be; // 重新读入图片，注意此时已经把
            // options.inJustDecodeBounds 设回 false 了
            opt.inJustDecodeBounds = false;
            bmp = BitmapFactory.decodeStream(is, null, opt);
        } catch (OutOfMemoryError error) {
        } catch (Exception ex) {
            Log.d("readBitMap", "" + ex.getMessage());
        }
        return bmp;
    }


    /**
     * Get bitmap from specified image path
     *
     * @param imgPath
     * @return
     */
    public static Bitmap getBitmap(String imgPath) {
        // Get bitmap through image path
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        newOpts.inJustDecodeBounds = false;
        newOpts.inPurgeable = true;
        newOpts.inInputShareable = true;
        // Do not compress
        newOpts.inSampleSize = 1;
        newOpts.inPreferredConfig = Config.RGB_565;
        return BitmapFactory.decodeFile(imgPath, newOpts);
    }

    /**
     * Store bitmap into specified image path
     *
     * @param bitmap
     * @param outPath
     * @throws FileNotFoundException
     */
    public static void storeImage(Bitmap bitmap, String outPath) throws FileNotFoundException {
        FileOutputStream os = new FileOutputStream(outPath);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
    }

    /**
     * Compress image by pixel, this will modify image width/height.
     * Used to get thumbnail
     *
     * @param imgPath image path
     * @param pixelW  target pixel of width
     * @param pixelH  target pixel of height
     * @return
     */
    public static Bitmap ratio(String imgPath, float pixelW, float pixelH) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        // 开始读入图片，此时把options.inJustDecodeBounds 设回true，即只读边不读内容
        newOpts.inJustDecodeBounds = true;
        newOpts.inPreferredConfig = Config.RGB_565;
        // Get bitmap info, but notice that bitmap is null now
        Bitmap bitmap = BitmapFactory.decodeFile(imgPath, newOpts);

        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        // 想要缩放的目标尺寸
        // 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        if (w > h && w > pixelW) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / pixelW);
        } else if (w < h && h > pixelH) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / pixelH);
        }
        if (be <= 0) be = 1;
        newOpts.inSampleSize = be;//设置缩放比例
        // 开始压缩图片，注意此时已经把options.inJustDecodeBounds 设回false了
        bitmap = BitmapFactory.decodeFile(imgPath, newOpts);
        // 压缩好比例大小后再进行质量压缩
//        return compress(bitmap, maxSize); // 这里再进行质量压缩的意义不大，反而耗资源，删除
        return bitmap;
    }

    /**
     * Compress image by size, this will modify image width/height.
     * Used to get thumbnail
     *
     * @param image
     * @param pixelW target pixel of width
     * @param pixelH target pixel of height
     * @return
     */
    public static Bitmap ratio(Bitmap image, float pixelW, float pixelH) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, os);
        if (os.toByteArray().length / 1024 > 1024) {//判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出
            os.reset();//重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, 50, os);//这里压缩50%，把压缩后的数据存放到baos中
        }
        ByteArrayInputStream is = new ByteArrayInputStream(os.toByteArray());
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        newOpts.inPreferredConfig = Config.RGB_565;
        Bitmap bitmap = BitmapFactory.decodeStream(is, null, newOpts);
        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        LogUtils.e("width " + w);
        LogUtils.e("height " + h);
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        if (w >= h && w > pixelW) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / pixelW);
        } else if (w < h && h > pixelH) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / pixelH);
        }
        if (be <= 0) be = 1;
        newOpts.inSampleSize = be;//设置缩放比例
        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        is = new ByteArrayInputStream(os.toByteArray());
        bitmap = BitmapFactory.decodeStream(is, null, newOpts);
        //压缩好比例大小后再进行质量压缩
//      return compress(bitmap, maxSize); // 这里再进行质量压缩的意义不大，反而耗资源，删除
        return bitmap;
    }

    /**
     * Compress by quality,  and generate image to the path specified
     *
     * @param image
     * @param outPath
     * @param maxSize target will be compressed to be smaller than this size.(kb)
     * @throws IOException
     */
    public static void compressAndGenImage(Bitmap image, String outPath, int maxSize) throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        // scale
        int options = 100;
        // Store the bitmap into output stream(no compress)
        image.compress(Bitmap.CompressFormat.JPEG, options, os);
        // Compress by loop
        while (os.toByteArray().length / 1024 > maxSize) {
            // Clean up os
            os.reset();
            // interval 10
            options -= 10;
            image.compress(Bitmap.CompressFormat.JPEG, options, os);
        }

        // Generate compressed image file
        FileOutputStream fos = new FileOutputStream(outPath);
        fos.write(os.toByteArray());
        fos.flush();
        fos.close();
    }

    /**
     * Compress by quality,  and generate image to the path specified
     *
     * @param imgPath
     * @param outPath
     * @param maxSize     target will be compressed to be smaller than this size.(kb)
     * @param needsDelete Whether delete original file after compress
     * @throws IOException
     */
    public static void compressAndGenImage(String imgPath, String outPath, int maxSize, boolean needsDelete) throws IOException {
        compressAndGenImage(getBitmap(imgPath), outPath, maxSize);

        // Delete original file
        if (needsDelete) {
            File file = new File(imgPath);
            if (file.exists()) {
                file.delete();
            }
        }
    }

    /**
     * Ratio and generate thumb to the path specified
     *
     * @param image
     * @param outPath
     * @param pixelW  target pixel of width
     * @param pixelH  target pixel of height
     * @throws FileNotFoundException
     */
    public static void ratioAndGenThumb(Bitmap image, String outPath, float pixelW, float pixelH) throws FileNotFoundException {
        Bitmap bitmap = ratio(image, pixelW, pixelH);
        storeImage(bitmap, outPath);
    }

    /**
     * Ratio and generate thumb to the path specified
     *
     * @param outPath
     * @param pixelW      target pixel of width
     * @param pixelH      target pixel of height
     * @param needsDelete Whether delete original file after compress
     * @throws FileNotFoundException
     */
    public static void ratioAndGenThumb(String imgPath, String outPath, float pixelW, float pixelH, boolean needsDelete) throws FileNotFoundException {
        Bitmap bitmap = ratio(imgPath, pixelW, pixelH);
        storeImage(bitmap, outPath);

        // Delete original file
        if (needsDelete) {
            File file = new File(imgPath);
            if (file.exists()) {
                file.delete();
            }
        }
    }

    public static Bitmap imageToSampleSize(Bitmap image) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 > 100) {  //循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;//每次都减少10
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
        return BitmapFactory.decodeStream(isBm, null, null);
    }

    /**
     * 通过Base32将Bitmap转换成Base64字符串
     *
     * @param bit the bitmap to encode
     * @return the encoded String
     */
    public static byte[] Bitmap2StrByBase64(Bitmap bit) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bit.compress(Bitmap.CompressFormat.JPEG, 100, bos);//参数100表示不压缩
        byte[] bytes = bos.toByteArray();
        LogUtils.e("byte = " + Arrays.toString(bytes));
        return Base64.encode(bytes, Base64.DEFAULT);
    }

}
