package com.keydom.mianren.ih_doctor.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.keydom.ih_common.bean.TriageBean;
import com.keydom.mianren.ih_doctor.R;

import java.util.ArrayList;

/**
 * @date 20/3/21 14:05
 * @des 分诊
 */
public class TriageOrderAdapter extends BaseQuickAdapter<TriageBean, BaseViewHolder> {

    private int status;

    public TriageOrderAdapter(@Nullable ArrayList<TriageBean> data) {
        super(R.layout.item_triage_order, data);
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    protected void convert(BaseViewHolder helper, TriageBean item) {
        helper.setText(R.id.triage_order_list_date_tv, item.getTriageTime())
                .setText(R.id.triage_order_list_status_tv, status == 0 ? "待接收" : "已接收")
                .setText(R.id.triage_order_list_patient_info_tv,
                        String.format(mContext.getString(R.string.txt_three_value),
                                item.getPatientName(), item.getPatientSex(),
                                item.getPatientAge() + "岁"))
                .setText(R.id.triage_order_list_description_tv, item.getTriageExplain())
                .setText(R.id.triage_order_list_apply_info_tv,
                        String.format(mContext.getString(R.string.txt_three_value),
                                item.getDept(), item.getDoctor(), item.getJobTitle()))
                .setText(R.id.triage_order_list_receive_info_tv,
                        String.format(mContext.getString(R.string.txt_three_value),
                                item.getChangeInfoDept(), item.getChangeInfoDoctor(),
                                item.getChangeInfoJobTitle()));
    }
}
