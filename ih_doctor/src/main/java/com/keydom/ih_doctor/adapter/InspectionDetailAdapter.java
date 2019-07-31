package com.keydom.ih_doctor.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.keydom.ih_doctor.R;
import com.keydom.ih_doctor.bean.InspectionDetailInof;

import java.util.List;

public class InspectionDetailAdapter extends BaseQuickAdapter<InspectionDetailInof.CheckoutResultListBean, BaseViewHolder> {

    public InspectionDetailAdapter(@Nullable List<InspectionDetailInof.CheckoutResultListBean> checkoutResultList) {
        super(R.layout.inspection_data_item, checkoutResultList);
    }

    @Override
    protected void convert(BaseViewHolder helper, InspectionDetailInof.CheckoutResultListBean item) {
        helper.setText(R.id.project_name_tv, item.getName()!=null&&!"".equals(item.getName())?item.getName():"")
                .setText(R.id.project_result_tv,item.getResult()!=null&&!"".equals(item.getResult())?item.getResult():"")
                .setText(R.id.project_unit_tv,item.getResultUnit()!=null&&!"".equals(item.getResultUnit())?item.getResultUnit():"")
                .setText(R.id.project_reference_value_tv, item.getReferenceValue()!=null&&!"".equals(item.getReferenceValue())?item.getReferenceValue():"");
    }
}
