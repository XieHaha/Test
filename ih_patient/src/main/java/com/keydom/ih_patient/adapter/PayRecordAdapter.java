package com.keydom.ih_patient.adapter;

import android.support.annotation.Nullable;

import com.blankj.utilcode.util.StringUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.bean.PayRecordBean;

import java.util.List;

/**
 * created date: 2019/1/15 on 16:44
 * des:缴费记录适配器
 */
public class PayRecordAdapter extends BaseQuickAdapter<PayRecordBean,BaseViewHolder> {
    /**
     * 构建方法
     */
    public PayRecordAdapter(@Nullable List<PayRecordBean> data) {
        super(R.layout.item_pay_record,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, PayRecordBean item) {
        helper.setText(R.id.time, StringUtils.isEmpty(item.getDate())?"":item.getDate())
                .setText(R.id.name,StringUtils.isEmpty(item.getName())?"":item.getName())
                .setText(R.id.project,StringUtils.isEmpty(item.getPayRegister())?"":item.getPayRegister())
                .setText(R.id.reimbursement,item.getSocialSecurityReimbursement()+"")
                .setText(R.id.money,item.getSelfPayment()+"");
    }
}
