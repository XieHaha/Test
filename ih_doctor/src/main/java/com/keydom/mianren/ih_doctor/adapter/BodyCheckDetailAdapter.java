package com.keydom.mianren.ih_doctor.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.keydom.ih_common.utils.GlideUtils;
import com.keydom.mianren.ih_doctor.R;

import java.util.List;


public class BodyCheckDetailAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    private Context context;

    public BodyCheckDetailAdapter(@Nullable List<String> checkoutResultList, Context context) {
        super(R.layout.bodycheck_photo_item, checkoutResultList);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        ImageView photoImg = helper.getView(R.id.photo_img);
        if (item != null && !"".equals(item)) {
            GlideUtils.load(photoImg, com.keydom.mianren.ih_doctor.constant.Const.IMAGE_HOST + item, 0, 0, false, null);
        }

    }
}
