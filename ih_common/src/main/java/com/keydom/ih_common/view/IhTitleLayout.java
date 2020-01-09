package com.keydom.ih_common.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.keydom.ih_common.R;

/**
 * @Name：com.keydom.ih_common.view
 * @Description：描述信息
 * @Author：song
 * @Date：18/11/12 下午12:59
 * 修改人：xusong
 * 修改时间：18/11/12 下午12:59
 */
public class IhTitleLayout extends LinearLayout {

    private OnLeftButtonClickListener onLeftButtonClickListener;
    private OnRightTextClickListener onRightTextClickListener;
    private OnRightTextClickListener onDoubleFrstRightTextClickListener;
    private OnRightTextClickListener onDoubleSecRightTextClickListener;
    private OnRightImgClickListener onRightImgClickListener;
    public MyViewHolder mViewHolder;
    private View viewAppTitle;
    private Context mContext;

    public IhTitleLayout(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public IhTitleLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public IhTitleLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        init();
    }

    private void init() {
        LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        viewAppTitle = inflater.inflate(R.layout.ih_title_bar, null);
        this.addView(viewAppTitle, layoutParams);

        mViewHolder = new MyViewHolder(this);
        mViewHolder.llLeftGoBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onLeftButtonClickListener != null) {
                    onLeftButtonClickListener.onLeftButtonClick(v);
                }
            }
        });
        mViewHolder.llRightComplete.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onRightTextClickListener != null) {
                    onRightTextClickListener.OnRightTextClick(v);
                } else if (onRightImgClickListener != null) {
                    onRightImgClickListener.OnRightImgClick(v);
                }
            }
        });

        mViewHolder.doubleFirstLl.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onDoubleFrstRightTextClickListener != null) {
                    onDoubleFrstRightTextClickListener.OnRightTextClick(v);
                }
            }
        });

        mViewHolder.doubleSecondaryLl.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onDoubleSecRightTextClickListener != null) {
                    onDoubleSecRightTextClickListener.OnRightTextClick(v);
                }
            }
        });

    }

    public void showRightDoubleBtn() {
        mViewHolder.llRightComplete.setVisibility(GONE);
        mViewHolder.doubleBtnLl.setVisibility(VISIBLE);
    }

    public void setDoubleRightListener(OnRightTextClickListener frstListener, OnRightTextClickListener secListener) {
        onDoubleFrstRightTextClickListener = frstListener;
        onDoubleSecRightTextClickListener = secListener;
    }

    /**
     * @param isLeftButtonVisile    是否显示左侧按钮
     * @param isCenterTitleVisile   是否显示标题
     * @param isRightCompleteVisile 是否显示右侧文字按钮
     */
    public void initViewsVisible(boolean isLeftButtonVisile, boolean isCenterTitleVisile, boolean isRightCompleteVisile) {
        // 左侧返回
        mViewHolder.llLeftGoBack.setVisibility(isLeftButtonVisile ? View.VISIBLE : View.INVISIBLE);

        // 中间标题
        mViewHolder.tvCenterTitle.setVisibility(isCenterTitleVisile ? View.VISIBLE : View.INVISIBLE);

        //右侧文字
        mViewHolder.llRightComplete.setVisibility(isRightCompleteVisile ? View.VISIBLE : View.INVISIBLE);
    }

    public void hideRightLl(boolean show) {
        mViewHolder.llRightComplete.setVisibility(show ? VISIBLE : INVISIBLE);
    }

    public void hideLeftLl(boolean show) {
        mViewHolder.llLeftGoBack.setVisibility(show ? VISIBLE : INVISIBLE);
    }

    /**
     * 设置标题
     *
     * @param title
     */
    public void setAppTitle(String title) {
        if (!TextUtils.isEmpty(title)) {
            mViewHolder.tvCenterTitle.setText(title);
        }
    }

    /**
     * 设置标题颜色
     *
     * @param color
     */
    public void setAppTitleColor(int color) {
            mViewHolder.tvCenterTitle.setTextColor(color);
    }



    public void setRightTitle(String text) {
        if (!TextUtils.isEmpty(text)) {
            mViewHolder.llRightComplete.setVisibility(VISIBLE);
            mViewHolder.tvRightComplete.setText(text);
            mViewHolder.ivRightComplete.setVisibility(View.GONE);
            mViewHolder.tvRightComplete.setVisibility(View.VISIBLE);
        }

    }

    public void setRightImg(Drawable drawable) {
        if (drawable != null) {
            mViewHolder.llRightComplete.setVisibility(VISIBLE);
            mViewHolder.ivRightComplete.setBackgroundDrawable(drawable);
            mViewHolder.ivRightComplete.setVisibility(View.VISIBLE);
            mViewHolder.tvRightComplete.setVisibility(View.GONE);
        }

    }

    public void setWhiteBar() {
        mViewHolder.tvCenterTitle.setTextColor(mContext.getResources().getColor(R.color.white));
        mViewHolder.goBackIv.setColorFilter(Color.parseColor("#FFFFFF"));
    }


    public void setAppBackground(int color) {
        viewAppTitle.setBackgroundColor(color);
    }

    public void setOnLeftButtonClickListener(OnLeftButtonClickListener listen) {
        onLeftButtonClickListener = listen;
    }

    public void setOnRightTextClickListener(OnRightTextClickListener listen) {
        onRightTextClickListener = listen;
    }

    public void setOnRightImgClickListener(OnRightImgClickListener listen) {
        onRightImgClickListener = listen;
    }

    //左侧按钮监听接口
    public static abstract interface OnLeftButtonClickListener {
        public abstract void onLeftButtonClick(View v);
    }

    //右侧第一个图片按钮监听接口
    public static abstract interface OnRightImgClickListener {
        public abstract void OnRightImgClick(View v);
    }

    //右侧文字监听接口
    public static abstract interface OnRightTextClickListener {
        public abstract void OnRightTextClick(View v);
    }

    //搜索按钮监听接口
    public static abstract interface OnSearchButtonClickListener {
        public abstract void OnSearchButtonClick(View v);
    }

    public class MyViewHolder {
        public LinearLayout llLeftGoBack;//左侧按钮
        public LinearLayout llRightComplete;//右侧布局
        public TextView tvCenterTitle;//标题
        public TextView tvRightComplete;//右侧文字
        public ImageView ivRightComplete;//右侧文字
        public ImageView goBackIv;
        public LinearLayout doubleFirstLl;
        public LinearLayout doubleSecondaryLl;
        public LinearLayout doubleBtnLl;

        public MyViewHolder(View v) {
            llLeftGoBack = v.findViewById(R.id.llLeftGoBack);
            llRightComplete = v.findViewById(R.id.llRightComplete);
            tvCenterTitle = v.findViewById(R.id.tvCenterTitle);
            tvRightComplete = v.findViewById(R.id.tvRightComplete);
            ivRightComplete = v.findViewById(R.id.ivRightComplete);
            goBackIv = v.findViewById(R.id.go_back_iv);
            doubleFirstLl = v.findViewById(R.id.llFirstRight);
            doubleSecondaryLl = v.findViewById(R.id.llSecondaryRight);
            doubleBtnLl = v.findViewById(R.id.double_btn_ll);
        }
    }
}
