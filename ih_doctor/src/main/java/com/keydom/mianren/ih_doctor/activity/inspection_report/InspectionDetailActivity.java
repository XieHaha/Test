package com.keydom.mianren.ih_doctor.activity.inspection_report;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.utils.CommonUtils;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.mianren.ih_doctor.R;
import com.keydom.mianren.ih_doctor.activity.inspection_report.controller.InspectionDetailController;
import com.keydom.mianren.ih_doctor.activity.inspection_report.view.InspectionDetailView;
import com.keydom.mianren.ih_doctor.adapter.InspectionDetailAdapter;
import com.keydom.mianren.ih_doctor.bean.InspectionDetailInof;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * @link Author: song
 * <p>
 * Create: 19/3/6 下午3:48
 * <p>
 * Changes (from 19/3/6)
 * <p>
 * 19/3/6 : Create InspectionDetailActivity.java (song);
 */
public class InspectionDetailActivity extends BaseControllerActivity<InspectionDetailController> implements InspectionDetailView {


    private TextView inspection_title_tv, name_tv, sex_tv, age_tv, doctor_name_tv, date_tv;
    private RecyclerView inspection_data_rv;

    /**
     * 检验报告适配器
     */
    private InspectionDetailAdapter inspectionDetailAdapter;
    /**
     * 检验报告列表
     */
    private List<InspectionDetailInof.CheckoutResultListBean> dataList = new ArrayList<>();
    /**
     * 检验报告单号
     */
    private String applyNumber;

    /**
     * 启动检验报告单详情页面
     *
     * @param context
     * @param applyNumber 检验报告单ID
     */
    public static void start(Context context, String applyNumber) {
        Intent intent = new Intent(context, InspectionDetailActivity.class);
        intent.putExtra("applyNumber", applyNumber);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_inspection_detail_layout;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle("检验报告单结果");
        inspection_title_tv = findViewById(R.id.inspection_title_tv);
        name_tv = findViewById(R.id.name_tv);
        sex_tv = findViewById(R.id.sex_tv);
        age_tv = findViewById(R.id.age_tv);
        doctor_name_tv = findViewById(R.id.doctor_name_tv);
        date_tv = findViewById(R.id.date_tv);
        inspection_data_rv = findViewById(R.id.inspection_data_rv);
        inspectionDetailAdapter = new InspectionDetailAdapter(dataList);
        inspection_data_rv.setAdapter(inspectionDetailAdapter);
        applyNumber = getIntent().getStringExtra("applyNumber");
        inspection_title_tv.setText("检验报告单");
        getController().getInspectionDetail(applyNumber);
    }

    @Override
    public void getInspectionDetailSuccess(InspectionDetailInof inspectionDetailInof) {
        name_tv.setText(inspectionDetailInof.getPatientName() != null && !"".equals(inspectionDetailInof.getPatientName()) ? inspectionDetailInof.getPatientName() : "");
        sex_tv.setText(CommonUtils.getPatientSex(inspectionDetailInof.getSex()));
        age_tv.setText(inspectionDetailInof.getAge() != null && !"".equals(inspectionDetailInof.getAge()) ? inspectionDetailInof.getAge() : "");
        doctor_name_tv.setText(inspectionDetailInof.getReporter() != null && !"".equals(inspectionDetailInof.getReporter()) ? inspectionDetailInof.getReporter() : "");
        date_tv.setText(inspectionDetailInof.getResultTime() != null && !"".equals(inspectionDetailInof.getResultTime()) ? inspectionDetailInof.getResultTime() : "");
        inspectionDetailAdapter.setNewData(inspectionDetailInof.getCheckoutResultList());
    }

    @Override
    public void getInspectionDetailFailed(String errMsg) {
        ToastUtil.showMessage(getContext(), "检验报告单详情获取失败" + errMsg);
        finish();
    }
}
