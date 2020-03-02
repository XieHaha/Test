package com.keydom.ih_patient.view;

import android.view.View;
import android.view.ViewTreeObserver;

/**
 * @date 2020年3月2日
 */
public class ViewPrepared {
    private boolean hasMeasured = false;

    public void asyncPrepare(final View view,
                             final OnPreDrawFinishListener onPreDrawFinishListener) {
        ViewTreeObserver vto = view.getViewTreeObserver();
        vto.addOnPreDrawListener(() -> {
            if (hasMeasured == false) {
                int height = view.getMeasuredHeight();
                int width = view.getMeasuredWidth();
                //获取到宽度和高度后，可用于计算
                if (onPreDrawFinishListener != null) {
                    onPreDrawFinishListener.onPreDrawFinish(width, height);
                }
                hasMeasured = true;
            }
            return true;
        });
    }

    public interface OnPreDrawFinishListener {
        void onPreDrawFinish(int w, int h);
    }
}
