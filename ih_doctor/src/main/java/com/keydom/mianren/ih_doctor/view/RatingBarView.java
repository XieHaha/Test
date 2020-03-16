package com.keydom.mianren.ih_doctor.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.keydom.mianren.ih_doctor.R;

/**
 * created date: 2018/12/26 on 19:58
 * des: 星级选择评论框
 */

public class RatingBarView extends LinearLayout {
    /**
     * 选择评分回调
     */
    public interface OnRatingListener {
        void onRating(Object bindObject, int RatingScore);
    }

    /**
     * 是否可以点击
     */
    private boolean mClickable = true;
    private OnRatingListener onRatingListener;
    private Object bindObject;
    private float starImageSize;
    private int starCount;
    private Drawable starEmptyDrawable;
    private Drawable starFillDrawable;
    private int mStarCount;

    /**
     * 设置是否可点击
     */
    public void setClickable(boolean clickable) {
        this.mClickable = clickable;
    }

    /**
     * 构建方法
     */
    public RatingBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOrientation(LinearLayout.HORIZONTAL);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.RatingBarView);
        starImageSize = ta.getDimension(R.styleable.RatingBarView_starImageSize, 20);
        starCount = ta.getInteger(R.styleable.RatingBarView_starCount, 5);
        starEmptyDrawable = ta.getDrawable(R.styleable.RatingBarView_starEmpty);
        starFillDrawable = ta.getDrawable(R.styleable.RatingBarView_starFill);
        ta.recycle();

        for (int i = 0; i < starCount; ++i) {
            ImageView imageView = getStarImageView(context, attrs);
            imageView.setOnClickListener(v -> {
                if (mClickable) {
                    mStarCount = indexOfChild(v) + 1;
                    setStar(mStarCount);
                    if (onRatingListener != null) {
                        onRatingListener.onRating(bindObject, mStarCount);
                    }
                }
            });
            addView(imageView);
        }
    }

    /**
     * 设置评分图片
     */
    private ImageView getStarImageView(Context context, AttributeSet attrs) {
        ImageView imageView = new ImageView(context);
        ViewGroup.LayoutParams para = new ViewGroup.LayoutParams(Math.round(starImageSize), Math.round(starImageSize));
        imageView.setLayoutParams(para);
        // 添加星星中间的距离，由于是padding所以设置星星大小时需要把这个算进去
        imageView.setPadding(10, 0, 10, 0);
//        imageView.setPadding(0, 0, 0, 0);
        imageView.setImageDrawable(starEmptyDrawable);
        imageView.setMaxWidth(10);
        imageView.setMaxHeight(10);
        return imageView;
    }

    /**
     * 设置星级
     */
    public void setStar(int starCount) {
        starCount = starCount > this.starCount ? this.starCount : starCount;
        starCount = starCount < 0 ? 0 : starCount;
        for (int i = 0; i < starCount; ++i) {
            ((ImageView) getChildAt(i)).setImageDrawable(starFillDrawable);
        }
        for (int i = this.starCount - 1; i >= starCount; --i) {
            ((ImageView) getChildAt(i)).setImageDrawable(starEmptyDrawable);
        }
    }

    /**
     * 获取星级
     */
    public int getStarCount() {
        return mStarCount;
    }

    /**
     * 设置选中图片
     */
    public void setStarFillDrawable(Drawable starFillDrawable) {
        this.starFillDrawable = starFillDrawable;
    }

    /**
     * 设置未选中图标
     */
    public void setStarEmptyDrawable(Drawable starEmptyDrawable) {
        this.starEmptyDrawable = starEmptyDrawable;
    }

    /**
     * 设置星级
     */
    public void setStarCount(int startCount) {
        this.starCount = startCount;
    }

    /**
     * 设置图片大小
     */
    public void setStarImageSize(float starImageSize) {
        this.starImageSize = starImageSize;
    }

    public void setBindObject(Object bindObject) {
        this.bindObject = bindObject;
    }

    /**
     * 这个回调，可以获取到用户评价给出的星星等级
     */
    public void setOnRatingListener(OnRatingListener onRatingListener) {
        this.onRatingListener = onRatingListener;
    }
}

