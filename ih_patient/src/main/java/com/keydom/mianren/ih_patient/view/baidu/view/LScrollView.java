package com.keydom.mianren.ih_patient.view.baidu.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.view.View;

import com.keydom.mianren.ih_patient.view.baidu.ChildLinkageEvent;
import com.keydom.mianren.ih_patient.view.baidu.ILinkageScroll;
import com.keydom.mianren.ih_patient.view.baidu.LinkageScrollHandler;

/**
 * 置于联动容器中的ScrollView，业务方需要继承此ScrollView
 *
 * @author zhanghao43
 * @since 2019/04/16
 */
public class LScrollView extends NestedScrollView implements ILinkageScroll {

    /** 联动滚动事件 */
    private ChildLinkageEvent mLinkageChildrenEvent;

    public LScrollView(Context context) {
        this(context, null);
    }

    public LScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);

        // 禁止滚动条
        setVerticalScrollBarEnabled(false);

        // 滚动监听，将必要事件传递给联动容器
        if (!canScrollVertically(-1)) {
            if (mLinkageChildrenEvent != null) {
                mLinkageChildrenEvent.onContentScrollToTop(this);
            }
        }

        if (!canScrollVertically(1)) {
            if (mLinkageChildrenEvent != null) {
                mLinkageChildrenEvent.onContentScrollToBottom(this);
            }
        }

        if (mLinkageChildrenEvent != null) {
            mLinkageChildrenEvent.onContentScroll(this);
        }
    }

    @Override
    public void setChildLinkageEvent(ChildLinkageEvent event) {
        mLinkageChildrenEvent = event;

        if (mLinkageChildrenEvent != null) {
            mLinkageChildrenEvent.onContentScroll(this);
        }
    }

    @Override
    public LinkageScrollHandler provideScrollHandler() {
        return new LinkageScrollHandler() {

            @Override
            public void flingContent(View target, int velocityY) {
                LScrollView.this.fling(velocityY);
            }

            @Override
            public void scrollContentToTop() {
                LScrollView.this.scrollTo(0, 0);
            }

            @Override
            public void scrollContentToBottom() {
                LScrollView.this.scrollTo(0, getVerticalScrollRange());
            }

            @Override
            public void stopContentScroll(View target) {
                LScrollView.this.fling(0);
            }

            @Override
            public boolean canScrollVertically(int direction) {
                return LScrollView.this.canScrollVertically(direction);
            }

            @Override
            public boolean isScrollable() {
                return true;
            }

            @SuppressLint("RestrictedApi")
            @Override
            public int getVerticalScrollExtent() {
                return computeVerticalScrollExtent();
            }

            @SuppressLint("RestrictedApi")
            @Override
            public int getVerticalScrollOffset() {
                return computeVerticalScrollOffset();
            }

            @SuppressLint("RestrictedApi")
            @Override
            public int getVerticalScrollRange() {
                return computeVerticalScrollRange();
            }
        };
    }
}
