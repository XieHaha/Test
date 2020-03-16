package com.keydom.mianren.ih_doctor.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.keydom.ih_common.constant.Const;
import com.keydom.ih_common.utils.GlideUtils;
import com.keydom.mianren.ih_doctor.R;
import com.keydom.mianren.ih_doctor.bean.NursingProjectInfo;

import java.util.List;

/**
 * created date: 2018/12/25 on 11:08
 * des:
 * author: HJW HP
 */
public class NursingProjectChooseAdapter extends BaseQuickAdapter<NursingProjectInfo, BaseViewHolder> {
    public NursingProjectChooseAdapter(@Nullable List<NursingProjectInfo> data) {
        super(R.layout.nursing_service_choose_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, NursingProjectInfo item) {
        helper.setText(R.id.service_name_tv, item.getName())
                .setText(R.id.service_desc_tv, item.getSummary())
                .setText(R.id.servie_price_tv, "¥" + item.getFee() + "元")
                .addOnClickListener(R.id.nursing_item);
        ImageView headImg = helper.getView(R.id.service_icon_img);
        GlideUtils.load(headImg, item.getIcon() == null ? "" : Const.IMAGE_HOST + item.getIcon(), 0, 0, false, null);
        ImageView selectImg = helper.getView(R.id.service_selected_img);
        selectImg.setImageResource(item.isSelect() ? R.mipmap.nursing_project_select : R.mipmap.nursing_project_unselect);
    }
}
