package com.keydom.mianren.ih_patient.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.keydom.ih_common.utils.BaseFileUtils;
import com.keydom.ih_common.utils.GlideUtils;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.bean.HealthConsultantBean;

import java.util.List;

/**
 * 咨询师
 *
 * @author 顿顿
 */
public class HealthConsultantAdapter extends BaseQuickAdapter<HealthConsultantBean,
        BaseViewHolder> {

    public HealthConsultantAdapter(@Nullable List<HealthConsultantBean> data) {
        super(R.layout.iten_health_consultant, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HealthConsultantBean item) {
        helper.setText(R.id.health_consultant_name_tv, item.getName())
                .setText(R.id.health_consultant_title_tv, item.getJobTitleName())
                .setText(R.id.health_consultant_depart_tv, item.getDeptName())
                .setText(R.id.health_consultant_good_tv, item.getAdept())
                .addOnClickListener(R.id.health_consultant_contact_tv);
        TextView contactTv = helper.getView(R.id.health_consultant_contact_tv);
        TextView historyTv = helper.getView(R.id.health_consultant_history_tv);
        if (item.getIsConsult() == 0) {
            historyTv.setVisibility(View.INVISIBLE);
            contactTv.setSelected(false);
            contactTv.setText("健康咨询");
        } else {
            historyTv.setVisibility(View.VISIBLE);
            contactTv.setSelected(true);
            contactTv.setText("继续咨询");
        }
        ImageView headerIv = helper.getView(R.id.health_consultant_header_iv);
        GlideUtils.load(headerIv, BaseFileUtils.getHeaderUrl(item.getImage()), -1,
                R.mipmap.user_icon, true, null);
    }

}
