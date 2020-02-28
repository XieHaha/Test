package com.keydom.ih_patient.view;

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

import com.keydom.ih_patient.R;


/**
 * @date 20/2/27 11:37
 * @des 儿童保健目录
 */
public class ChildCareItemLayout extends RelativeLayout {
    public MyViewHolder mViewHolder;
    private View itemChildcare;
    private Context mContext;

    public ChildCareItemLayout(Context context) {
        this(context, null);
    }

    public ChildCareItemLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public ChildCareItemLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        LayoutInflater inflater =
                (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT);
        itemChildcare = inflater.inflate(R.layout.item_childcare, null);
        this.addView(itemChildcare, layoutParams);
        mViewHolder = new MyViewHolder(this);
        TypedArray ta = mContext.obtainStyledAttributes(attrs, R.styleable.ChildCareItemLayout);
        int imageId = ta.getResourceId(R.styleable.ChildCareItemLayout_item_image, -1);
        int titleId = ta.getResourceId(R.styleable.ChildCareItemLayout_item_title, -1);

        mViewHolder.ivItem.setImageResource(imageId);
        mViewHolder.tvTitle.setText(titleId);
        ta.recycle();

    }

    public void setContent(String content) {
        mViewHolder.tvContent.setText(content);
    }

    public class MyViewHolder {
        public TextView tvTitle;
        public TextView tvContent;
        public ImageView ivItem;

        public MyViewHolder(View v) {
            tvTitle = v.findViewById(R.id.tv_title);
            tvContent = v.findViewById(R.id.tv_content);
            ivItem = v.findViewById(R.id.iv_item);
        }
    }
}
