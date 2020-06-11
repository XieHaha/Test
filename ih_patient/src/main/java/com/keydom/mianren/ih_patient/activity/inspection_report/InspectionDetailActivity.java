package com.keydom.mianren.ih_patient.activity.inspection_report;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.utils.CommonUtils;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.inspection_report.controller.InspectionDetailController;
import com.keydom.mianren.ih_patient.activity.inspection_report.view.InspectionDetailView;
import com.keydom.mianren.ih_patient.adapter.InspectionDetailAdapter;
import com.keydom.mianren.ih_patient.bean.CheckoutResultListBean;
import com.keydom.mianren.ih_patient.bean.InspectionDetailBean;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * 检查详情
 */
public class InspectionDetailActivity extends BaseControllerActivity<InspectionDetailController> implements InspectionDetailView {
    /**
     * 启动方法
     */
    public static void start(Context context, String reportID, String checkoutName) {
        Intent intent = new Intent(context, InspectionDetailActivity.class);
        intent.putExtra("reportID", reportID);
        intent.putExtra("checkoutName", checkoutName);
        context.startActivity(intent);
    }

    private TextView nameTv;
    private TextView sexTv;
    private TextView ageTv;
    private TextView doctorNameTv;
    private TextView dateTv;
    private TextView inspectionTitleTv;
    private TextView departNameTv;
    private RecyclerView inspectionDataRv;
    private InspectionDetailAdapter inspectionDetailAdapter;
    private List<CheckoutResultListBean> dataList = new ArrayList<>();
    private String reportID, checkoutName;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_inspection_detail_layout;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle("检验检查报告");
        reportID = getIntent().getStringExtra("reportID");
        checkoutName = getIntent().getStringExtra("checkoutName");
        inspectionTitleTv = findViewById(R.id.inspection_title_tv);
        nameTv = findViewById(R.id.name_tv);
        sexTv = findViewById(R.id.sex_tv);
        ageTv = findViewById(R.id.age_tv);
        doctorNameTv = findViewById(R.id.doctor_name_tv);
        dateTv = findViewById(R.id.date_tv);
        departNameTv = findViewById(R.id.depart_name_tv);
        inspectionDataRv = findViewById(R.id.inspection_data_rv);
        inspectionDetailAdapter = new InspectionDetailAdapter(dataList);
        inspectionDataRv.setAdapter(inspectionDetailAdapter);

        getController().getInspectionDetail(reportID);
    }

    @Override
    public void getInspectionDetailSuccess(InspectionDetailBean detailBean) {
        inspectionTitleTv.setText(detailBean.getReportTitle());
        nameTv.setText(detailBean.getPatientName());
        sexTv.setText(CommonUtils.getPatientSex(detailBean.getSex()));
        ageTv.setText(detailBean.getAge());
        doctorNameTv.setText(detailBean.getPatientName());
        dateTv.setText(detailBean.getReportTime());
        departNameTv.setText(detailBean.getApplicationDepartment());
        inspectionDetailAdapter.setNewData(detailBean.getDataS());
    }

    @Override
    public void getInspectionDetailFailed(String errMsg) {
        ToastUtil.showMessage(getContext(), checkoutName + "报告单详情获取失败" + errMsg);
        finish();
    }
}
