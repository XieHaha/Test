package com.keydom.ih_doctor.adapter;

import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_doctor.R;
import com.keydom.ih_doctor.activity.inspection_report.BodyCheckDetailActivity;
import com.keydom.ih_doctor.activity.inspection_report.InspectionDetailActivity;
import com.keydom.ih_doctor.activity.online_diagnose.ReportListActivity;
import com.keydom.ih_doctor.bean.BodyCheckDetailInfo;
import com.keydom.ih_doctor.bean.ReportItemBean;
import com.keydom.ih_doctor.m_interface.SingleClick;
import com.keydom.ih_doctor.net.DiagnoseApiService;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ReportListAdapter extends BaseQuickAdapter<ReportItemBean, BaseViewHolder> {
    private int mType;

    public ReportListAdapter(@Nullable List<ReportItemBean> data, int type) {
        super(R.layout.report_item_layout, data);
        mType = type;
    }

    @Override
    protected void convert(BaseViewHolder helper, ReportItemBean item) {

        helper.setText(R.id.report_name, item.getName());
        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @SingleClick(1000)
            @Override
            public void onClick(View v) {
                if (mType == ReportListActivity.REPORT_INSPECT) {
                    getBodyCheckDetail(String.valueOf(item.getId()));
                } else if (mType == ReportListActivity.REPORT_CHECK_OUT) {
                    InspectionDetailActivity.start(mContext, String.valueOf(item.getId()));
                }
            }
        });

    }

    /**
     * 获取检查申请详情
     *
     * @param applyNumber 检查报告单号
     */
    public void getBodyCheckDetail(String applyNumber) {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(DiagnoseApiService.class).getInspectResultInfo(applyNumber), new HttpSubscriber<BodyCheckDetailInfo>(mContext) {
            @Override
            public void requestComplete(@org.jetbrains.annotations.Nullable BodyCheckDetailInfo data) {
                BodyCheckDetailActivity.start(mContext, String.valueOf(applyNumber));
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                ToastUtil.showMessage(mContext,"无法打开该检查单"+msg);
                return super.requestError(exception, code, msg);
            }
        });
    }

}
