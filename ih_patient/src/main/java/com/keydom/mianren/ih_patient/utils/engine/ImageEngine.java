/*
package com.keydom.mianren.ih_patient.utils.engine;

import android.content.Context;
import android.widget.ImageView;

import com.keydom.mianren.ih_patient.R;
import com.nostra13.universalimageloader.cache.disc.DiskCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;


*/
/**
 * 图片载入引擎（目前使用nostra13图片加载库）
 *
 * @author Xun.Zhang
 * @version 1.1
 *//*

public class ImageEngine {

    */
/**
     * 初始化方法（在Application中调用一次就可以，其他时候不调用）
     *
     * @param context
     *//*

    public static void init(Context context) {
        ImageLoaderUtil.initImageLoader(context);
    }

    */
/**
     * 显示图片
     *
     * @param url
     * @param imageView
     *//*

    public static void display(String url, ImageView imageView) {
        ImageLoader.getInstance().displayImage(url, imageView);
    }


    */
/**
     * 显示图片
     *
     * @param url
     * @param imageView
     * @param defaultResId 默认占位图
     * @param isRound      是否显示为圆形
     *//*

    public static void display(String url, ImageView imageView, int defaultResId, boolean... isRound) {
        DisplayImageOptions displayImageOptions;
        if (null != isRound && isRound.length > 0 && isRound[0]) {
            displayImageOptions = ImageLoaderUtil.getRoundDisplayImageOptions(defaultResId);
        } else {
            displayImageOptions = ImageLoaderUtil.getDisplayImageOptions(defaultResId);
        }
        ImageLoader.getInstance().displayImage(url, imageView, displayImageOptions);
    }

    */
/**
     * 显示图片
     *
     * @param url
     * @param imageView
     * @param isRound   是否显示为圆形
     *//*

    public static void display(String url, ImageView imageView, boolean isRound) {
        DisplayImageOptions displayImageOptions;
        if (isRound) {
            displayImageOptions = ImageLoaderUtil.getRoundDisplayImageOptions(R.drawable.bg_default_photo_round);
        } else {
            displayImageOptions = ImageLoaderUtil.getDisplayImageOptions(R.drawable.bg_default_photo);
        }
        ImageLoader.getInstance().displayImage(url, imageView, displayImageOptions);
    }

    */
/**
     * 显示图片
     *
     * @param url
     * @param imageView
     *//*

    public static void displayWithNoCache(String url, ImageView imageView, int defaultResId) {
        ImageLoader.getInstance().displayImage(url, imageView, ImageLoaderUtil.getNoCacheDisplayImageOptions(defaultResId));
    }

    public static void clearDiskCache() {
        ImageLoader.getInstance().clearDiskCache();
    }

    public static String getDiskCacheFolder() {
        DiskCache diskCache = ImageLoader.getInstance().getDiskCache();
        if (null != diskCache && null != diskCache.getDirectory()) {
            return diskCache.getDirectory().getAbsolutePath();
        }
        return null;
    }

}
*/
