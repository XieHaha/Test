package com.keydom.mianren.ih_patient.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.keydom.mianren.ih_patient.R;


/**
 * 公共标题
 */
public class GeneralTitle extends RelativeLayout {
    private ImageView leftImg;
    private TextView nameTv,rightTv;
    private TitleChildClickLisener titleChildClickLisener;

    /**
     * 构建方法
     */
    public GeneralTitle(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.general_title_layout,this,true);
        leftImg=findViewById(R.id.title_left_img);
        nameTv=findViewById(R.id.title_name_tv);
        rightTv=findViewById(R.id.title_right_tv);

        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.generaltitle);
        if(attributes!=null){
            if(attributes.getBoolean(R.styleable.generaltitle_isLeftImgVisiable,false)){
                leftImg.setImageResource(attributes.getInt(R.styleable.generaltitle_leftImgRec,R.mipmap.back));
                leftImg.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        titleChildClickLisener.OnLeftClick();
                    }
                });
            }
            if(attributes.getBoolean(R.styleable.generaltitle_isRightTvVisiable,false)){

                String rightTvStr=attributes.getString(R.styleable.generaltitle_rightString);
                if(!TextUtils.isEmpty(rightTvStr)){
                    rightTv.setText(rightTvStr);
                }
                Drawable top = getResources().getDrawable(attributes.getResourceId(R.styleable.generaltitle_rightDrawableTop,0));
                rightTv.setCompoundDrawablesWithIntrinsicBounds(null, top , null, null);
                rightTv.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        titleChildClickLisener.OnRightClick();
                    }
                });
            }else {
                rightTv.setVisibility(GONE);
            }

            String titlenameStr=attributes.getString(R.styleable.generaltitle_titleName);
            if(!TextUtils.isEmpty(titlenameStr)){
                nameTv.setText(titlenameStr);
            }
            attributes.recycle();
        }
    }

    /**
     * 点击监听
     */
    public interface TitleChildClickLisener{
        void OnLeftClick();
        void OnRightClick();
    }

    /**
     * 设置监听
     */
    public void registerTitleClickLisener(TitleChildClickLisener titleChildClickLisener){
        this.titleChildClickLisener=titleChildClickLisener;
    }
}
