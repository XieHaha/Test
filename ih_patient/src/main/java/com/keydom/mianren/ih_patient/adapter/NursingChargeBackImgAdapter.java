package com.keydom.mianren.ih_patient.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.blankj.utilcode.util.ConvertUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.keydom.ih_common.constant.Const;
import com.keydom.mianren.ih_patient.R;

import java.util.List;

/**
 * created date: 2018/12/19 on 18:10
 * des:护理图片适配器
 */
public class NursingChargeBackImgAdapter extends BaseQuickAdapter<String,BaseViewHolder> {
    /**
     * 构建方法
     */
    public NursingChargeBackImgAdapter(@Nullable List<String> data) {
        super(R.layout.item_nursing_charge_back_img,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        RoundedCorners roundedCorners= new RoundedCorners(ConvertUtils.dp2px(mContext.getResources().getDimension(R.dimen.dp_5)));
        RequestOptions options=RequestOptions.bitmapTransform(roundedCorners);
        Glide.with(mContext).load(Const.IMAGE_HOST+item).apply(options).into((ImageView) helper.getView(R.id.img));
    }
}
