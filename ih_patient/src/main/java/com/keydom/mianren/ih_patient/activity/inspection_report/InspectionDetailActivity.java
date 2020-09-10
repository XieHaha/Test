package com.keydom.mianren.ih_patient.activity.inspection_report;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.constant.Const;
import com.keydom.ih_common.utils.CommonUtils;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.inspection_report.controller.InspectionDetailController;
import com.keydom.mianren.ih_patient.activity.inspection_report.view.BodyCheckDetailView;
import com.keydom.mianren.ih_patient.adapter.InspectionDetailAdapter;
import com.keydom.mianren.ih_patient.bean.CheckoutResultListBean;
import com.keydom.mianren.ih_patient.bean.InspectionDetailBean;
import com.keydom.mianren.ih_patient.bean.InspectionRecordBean;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: LiuJie
 * @Date: 2019/3/4 0004
 * @Desc: 检查报告单详情页面
 */
public class InspectionDetailActivity extends BaseControllerActivity<InspectionDetailController> implements BodyCheckDetailView {
    /**
     * 启动
     */
    public static void start(Context context, InspectionRecordBean recordBean) {
        Intent intent = new Intent(context, InspectionDetailActivity.class);
        intent.putExtra(Const.DATA, recordBean);
        context.startActivity(intent);
    }

    private TextView inspectionTitleTv, nameTv, sexTv, ageTv, doctorNameTv, dateTv, departTv,
            projectTv;
    private RecyclerView inspectionDataRv;
    private InspectionDetailAdapter inspectionDetailAdapter;
    private List<CheckoutResultListBean> dataList = new ArrayList<>();
    private InspectionRecordBean recordBean;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_bodycheck_detail_layout;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle("检验报告详情");
        recordBean = (InspectionRecordBean) getIntent().getSerializableExtra(Const.DATA);

        inspectionTitleTv = findViewById(R.id.inspection_title_tv);
        nameTv = findViewById(R.id.name_tv);
        sexTv = findViewById(R.id.sex_tv);
        ageTv = findViewById(R.id.age_tv);
        doctorNameTv = findViewById(R.id.doctor_name_tv);
        dateTv = findViewById(R.id.date_tv);
        departTv = findViewById(R.id.depart_tv);
        projectTv = findViewById(R.id.project_tv);
        inspectionDataRv = findViewById(R.id.inspection_data_rv);
        inspectionDetailAdapter = new InspectionDetailAdapter(dataList);
        inspectionDataRv.setNestedScrollingEnabled(false);
        inspectionDataRv.setAdapter(inspectionDetailAdapter);

        getController().getInspectionDetail(recordBean.getReportID());
    }

    @Override
    public void getBodyCheckDetailSuccess(InspectionDetailBean detailBean) {
        nameTv.setText(detailBean.getPatientName() != null && !"".equals(detailBean.getPatientName()) ? detailBean.getPatientName() : "");
        sexTv.setText(CommonUtils.getPatientSex(detailBean.getSex()));
        ageTv.setText(recordBean.getAge());
        doctorNameTv.setText(detailBean.getPatientName());
        dateTv.setText(detailBean.getReportTime());
        departTv.setText(detailBean.getApplicationDepartment());
        projectTv.setText(recordBean.getItemName());
        inspectionTitleTv.setText(detailBean.getReportTitle());
        inspectionDetailAdapter.setNewData(detailBean.getDataS());
    }

    @Override
    public void getBodyCheckDetailFailed(String errMsg) {
        ToastUtil.showMessage(getContext(), "检验报告单详情获取失败" + errMsg);
        finish();
    }
}
