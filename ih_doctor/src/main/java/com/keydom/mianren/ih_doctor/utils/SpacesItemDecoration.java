package com.keydom.mianren.ih_doctor.utils;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
    private int space, bottom;

    public SpacesItemDecoration(int space, int bottom) {
        this.space = space;
        this.bottom = bottom;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view,
                               RecyclerView parent, RecyclerView.State state) {
        outRect.right = space;
        outRect.bottom = bottom;
    }

}
