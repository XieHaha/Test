package com.keydom.ih_common.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.keydom.ih_common.R;

public class GeneralItemView extends RelativeLayout {
    private ImageView leftImg, rightImg;
    private TextView nameTv, dspTv;
    private View redPointView;

    public GeneralItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.general_itemview_layout, this, true);
        leftImg = findViewById(R.id.left_img);
        nameTv = findViewById(R.id.item_name_tv);
        dspTv = findViewById(R.id.item_descripe_tv);
        dspTv.setTextColor(getResources().getColor(R.color.colorPrimary));
        rightImg = findViewById(R.id.right_img);
        redPointView = findViewById(R.id.item_redpoint_view);
        int titleStrResid = attrs.getAttributeResourceValue("http://schemas.android" +
                ".com/apk/res-audo/com.keydom.ih_common.view", "itemTitle", -1);
        String titleStr = attrs.getAttributeValue("http://schemas.android.com/apk/res-audo/com" +
                ".keydom.ih_common.view", "itemTitle");
        int leftImg = attrs.getAttributeResourceValue("http://schemas.android" +
                ".com/apk/res-audo/com.keydom.ih_common.view", "leftImg", -1);
        int rightImg = attrs.getAttributeResourceValue("http://schemas.android" +
                ".com/apk/res-audo/com.keydom.ih_common.view", "rightImg", -1);
        setLeftImg(leftImg);
        setRightImg(rightImg);
        setItemName(titleStr, titleStrResid);
    }

    private void setLeftImg(int ResourceId) {
        if (ResourceId != -1) {
            leftImg.setImageResource(ResourceId);
            leftImg.setVisibility(VISIBLE);
        } else {
            leftImg.setVisibility(GONE);
        }
    }

    private void setItemName(String nameStr, int titleStrResid) {
        if (titleStrResid != -1) {
            nameTv.setText(getContext().getString(titleStrResid));
        } else if (nameStr != null) {
            nameTv.setText(nameStr);
        }
    }

    public void setItemDescripe(String descripeStr) {
        dspTv.setText(descripeStr);
    }

    private void setRightImg(int ResourceId) {
        rightImg.setImageResource(ResourceId);
    }

    public void setRedpointVisiable(boolean visiable) {
        if (visiable) {
            redPointView.setVisibility(VISIBLE);
        } else {
            redPointView.setVisibility(GONE);
        }
    }
}
