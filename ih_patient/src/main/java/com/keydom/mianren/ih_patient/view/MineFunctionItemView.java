package com.keydom.mianren.ih_patient.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.keydom.mianren.ih_patient.R;


/**
 * 个人中心功能控件
 */
public class MineFunctionItemView extends RelativeLayout {
    private ImageView iconImg;
    private TextView nameTv;
    private View redPointView;

    /**
     * 构建方法
     */
    public MineFunctionItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.mine_function_item_bg, this, true);
        iconImg=findViewById(R.id.function_icon);
        nameTv=findViewById(R.id.funciton_name);
        redPointView=findViewById(R.id.item_redpoint_view);
        String titleStr = attrs.getAttributeValue("http://schemas.android.com/apk/res-audo/com.keydom.mianren.ih_patient.view", "itemTitle");
        int titleStrResid = attrs.getAttributeResourceValue("http://schemas.android.com/apk/res-audo/com.keydom.mianren.ih_patient.view", "itemTitle",-1);
        int iconImg = attrs.getAttributeResourceValue("http://schemas.android.com/apk/res-audo/com.keydom.mianren.ih_patient.view", "iconImg",-1);
        setIconImg(iconImg);
        setItemName(titleStrResid,titleStr);

    }

    /**
     * 设置图片
     */
    public void setIconImg(int ResourceId){
        iconImg.setImageResource(ResourceId);
    }

    /**
     * 设置名字
     */
    private void setItemName(int titleStrResid, String nameStr){
        if(titleStrResid!=-1){
            nameTv.setText(getContext().getString(titleStrResid));
        }else if(nameStr!=null){
            nameTv.setText(nameStr);
        }
    }

    /**
     * 设置是否可见
     */
    public void setRedpointVisiable(boolean visiable){
        if(visiable){
            redPointView.setVisibility(VISIBLE);
        }else {
            redPointView.setVisibility(GONE);
        }
    }
}
