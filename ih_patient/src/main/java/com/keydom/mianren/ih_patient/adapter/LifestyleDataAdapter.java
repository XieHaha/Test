package com.keydom.mianren.ih_patient.adapter;

import android.support.annotation.Nullable;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.bean.LifestyleDataBean;

import java.util.List;

/**
 * created date: 2019/1/15 on 16:44
 * des:生活方式数据适配器
 *
 * @author 顿顿
 */
public class LifestyleDataAdapter extends BaseQuickAdapter<LifestyleDataBean, BaseViewHolder> {
    private int lifestyleType;

    /**
     * 构建方法
     */
    public LifestyleDataAdapter(@Nullable List<LifestyleDataBean> data, int lifestyleType) {
        super(R.layout.item_lifestyle_data, data);
        this.lifestyleType = lifestyleType;
    }

    @Override
    protected void convert(BaseViewHolder helper, LifestyleDataBean item) {
        TextView title = helper.getView(R.id.item_lifestyle_data_name_tv);
        title.setText(helper.getAdapterPosition() + "");
        //        if (lifestyleType == LifestyleDataActivity.LIFESTYLE_DIET) {
        //        } else {
        //
        //        }
        if (item.isSelected()) {
            helper.setVisible(R.id.item_lifestyle_data_select_iv, true);
            title.setSelected(true);
        } else {
            helper.setVisible(R.id.item_lifestyle_data_select_iv, false);
            title.setSelected(false);
        }


    }
}
