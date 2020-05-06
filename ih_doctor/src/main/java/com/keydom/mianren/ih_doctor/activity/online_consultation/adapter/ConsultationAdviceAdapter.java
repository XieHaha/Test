package com.keydom.mianren.ih_doctor.activity.online_consultation.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.keydom.mianren.ih_doctor.R;
import com.keydom.mianren.ih_doctor.bean.ConsultationAdviceBean;

import java.util.List;

public class ConsultationAdviceAdapter extends BaseQuickAdapter<ConsultationAdviceBean,
        BaseViewHolder> {
    private Context context;

    public ConsultationAdviceAdapter(Context context, List<ConsultationAdviceBean> data) {
        super(R.layout.item_consultation_advice, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, final ConsultationAdviceBean item) {
        helper.setText(R.id.consultation_advice_name, item.getDoctorName())
                .setText(R.id.consultation_advice_depart, item.getDeptName())
                .setText(R.id.consultation_advice_date, item.getCreateTime())
                .setText(R.id.consultation_advice_content, item.getContent());
    }
}
