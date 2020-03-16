package com.keydom.mianren.ih_patient.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.keydom.mianren.ih_patient.R;


/**
 * @date 20/2/27 11:37
 * @des 病案邮寄 - 身份认证
 */
public class MedicalAuthLayout extends RelativeLayout {
    public MyViewHolder holder;
    private View itemMedicalAuth;
    private Context mContext;

    public MedicalAuthLayout(Context context) {
        this(context, null);
    }

    public MedicalAuthLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public MedicalAuthLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        LayoutInflater inflater =
                (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT);
        itemMedicalAuth = inflater.inflate(R.layout.item_medical_auth, null);
        this.addView(itemMedicalAuth, layoutParams);
        holder = new MyViewHolder(this);

        TypedArray ta = mContext.obtainStyledAttributes(attrs, R.styleable.MedicalAuthLayout);
        int titleId = ta.getResourceId(R.styleable.MedicalAuthLayout_tvHint, -1);

        if (titleId != -1) {
            holder.tvContent.setText(titleId);
        }
        ta.recycle();
    }

    public void setContent(String content) {
        holder.tvContent.setText(content);
    }

    public void setContent(int resId) {
        holder.tvContent.setText(resId);
    }

    public void setImage(String path) {
        Glide.with(mContext).load(path).into(holder.ivItem);
    }

    public void setImage(int resId) {
        holder.ivItem.setImageResource(resId);
    }


    public class MyViewHolder {
        public TextView tvContent;
        public ImageView ivItem;

        public MyViewHolder(View v) {
            tvContent = v.findViewById(R.id.tv_hint);
            ivItem = v.findViewById(R.id.iv_bg);
        }
    }
}
