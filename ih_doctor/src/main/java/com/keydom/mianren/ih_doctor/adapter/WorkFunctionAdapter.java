package com.keydom.mianren.ih_doctor.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.keydom.mianren.ih_doctor.R;
import com.keydom.mianren.ih_doctor.bean.IndexMenuBean;
import com.keydom.mianren.ih_doctor.utils.GetMenuIconResId;

import java.util.List;

public class WorkFunctionAdapter extends BaseQuickAdapter<IndexMenuBean, BaseViewHolder> {
    private Context context;
    private int itemWidth=0;
    public WorkFunctionAdapter(Context context, @Nullable List<IndexMenuBean> data, int itemWidth) {
        super(R.layout.work_function_item, data);
        this.context=context;
        this.itemWidth=itemWidth;
    }

    @Override
    protected void convert(BaseViewHolder helper, IndexMenuBean item) {
       ViewGroup.LayoutParams layoutParams= helper.itemView.getLayoutParams();
       layoutParams.width=itemWidth;
       helper.itemView.setLayoutParams(layoutParams);
        ImageView usericon=helper.getView(R.id.work_function_icon);
        View redPointView =helper.getView(R.id.item_redpoint_view);
        helper.setText(R.id.work_function_name,item.getName());
        if (item.isRedPointShow())
           redPointView.setVisibility(View.VISIBLE);
        else
           redPointView.setVisibility(View.GONE);

        int resId = GetMenuIconResId.getInstance().getId(item.getName());
        if (resId == -1) {
            usericon.setImageDrawable(context.getResources().getDrawable(R.mipmap.nurse_visit));
        } else {
            usericon.setImageDrawable(context.getResources().getDrawable(resId));
        }
    }
}
