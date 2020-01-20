package com.keydom.ih_patient.adapter;

import android.support.annotation.Nullable;
import android.view.View;

import com.blankj.utilcode.util.StringUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.bean.PregnancyOrderDetailItem;

import java.util.List;

/**
 * created date: 2019/1/15 on 16:44
 * des:产检预约详情
 */
public class PregnancyOrderDetailAdapter extends BaseQuickAdapter<PregnancyOrderDetailItem, BaseViewHolder> {
    /**
     * 构建方法
     */
    public PregnancyOrderDetailAdapter(@Nullable List<PregnancyOrderDetailItem> data) {
        super(R.layout.item_pregnancy_order_detail, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, PregnancyOrderDetailItem item) {

        if (null != item) {
            helper.setText(R.id.pregnancy_order_detail_date_tv, StringUtils.isEmpty(item.getPrenatalDate()) ? "" : item.getPrenatalDate());
            helper.setText(R.id.pregnancy_order_detail_check_projects_tv, StringUtils.isEmpty(item.getProjectName()) ? "" : item.getProjectName());
            helper.setText(R.id.pregnancy_order_time_tv, StringUtils.isEmpty(item.getTimeInterval()) ? "" : item.getTimeInterval());


            //1：检验检查；2：产检门诊；12：检验检查和产检门诊
            switch (item.getAppointType()) {
                case 1:
                    helper.getView(R.id.pregnancy_order_detail_check_projects_root_rl).setVisibility(View.VISIBLE);
                    helper.getView(R.id.pregnancy_detail_order_diagnose_iv).setSelected(false);
                    helper.getView(R.id.pregnancy_detail_order_check_iv).setSelected(true);
                    break;
                case 2:
                    helper.getView(R.id.pregnancy_order_detail_check_projects_root_rl).setVisibility(View.GONE);
                    helper.getView(R.id.pregnancy_detail_order_diagnose_iv).setSelected(true);
                    helper.getView(R.id.pregnancy_detail_order_check_iv).setSelected(false);
                    break;
                case 12:
                    helper.getView(R.id.pregnancy_order_detail_check_projects_root_rl).setVisibility(View.VISIBLE);
                    helper.getView(R.id.pregnancy_detail_order_diagnose_iv).setSelected(true);
                    helper.getView(R.id.pregnancy_detail_order_check_iv).setSelected(true);
                    break;
                default:
                    helper.getView(R.id.pregnancy_order_detail_check_projects_root_rl).setVisibility(View.VISIBLE);
                    helper.getView(R.id.pregnancy_detail_order_diagnose_iv).setSelected(false);
                    helper.getView(R.id.pregnancy_detail_order_check_iv).setSelected(false);
                    break;
            }

        }
    }
}
