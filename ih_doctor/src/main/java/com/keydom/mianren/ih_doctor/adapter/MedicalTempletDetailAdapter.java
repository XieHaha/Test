package com.keydom.mianren.ih_doctor.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.keydom.mianren.ih_doctor.R;
import com.keydom.mianren.ih_doctor.bean.MedicalTempletDetailBean;

import java.util.List;

/**
 * created date: 2019/1/8 on 16:07
 * des:
 * author: HJW HP
 */
public class MedicalTempletDetailAdapter extends BaseQuickAdapter<MedicalTempletDetailBean,BaseViewHolder> {
    private int[] imgs = {R.mipmap.point_blue,R.mipmap.point_black,R.mipmap.point_green,R.mipmap.point_orange,
            R.mipmap.point_purple,R.mipmap.point_purple_blue,R.mipmap.point_red,R.mipmap.point_yellow};

    public MedicalTempletDetailAdapter(@Nullable List<MedicalTempletDetailBean> data) {
        super(R.layout.item_medical_templet_detail,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MedicalTempletDetailBean item) {
        helper.setText(R.id.title,item.getTitle()+"")
                .setText(R.id.content,item.getContent()+"");
        int x=(int)(Math.random()*8);
        helper.setImageResource(R.id.icon,imgs[x]);
    }
}
