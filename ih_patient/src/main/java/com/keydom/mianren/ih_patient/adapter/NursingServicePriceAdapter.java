package com.keydom.mianren.ih_patient.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.bean.NursingServiceOrderInfo;

import java.util.List;

/**
 * 护理服务价格适配器
 */
public class NursingServicePriceAdapter extends BaseQuickAdapter<NursingServiceOrderInfo.NursingServiceOrderDetailBaseDtoBean.OrderDetailItemsBean, BaseViewHolder> {
    /**
     * 构造方法
     */
    public NursingServicePriceAdapter(@Nullable List<NursingServiceOrderInfo.NursingServiceOrderDetailBaseDtoBean.OrderDetailItemsBean> data) {
        super(R.layout.nursing_service_fee_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, NursingServiceOrderInfo.NursingServiceOrderDetailBaseDtoBean.OrderDetailItemsBean item) {
        if (helper.getAdapterPosition() == 0) {
            helper.setText(R.id.nursing_project_name_tv, helper.getAdapterPosition() + 1 + "、" + item.getServiceName() + "服务费")
                    .setText(R.id.nursing_project_fee_tv, "  ¥" + item.getTotalPrice() + "");
        } else {
            helper.setText(R.id.nursing_project_name_tv, helper.getAdapterPosition() + 1 + "、" + item.getServiceName() + "服务费")
                    .setText(R.id.nursing_project_fee_tv, "  ¥" + item.getTotalPrice() + "元/" + item.getFrequency() + "次");
        }

    }
}
