package com.keydom.mianren.ih_patient.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.keydom.mianren.ih_patient.R;

import java.util.List;

/**
 * 儿童保健
 */
public class ChildHealthAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public ChildHealthAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String teamItem) {
        helper.setText(R.id.tv_stage, "三周")
                .setText(R.id.tv_date, "2020年2月27日")
                .setText(R.id.tv_one, "体格检查")
                .setText(R.id.tv_two, "身高、体重检查")
                .setText(R.id.tv_three, "抽血检查");
        ImageView imageView = helper.getView(R.id.iv_select);
        imageView.setSelected(true);
    }
}
