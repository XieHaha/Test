package com.keydom.mianren.ih_patient.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.bean.HospitalRecordBean;

import java.util.List;

/**
 * 住院预缴金适配器
 *
 * @author 顿顿
 */
public class HospitalPaymentAdapter extends BaseQuickAdapter<HospitalRecordBean, BaseViewHolder> {

    public HospitalPaymentAdapter(@Nullable List<HospitalRecordBean> data) {
        super(R.layout.item_hospital_payment, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HospitalRecordBean item) {
        helper.setText(R.id.tv_deposit, "住院预缴")
                .setText(R.id.tv_amount, item.getPayMoney())
                .setText(R.id.tv_pay_way, getType(item.getChargeType()))
                .setText(R.id.tv_time, item.getHisRechargeDate());
    }

    private String getType(String type) {
        switch (type) {
            case "0":
                return "现金";
            case "1":
                return "微信";
            case "2":
                return "VIP余额";
            case "3":
                return "银行卡";
            default:
                return "其他";
        }
    }
}
