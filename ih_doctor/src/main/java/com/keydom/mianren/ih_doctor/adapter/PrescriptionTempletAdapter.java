package com.keydom.mianren.ih_doctor.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.keydom.mianren.ih_doctor.R;
import com.keydom.mianren.ih_doctor.bean.PrescriptionTempletBean;

import java.util.List;

public class PrescriptionTempletAdapter extends BaseQuickAdapter<PrescriptionTempletBean,BaseViewHolder> {
    public PrescriptionTempletAdapter(@Nullable List<PrescriptionTempletBean> data) {
        super(R.layout.prescription_templet_item,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, PrescriptionTempletBean item) {
        helper.setText(R.id.templet_name_tv,item.getTemplateName());
    }
}
