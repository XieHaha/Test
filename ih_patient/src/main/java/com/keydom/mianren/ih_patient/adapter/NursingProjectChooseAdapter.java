package com.keydom.mianren.ih_patient.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.blankj.utilcode.util.StringUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.keydom.ih_common.constant.Const;
import com.keydom.ih_common.utils.GlideUtils;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.bean.NursingProjectInfo;

import java.util.List;

/**
 * created date: 2018/12/25 on 11:08
 * des:护理项目选择适配器
 */
public class NursingProjectChooseAdapter extends BaseQuickAdapter<NursingProjectInfo,BaseViewHolder> {
    /**
     * 构建方法
     */
    public NursingProjectChooseAdapter(@Nullable List<NursingProjectInfo> data) {
        super(R.layout.nursing_service_item,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, NursingProjectInfo item) {
        helper.setText(R.id.service_name_tv,item.getName())
                .setText(R.id.service_desc_tv,item.getSummary())
                .setText(R.id.servie_price_tv,"¥"+item.getFee()+"元")
                .setVisible(R.id.service_dep_tv, !StringUtils.isEmpty(item.getHospitalDeptName()))
                .setText(R.id.service_dep_tv,item.getHospitalDeptName()+"")
                .addOnClickListener(R.id.service_selected_img);
        ImageView headImg=helper.getView(R.id.service_icon_img);
        GlideUtils.load(headImg, item.getIcon() == null ? "" : Const.IMAGE_HOST+item.getIcon(), 0, 0, false, null);
        ImageView selectImg=helper.getView(R.id.service_selected_img);
        selectImg.setImageResource(item.isSelect()?R.mipmap.nursing_project_select:R.mipmap.nursing_project_unselect);
    }
}
