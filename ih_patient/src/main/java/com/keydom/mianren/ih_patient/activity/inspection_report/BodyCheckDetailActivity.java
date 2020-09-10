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
import com.keydom.mianren.ih_patient.activity.inspection_report.controller.BodyCheckDetailController;
import com.keydom.mianren.ih_patient.activity.inspection_report.view.InspectionDetailView;
import com.keydom.mianren.ih_patient.adapter.BodyCheckDetailAdapter;
import com.keydom.mianren.ih_patient.bean.CheckoutResultListBean;
import com.keydom.mianren.ih_patient.bean.InspectionDetailBean;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * 检查详情
 *
 * @author 顿顿
 */
public class BodyCheckDetailActivity extends BaseControllerActivity<BodyCheckDetailController> implements InspectionDetailView {
    /**
     * 启动方法
     */
    public static void start(Context context, String reportID) {
        Intent intent = new Intent(context, BodyCheckDetailActivity.class);
        intent.putExtra("reportID", reportID);
        context.startActivity(intent);
    }

    private TextView nameTv;
    private TextView sexTv;
    private TextView ageTv;
    private TextView doctorNameTv;
    private TextView dateTv,numberTv;
    private TextView inspectionTitleTv;
    private TextView departNameTv;
    private RecyclerView inspectionDataRv;
    private BodyCheckDetailAdapter bodyCheckDetailAdapter;
    private List<CheckoutResultListBean> dataList = new ArrayList<>();
    private String reportID;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_inspection_detail_layout;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle("检查报告详情");
        reportID = getIntent().getStringExtra("reportID");
        inspectionTitleTv = findViewById(R.id.inspection_title_tv);
        nameTv = findViewById(R.id.name_tv);
        sexTv = findViewById(R.id.sex_tv);
        ageTv = findViewById(R.id.age_tv);
        doctorNameTv = findViewById(R.id.doctor_name_tv);
        dateTv = findViewById(R.id.date_tv);
        numberTv = findViewById(R.id.number_tv);
        departNameTv = findViewById(R.id.depart_name_tv);
        inspectionDataRv = findViewById(R.id.inspection_data_rv);
        bodyCheckDetailAdapter = new BodyCheckDetailAdapter(dataList);
        inspectionDataRv.setAdapter(bodyCheckDetailAdapter);

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
        numberTv.setText(reportID);
        departNameTv.setText(detailBean.getApplicationDepartment());
        bodyCheckDetailAdapter.setNewData(detailBean.getDataS());
    }

    @Override
    public void getInspectionDetailFailed(String errMsg) {
        ToastUtil.showMessage(getContext(), "报告单详情获取失败" + errMsg);
        finish();
    }
}
