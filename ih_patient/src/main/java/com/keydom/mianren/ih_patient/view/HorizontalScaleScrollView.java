package com.keydom.mianren.ih_patient.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;

import com.keydom.mianren.ih_patient.R;

/**
 * 水平滚动刻度尺
 *
 * @author 顿顿
 */
public class HorizontalScaleScrollView extends BaseScaleView {

    public HorizontalScaleScrollView(Context context) {
        super(context);
    }

    public HorizontalScaleScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HorizontalScaleScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public HorizontalScaleScrollView(Context context, AttributeSet attrs, int defStyleAttr,
                                     int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void initVar() {
        mRectWidth = (mMax - mMin) * mScaleMargin;
        mRectHeight = mScaleHeight * 8;
        mScaleMaxHeight = mScaleHeight * 2;

        // 设置layoutParams
        ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(mRectWidth, mRectHeight);
        this.setLayoutParams(lp);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int height = MeasureSpec.makeMeasureSpec(mRectHeight, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, height);
        mScaleScrollViewRange = getMeasuredWidth();
        mTempScale = mScaleScrollViewRange / mScaleMargin / 2 + mMin;
        mMidCountScale = mScaleScrollViewRange / mScaleMargin / 2 + mMin;
    }

    @Override
    protected void onDrawLine(Canvas canvas, Paint paint) {
        canvas.drawLine(0, mRectHeight, mRectWidth, mRectHeight, paint);
    }

    @Override
    protected void onDrawScale(Canvas canvas, Paint paint) {
        paint.setStrokeWidth(3);
        paint.setColor(ContextCompat.getColor(getContext(), R.color.stroke_color));
        for (int i = 0; i <= mMax - mMin; i++) {
            canvas.drawLine(i * mScaleMargin, mRectHeight, i * mScaleMargin,
                    mRectHeight - mScaleHeight, paint);
        }
    }

    @Override
    protected void onDrawPointer(Canvas canvas, Paint paint) {

        paint.setColor(ContextCompat.getColor(getContext(), R.color.color_57a7fc));

        //每一屏幕刻度的个数/2
        int countScale = mScaleScrollViewRange / mScaleMargin / 2;
        //根据滑动的距离，计算指针的位置【指针始终位于屏幕中间】
        int finalX = mScroller.getFinalX();
        //滑动的刻度 //四舍五入取整
        int tmpCountScale = (int) Math.rint((double) finalX / (double) mScaleMargin);
        //总刻度
        mCountScale = tmpCountScale + countScale + mMin;
        //回调方法
        if (mScrollListener != null) {
            mScrollListener.onScaleScroll(mCountScale);
        }
        paint.setStrokeWidth(9);
        canvas.drawLine(countScale * mScaleMargin + finalX, mRectHeight,
                countScale * mScaleMargin + finalX,
                mRectHeight - mScaleMaxHeight - mScaleHeight + 20, paint);
    }

    @Override
    public void scrollToScale(int val) {
        if (val < mMin || val > mMax) {
            return;
        }
        int dx = (val - mCountScale) * mScaleMargin;
        smoothScrollBy(dx, 0);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (mScroller != null && !mScroller.isFinished()) {
                    mScroller.abortAnimation();
                }
                mScrollLastX = x;
                return true;
            case MotionEvent.ACTION_MOVE:
                int dataX = mScrollLastX - x;
                //向右边滑动
                if (mCountScale - mTempScale < 0) {
                    //禁止继续向右滑动
                    if (mCountScale <= mMin && dataX <= 0) {
                        return super.onTouchEvent(event);
                    }
                    //向左边滑动
                } else if (mCountScale - mTempScale > 0) {
                    //禁止继续向左滑动
                    if (mCountScale >= mMax && dataX >= 0) {
                        return super.onTouchEvent(event);
                    }
                }
                smoothScrollBy(dataX, 0);
                mScrollLastX = x;
                postInvalidate();
                mTempScale = mCountScale;
                return true;
            case MotionEvent.ACTION_UP:
                if (mCountScale < mMin) {
                    mCountScale = mMin;
                }
                if (mCountScale > mMax) {
                    mCountScale = mMax;
                }
                int finalX = (mCountScale - mMidCountScale) * mScaleMargin;
                //纠正指针位置
                mScroller.setFinalX(finalX);
                postInvalidate();
                return true;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }

}
