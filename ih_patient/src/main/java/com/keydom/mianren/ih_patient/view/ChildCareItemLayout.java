package com.keydom.mianren.ih_patient.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.bean.ChildHealthProjectItemBean;

import java.util.List;


/**
 * @author 顿顿
 * @date 20/2/27 11:37
 * @des 儿童保健目录
 */
public class ChildCareItemLayout extends RelativeLayout {
    public MyViewHolder holder;
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
        holder = new MyViewHolder(this);
        TypedArray ta = mContext.obtainStyledAttributes(attrs, R.styleable.ChildCareItemLayout);
        int imageId = ta.getResourceId(R.styleable.ChildCareItemLayout_item_image, -1);
        int titleId = ta.getResourceId(R.styleable.ChildCareItemLayout_item_title, -1);

        holder.ivItem.setImageResource(imageId);
        if (titleId != -1) {
            holder.tvTitle.setText(titleId);
        }
        ta.recycle();

    }

    public void setTitle(String title) {
        holder.tvTitle.setText(title);
    }

    public void setContent(String content) {
        holder.contentLayout.setVisibility(GONE);
        holder.tvContent.setVisibility(VISIBLE);
        holder.tvContent.setText(content);
    }

    public void setContentList(List<ChildHealthProjectItemBean> data, boolean must) {
        holder.tvContent.setVisibility(GONE);
        holder.contentLayout.setVisibility(VISIBLE);
        holder.contentLayout.removeAllViews();

        if (data != null && data.size() > 0) {
            for (ChildHealthProjectItemBean bean : data) {
                View view =
                        LayoutInflater.from(mContext).inflate(R.layout.item_child_health_project,
                                null);
                ImageView ivSelect = view.findViewById(R.id.item_project_select_iv);
                TextView tvName = view.findViewById(R.id.item_project_name_tv);
                TextView tvPrice = view.findViewById(R.id.item_project_price_tv);
                ivSelect.setSelected(must);
                tvName.setText(bean.getName());
                tvPrice.setText("￥" + bean.getPrice());

                if (!must) {
                    view.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ivSelect.setSelected(!ivSelect.isSelected());
                        }
                    });
                }
                holder.contentLayout.addView(view);
            }
        }
    }

    public class MyViewHolder {
        public TextView tvTitle;
        public TextView tvContent;
        public ImageView ivItem;
        private LinearLayout contentLayout;

        public MyViewHolder(View v) {
            tvTitle = v.findViewById(R.id.tv_title);
            tvContent = v.findViewById(R.id.tv_content);
            contentLayout = v.findViewById(R.id.item_childcare_layout);
            ivItem = v.findViewById(R.id.iv_item);
        }
    }
}
