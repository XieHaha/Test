package com.keydom.mianren.ih_patient.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.bean.CheckoutResultListBean;

import java.util.List;

/**
 * 检验适配器
 *
 * @author 顿顿
 */
public class InspectionDetailAdapter extends BaseQuickAdapter<CheckoutResultListBean,
        BaseViewHolder> {
    /**
     * 构造方法
     */
    public InspectionDetailAdapter(@Nullable List<CheckoutResultListBean> checkoutResultList) {
        super(R.layout.bodycheck_photo_item, checkoutResultList);
    }

    @Override
    protected void convert(BaseViewHolder helper, CheckoutResultListBean item) {
        helper.setText(R.id.project_name_tv, item.getItemDetailsName())
                .setText(R.id.project_result_tv, item.getResultValue())
                .setText(R.id.project_unit_tv, item.getUnit())
                .setText(R.id.project_reference_value_tv, item.getReferenceValues());
        if ("1".equals(item.getIsNormal())) {
            helper.setText(R.id.project_status_tv, "正常");
        } else if ("2".equals(item.getIsNormal())) {
            helper.setText(R.id.project_status_tv, "超出");
        } else if ("3".equals(item.getIsNormal())) {
            helper.setText(R.id.project_status_tv, "不足");
        } else {
            helper.setText(R.id.project_status_tv, "正常");
        }
    }
}
