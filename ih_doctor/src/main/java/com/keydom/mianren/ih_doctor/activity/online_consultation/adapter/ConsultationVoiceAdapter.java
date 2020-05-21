package com.keydom.mianren.ih_doctor.activity.online_consultation.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.keydom.mianren.ih_doctor.R;
import com.keydom.mianren.ih_doctor.bean.VoiceBean;

import java.util.List;

public class ConsultationVoiceAdapter extends BaseQuickAdapter<VoiceBean,
        BaseViewHolder> {
    private Context context;

    public ConsultationVoiceAdapter(Context context, List<VoiceBean> data) {
        super(R.layout.item_consultation_voice, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, final VoiceBean item) {
    }
}
