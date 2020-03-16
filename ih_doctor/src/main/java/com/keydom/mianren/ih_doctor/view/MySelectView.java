package com.keydom.mianren.ih_doctor.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.keydom.mianren.ih_doctor.R;

import java.util.Stack;

public class MySelectView extends RelativeLayout {
    private TextView nameTip, nameTv;
    private MySelectView tagBox;
    private Stack<View> mStack = new Stack<>();

    public MySelectView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.select_view, this, true);
        nameTip = findViewById(R.id.name_tip);
        nameTv = findViewById(R.id.name);
        tagBox = findViewById(R.id.tag_box);
        String titleStr = attrs.getAttributeValue("http://schemas.android.com/apk/res-audo/com.keydom.mianren.ih_doctor.view", "itemtip");
        String hintStr = attrs.getAttributeValue("http://schemas.android.com/apk/res-audo/com.keydom.mianren.ih_doctor.view", "itemHint");
        int leftImg = attrs.getAttributeResourceValue("http://schemas.android.com/apk/res-audo/com.keydom.mianren.ih_doctor.view", "itemLeftImg", -1);
        setItemName(titleStr);
        Drawable img = context.getResources().getDrawable(leftImg);
        img.setBounds(0, 0, img.getMinimumWidth(), img.getMinimumHeight());
        setItemTip(hintStr, img);
    }

    private void setItemName(String value) {
        nameTv.setText(value);
    }

    private void setItemTip(String value, Drawable left) {
        nameTip.setText(value);

        nameTip.setCompoundDrawables(left, null, null, null);
    }

    public void setRightClickListener(OnClickListener listener) {
        nameTv.setOnClickListener(listener);
    }

    public void addTag(View view) {
        mStack.push(view);
        tagBox.addView(view);
    }

    public void removeTag(View view) {
        mStack.remove(view);
        tagBox.removeView(view);
    }


}
