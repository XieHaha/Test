package com.keydom.mianren.ih_doctor.view;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.util.AttributeSet;

/**
 * @Name：com.keydom.ih_doctor.view
 * @Description：描述信息
 * @Author：song
 * @Date：19/3/28 上午11:28
 * 修改人：xusong
 * 修改时间：19/3/28 上午11:28
 */
public class PatientRvManager extends LinearLayoutManager {
    private boolean isScrollEnabled = true;

    public PatientRvManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public PatientRvManager(Context context) {
        super(context);
    }

//    public PatientRvManager(Context context, int spanCount) {
//        super(context, spanCount);
//    }
//
//    public PatientRvManager(Context context, int spanCount, int orientation, boolean reverseLayout) {
//        super(context, spanCount, orientation, reverseLayout);
//    }

    public void setScrollEnabled(boolean flag) {
        this.isScrollEnabled = flag;
    }

    @Override
    public boolean canScrollVertically() {
        return isScrollEnabled && super.canScrollVertically();
    }

}
