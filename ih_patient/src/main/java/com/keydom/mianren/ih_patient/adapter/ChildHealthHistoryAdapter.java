package com.keydom.mianren.ih_patient.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.bean.ChildHealthDoingBean;

import java.util.List;

/**
 * 儿保项目
 *
 * @author 顿顿
 */
public class ChildHealthHistoryAdapter extends BaseQuickAdapter<ChildHealthDoingBean,
        BaseViewHolder> {

    public ChildHealthHistoryAdapter(@Nullable List<ChildHealthDoingBean> data) {
        super(R.layout.item_child_health, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ChildHealthDoingBean item) {
        helper.setText(R.id.item_child_health_age_tv,
                String.format(mContext.getString(R.string.txt_child_age), item.getAge()))
                .setText(R.id.item_child_health_project_tv, getProject(item));
    }

    private String getProject(ChildHealthDoingBean item) {
        StringBuilder builder = new StringBuilder();
        if (item.getSelect() != null) {
            int count = item.getSelect().size();
            for (int i = 0; i < count; i++) {
                builder.append(item.getSelect().get(i).getName());
                if (item.getSelect().size() - 1 != i) {
                    builder.append("、");
                }
            }
        }
        return builder.toString();
    }
}
