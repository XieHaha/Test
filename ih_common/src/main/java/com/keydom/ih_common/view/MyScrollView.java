package com.keydom.ih_common.view;

import android.content.Context;
import android.util.AttributeSet;

/**
 * @Name：com.keydom.ih_doctor.view
 * @Description：描述信息
 * @Author：song
 * @Date：18/11/7 下午9:57
 * 修改人：xusong
 * 修改时间：18/11/7 下午9:57
 */
public class MyScrollView extends AnimationScrollView {

    private OnScrollListener onScrollListener;

    public MyScrollView(Context context) {
        super(context);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (onScrollListener != null) {
            onScrollListener.onScroll(t);
        }
    }

    /**
     * 接口对外公开
     * @param onScrollListener
     */
    public void setOnScrollListener(OnScrollListener onScrollListener) {
        this.onScrollListener = onScrollListener;
    }

    public interface OnScrollListener{
        void onScroll(int scrollY);
    }
}