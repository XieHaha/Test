package com.keydom.ih_common.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.keydom.ih_common.R;

public class CountItemView extends RelativeLayout {
    private TextView countTv;
    private TextView countName;
    private View redPointView;
    public CountItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.item_count_layout, this, true);
        countTv=findViewById(R.id.count_tv);
        countName=findViewById(R.id.count_name);
        redPointView=findViewById(R.id.item_redpoint_view);
        String countName = attrs.getAttributeValue("http://schemas.android.com/apk/res-audo/com.keydom.ih_common.view", "itemName");
        setCountName(countName);
    }

    private void setCountName(String name){
        if(name!=null)
        countName.setText(name);
    }


    public void setCount(String count){
        countTv.setText(count);
    }



    public void setRedpointVisiable(boolean visiable){
        if(visiable){
            redPointView.setVisibility(VISIBLE);
        }else {
            redPointView.setVisibility(GONE);
        }
    }
}
