package com.keydom.mianren.ih_doctor.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.keydom.mianren.ih_doctor.R;
import com.keydom.mianren.ih_doctor.bean.MedicalRecordTempletBean;

import java.util.List;

/**
 * created date: 2019/1/8 on 15:18
 * des:
 * author: HJW HP
 */
public class MedicalRecordTempletAdapter extends BaseQuickAdapter<MedicalRecordTempletBean, BaseViewHolder> {
    private int[] imgs = {R.mipmap.point_blue, R.mipmap.point_black, R.mipmap.point_green, R.mipmap.point_orange,
            R.mipmap.point_purple, R.mipmap.point_purple_blue, R.mipmap.point_red, R.mipmap.point_yellow};

    public MedicalRecordTempletAdapter(@Nullable List<MedicalRecordTempletBean> data) {
        super(R.layout.item_medical_record_templet, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MedicalRecordTempletBean item) {
        helper.setText(R.id.content, item.getTemplateName() + "");
        int x = (int) (Math.random() * 8);
        helper.setImageResource(R.id.icon, imgs[x]);
    }
}
