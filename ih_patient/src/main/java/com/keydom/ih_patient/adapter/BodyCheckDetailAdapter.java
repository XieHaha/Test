package com.keydom.ih_patient.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.keydom.ih_common.constant.Const;
import com.keydom.ih_common.utils.CommonUtils;
import com.keydom.ih_patient.R;

import java.util.List;

/**
 * 检查适配器
 */
public class BodyCheckDetailAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    private Context context;

    /**
     * 构造方法
     */
    public BodyCheckDetailAdapter(@Nullable List<String> checkoutResultList,Context context) {
        super(R.layout.bodycheck_photo_item, checkoutResultList);
        this.context=context;
    }
    @Override
    protected void convert(BaseViewHolder helper, String item) {
        ImageView photoImg = helper.getView(R.id.photo_img);
        if(item!=null&&!"".equals(item)){
            Glide.with(context).load(Const.IMAGE_HOST+item).into(photoImg);
        }
        photoImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                CommonUtils.previewImage(context, Const.IMAGE_HOST+item);
                CommonUtils.previewImageList(context,getData(),helper.getPosition(),true);
            }
        });

    }
}
