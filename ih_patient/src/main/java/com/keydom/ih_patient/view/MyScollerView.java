package com.keydom.ih_patient.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

public class MyScollerView extends ScrollView {

    private ScollerViewInterface scrollViewListener = null;
    public MyScollerView(Context context) {
        super(context);
    }

    public MyScollerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyScollerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setScrollViewListener(ScollerViewInterface scrollViewListener) {
        this.scrollViewListener = scrollViewListener;
    }

    @Override
    protected void onScrollChanged(int x, int y, int oldx, int oldy) {
        super.onScrollChanged(x, y, oldx, oldy);
        if (scrollViewListener != null) {
            scrollViewListener.onScrollChanged(this, x, y, oldx, oldy);
        }
    }
}
