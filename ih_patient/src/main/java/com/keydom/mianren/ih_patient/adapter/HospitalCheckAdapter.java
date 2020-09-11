package com.keydom.mianren.ih_patient.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.bean.HospitalCheckBean;

import java.util.List;

/**
 * 住院清单
 *
 * @author 顿顿
 */
public class HospitalCheckAdapter extends BaseQuickAdapter<HospitalCheckBean, BaseViewHolder> {

    public HospitalCheckAdapter(@Nullable List<HospitalCheckBean> data) {
        super(R.layout.item_hospital_check, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HospitalCheckBean item) {
        helper.setText(R.id.hospital_check_name_tv, item.getExpenseTypeName())
                .setText(R.id.hospital_check_price_tv, item.getFee());
    }
}
