package com.keydom.ih_common.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.DrawableRes;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.keydom.ih_common.R;

public class GlideUtils {

    @SuppressLint("CheckResult")
    public static void load(ImageView imageView, Object model, @DrawableRes int holderId, @DrawableRes int errorId, boolean isCircle, DiskCacheStrategy strategy) {
        Context context = imageView.getContext();
        if (strategy == null) {
            strategy = DiskCacheStrategy.ALL;
        }
        if (((context instanceof Activity) && !((Activity) context).isDestroyed()) || !(context instanceof Activity)) {
            RequestOptions options = new RequestOptions().priority(Priority.HIGH).diskCacheStrategy(strategy);
            if (holderId != 0) {
                options.placeholder(holderId);
            }
            if (errorId != 0) {
                options.error(errorId);
            } else {
                options.error(R.mipmap.user_icon);
            }
            if (isCircle) {
                options.circleCrop();
            }
            if (model instanceof String) {
                model = ((String) model).trim();
            }
            Glide.with(context).load(model).apply(options).into(imageView);
        }
    }

    public static void loadWithBorder(ImageView imageView, Object model) {
        Context context = imageView.getContext();
        Glide.with(context).load(model)
                .apply(new RequestOptions().error(R.mipmap.user_icon).centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL)
                        .transform(new GlideCircleWithBorder(context, 3, Color.parseColor("#ffffff"))))
                .into(imageView);
    }
}
