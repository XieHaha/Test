package com.keydom.mianren.ih_patient.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.keydom.mianren.ih_patient.R;

import java.util.List;

/**
 * 住院预缴金适配器
 *
 * @author 顿顿
 */
public class HospitalPaymentAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public HospitalPaymentAdapter(@Nullable List<String> data) {
        super(R.layout.item_hospital_payment, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String teamItem) {
        helper.setText(R.id.tv_deposit, "医院押金")
                .setText(R.id.tv_amount, "1000元")
                .setText(R.id.tv_pay_way, "微信")
                .setText(R.id.tv_time, "2020年2月26日");
    }
}
