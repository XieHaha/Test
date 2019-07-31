package com.keydom.ih_common.im.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

public class HackyViewPager extends ViewPager {
    public HackyViewPager(@NonNull Context context) {
        super(context);
    }

    public HackyViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptHoverEvent(MotionEvent event) {
        try {
            return super.onInterceptHoverEvent(event);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return false;
        }

    }

    public void resetHeight(final View view) {
        view.post(new Runnable() {
            @Override
            public void run() {
                int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
                int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
                view.measure(w, h);
                int height = view.getMeasuredHeight();
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) getLayoutParams();
                if (params == null) {
                    params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, height);
                } else {
                    params.height = height;
                }
                setLayoutParams(params);
            }
        });

    }
}
