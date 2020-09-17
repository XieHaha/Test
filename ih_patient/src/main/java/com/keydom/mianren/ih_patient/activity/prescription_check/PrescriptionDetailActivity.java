package com.keydom.mianren.ih_patient.activity.prescription_check;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.prescription_check.controller.PrescriptionDetailController;
import com.keydom.mianren.ih_patient.activity.prescription_check.view.PrescriptionDetailView;
import com.keydom.mianren.ih_patient.adapter.MedicineRecyclrViewAdapter;
import com.keydom.mianren.ih_patient.bean.PrescriptionDetailBean;
import com.keydom.mianren.ih_patient.bean.PrescriptionDrugBean;
import com.keydom.mianren.ih_patient.bean.PrescriptionRootBean;
import com.keydom.mianren.ih_patient.constant.Const;
import com.keydom.mianren.ih_patient.view.PrescriptionLayoutView;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * created date: 2019/1/18 on 14:31
 * des:电子处方详情页面
 *
 * @author 顿顿
 */
public class PrescriptionDetailActivity extends BaseControllerActivity<PrescriptionDetailController> implements PrescriptionDetailView {

    public static final String ID = "id";

    private PrescriptionRootBean rootBean;

    private MedicineRecyclrViewAdapter mAdapter;
    private RecyclerView recyclerView;
    /**
     * 电子处方集合
     */
    private List<PrescriptionDrugBean> dataList = new ArrayList<>();
    private TextView number, hospitalName, userName, userSex, userAge, caseNum, deptName, address
            , phoneNum, feeType, diagnose, date, doctorName, checkDoctorName,
            prescriptionTypeName, sendDoctorName;
    private LinearLayout prescription_detail_layout;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_prescription_layout;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle("电子处方单");
        prescription_detail_layout = findViewById(R.id.prescription_detail_layout);
        String id = getIntent().getStringExtra(ID);
        rootBean = (PrescriptionRootBean) getIntent().getSerializableExtra(Const.DATA);
        if (rootBean == null) {
            getController().getPrescriptionDetail(id);
        } else {
            setDetail(rootBean);
        }
    }

    @Override
    public void getDetailSuccess(PrescriptionDetailBean bean) {
        setDetail(bean);
    }

    /**
     * 处方详情显示在控件上
     */
    private void setDetail(PrescriptionDetailBean bean) {
        if (bean != null) {
            prescription_detail_layout.removeAllViews();
            for (int i = 0; i < bean.getList().size(); i++) {
                PrescriptionLayoutView prescriptionDetailView =
                        new PrescriptionLayoutView(getContext());
                prescriptionDetailView.setData(bean, i);
                prescription_detail_layout.addView(prescriptionDetailView);
            }
        }
    }

    /**
     * 处方详情显示在控件上
     */
    private void setDetail(PrescriptionRootBean bean) {
        if (bean != null) {
            prescription_detail_layout.removeAllViews();
            for (int i = 0; i < bean.getItem().size(); i++) {
                PrescriptionLayoutView prescriptionDetailView =
                        new PrescriptionLayoutView(getContext());
                prescriptionDetailView.setData(bean, i);
                prescription_detail_layout.addView(prescriptionDetailView);
            }
        }
    }

}
