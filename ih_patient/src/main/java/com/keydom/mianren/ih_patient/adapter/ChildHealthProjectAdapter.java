package com.keydom.mianren.ih_patient.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.bean.ChildHealthProjectBean;

import java.util.List;

/**
 * 儿保项目
 *
 * @author 顿顿
 */
public class ChildHealthProjectAdapter extends BaseQuickAdapter<ChildHealthProjectBean,
        BaseViewHolder> {

    public ChildHealthProjectAdapter(@Nullable List<ChildHealthProjectBean> data) {
        super(R.layout.item_child_health, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ChildHealthProjectBean item) {
        helper.setText(R.id.item_child_health_age_tv,
                String.format(mContext.getString(R.string.txt_child_age), item.getAge()))
                .setText(R.id.item_child_health_project_tv, getProject(item));
    }

    private String getProject(ChildHealthProjectBean item) {
        StringBuilder builder = new StringBuilder();
        if (item.getMustFill() != null) {
            int count = item.getMustFill().size();
            for (int i = 0; i < count; i++) {
                builder.append(item.getMustFill().get(i).getName());
                if (item.getMustFill().size() - 1 != i) {
                    builder.append("、");
                }
            }
        }
        if (item.getNotMustFill() != null) {
            int count = item.getNotMustFill().size();
            for (int i = 0; i < count; i++) {
                if (i == 0 && !TextUtils.isEmpty(builder.toString())) {
                    builder.append("、");
                }
                builder.append(item.getNotMustFill().get(i).getName());
                if (item.getNotMustFill().size() - 1 != i) {
                    builder.append("、");
                }
            }
        }
        return builder.toString();
    }
}
