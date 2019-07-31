package com.keydom.ih_common.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;

public class FloatLayout extends FrameLayout {
    private final WindowManager mWindowManager;
    private WindowManager.LayoutParams mWmParams;

    private float mTouchStartX;
    private float mTouchStartY;

    private long startTime;
    private static boolean isclick;

    private boolean mHasShown;

    public FloatLayout(Context context) {
        this(context, null);
    }

    public FloatLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        mHasShown = true;
    }

    /**
     * 将小悬浮窗的参数传入，用于更新小悬浮窗的位置。
     *
     * @param params 小悬浮窗的参数
     */
    public void setParams(WindowManager.LayoutParams params) {
        mWmParams = params;
    }

    public boolean isShow() {
        return mHasShown;
    }

    public void show() {
        if (!mHasShown) {
            mWindowManager.addView(this, mWmParams);
        }
        mHasShown = true;
    }

    public void hide() {
        if (mHasShown) {
            mWindowManager.removeViewImmediate(this);
        }
        mHasShown = false;
    }


    public void setOnClickListener(ForbidClickListener listener) {
        super.setOnClickListener(listener);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        // 获取相对屏幕的坐标，即以屏幕左上角为原点
        int x = (int) event.getRawX();
        int y = (int) event.getRawY();
        //下面的这些事件，跟图标的移动无关，为了区分开拖动和点击事件
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                isclick = false;
                startTime = System.currentTimeMillis();
                mTouchStartX = event.getX();
                mTouchStartY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                isclick = true;
                //图标移动的逻辑在这里
                float mMoveStartX = event.getX();
                float mMoveStartY = event.getY();
                // 如果移动量大于3才移动
                if (Math.abs(mTouchStartX - mMoveStartX) > 3
                        && Math.abs(mTouchStartY - mMoveStartY) > 3) {
                    // 更新浮动窗口位置参数
                    mWmParams.x = (int) (x - mTouchStartX);
                    mWmParams.y = (int) (y - mTouchStartY);
                    mWindowManager.updateViewLayout(this, mWmParams);
                    return false;
                }
                break;
            case MotionEvent.ACTION_UP:
                long endTime = System.currentTimeMillis();
                //当从点击到弹起小于半秒的时候,则判断为点击,如果超过则不响应点击事件
                isclick = (!((endTime - startTime) > 0.1 * 1000L)) & isclick;
                Log.e("Float", "" + isclick);
                return isclick;
            default:
        }
        return false;
    }

    public static abstract class ForbidClickListener implements OnClickListener {

        public abstract void forbidClick(View v);

        @Override
        public void onClick(View v) {
            if (isclick) {
                forbidClick(v);
            }
        }
    }
}