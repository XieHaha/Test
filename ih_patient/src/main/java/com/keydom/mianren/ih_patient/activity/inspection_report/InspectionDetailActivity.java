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
import com.keydom.mianren.ih_patient.constant.Const;
import com.keydom.mianren.ih_patient.constant.Type;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * 检验详情
 */
public class InspectionDetailActivity extends BaseControllerActivity<InspectionDetailController> implements InspectionDetailView {
    /**
     * 启动方法
     */
    public static void start(Context context, int type, String reportID, String checkoutName) {
        Intent intent = new Intent(context, InspectionDetailActivity.class);
        intent.putExtra(Const.TYPE, type);
        intent.putExtra("reportID", reportID);
        intent.putExtra("checkoutName", checkoutName);
        context.startActivity(intent);
    }

    private TextView nameTv;
    private TextView sexTv;
    private TextView ageTv;
    private TextView doctorNameTv;
    private TextView dateTv;
    private RecyclerView inspectionDataRv;
    private InspectionDetailAdapter inspectionDetailAdapter;
    private List<CheckoutResultListBean> dataList = new ArrayList<>();
    private String reportID, checkoutName;
    /**
     * 类型 Type.INSPECTIONTYPE 检验  Type.BODYCHECKTYPE 检查
     */
    private int type;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_inspection_detail_layout;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        type = getIntent().getIntExtra(Const.TYPE, 1);
        reportID = getIntent().getStringExtra("reportID");
        checkoutName = getIntent().getStringExtra("checkoutName");
        TextView inspectionTitleTv = findViewById(R.id.inspection_title_tv);
        if (type == Type.INSPECTIONTYPE) {
            setTitle("检验报告单结果");
            inspectionTitleTv.setText(checkoutName + "检验报告单");
        } else {
            setTitle("检查报告单结果");
            inspectionTitleTv.setText(checkoutName + "检查报告单");
        }
        nameTv = findViewById(R.id.name_tv);
        sexTv = findViewById(R.id.sex_tv);
        ageTv = findViewById(R.id.age_tv);
        doctorNameTv = findViewById(R.id.doctor_name_tv);
        dateTv = findViewById(R.id.date_tv);
        inspectionDataRv = findViewById(R.id.inspection_data_rv);
        inspectionDetailAdapter = new InspectionDetailAdapter(dataList);
        inspectionDataRv.setAdapter(inspectionDetailAdapter);

        getController().getInspectionDetail(reportID, type);
    }

    @Override
    public void getInspectionDetailSuccess(InspectionDetailBean detailBean) {
        nameTv.setText(detailBean.getPatientName() != null && !"".equals(detailBean.getPatientName()) ? detailBean.getPatientName() : "");
        sexTv.setText(CommonUtils.getPatientSex(detailBean.getSex()));
        ageTv.setText(detailBean.getAge());
        doctorNameTv.setText(detailBean.getPatientName());
        dateTv.setText(detailBean.getReportTime());
        inspectionDetailAdapter.setNewData(detailBean.getDataS());
    }

    @Override
    public void getInspectionDetailFailed(String errMsg) {
        ToastUtil.showMessage(getContext(), checkoutName + "检验报告单详情获取失败" + errMsg);
        finish();
    }
}
