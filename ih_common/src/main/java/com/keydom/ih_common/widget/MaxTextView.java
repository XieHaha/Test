package com.keydom.ih_common.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

public class MaxTextView extends TextView {
    public MaxTextView(Context context) {
        super(context);
    }

    public MaxTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MaxTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        String mLoadContent = MaxTextView.this.getText().toString().trim();
        if(mLoadContent.length()>100){
            mLoadContent=mLoadContent.substring(0,100)+"...";
            MaxTextView.this.setText(mLoadContent);
        }
    }
}
