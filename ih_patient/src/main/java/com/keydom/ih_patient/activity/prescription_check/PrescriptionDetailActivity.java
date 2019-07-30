package com.keydom.ih_patient.activity.prescription_check;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.utils.CommonUtils;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.prescription_check.controller.PrescriptionDetailController;
import com.keydom.ih_patient.activity.prescription_check.view.PrescriptionDetailView;
import com.keydom.ih_patient.adapter.MedicineRecyclrViewAdapter;
import com.keydom.ih_patient.bean.PrescriptionDetailBean;
import com.keydom.ih_patient.bean.PrescriptionDrugBean;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * created date: 2019/1/18 on 14:31
 * des:电子处方详情页面
 */
public class PrescriptionDetailActivity extends BaseControllerActivity<PrescriptionDetailController> implements PrescriptionDetailView {

    public static final String ID = "id";

    private MedicineRecyclrViewAdapter mAdapter;
    private RecyclerView recyclerView;
    /**
     * 电子处方集合
     */
    private List<PrescriptionDrugBean> dataList = new ArrayList<>();
    private TextView number, hospitalName, userName, userSex, userAge, caseNum, deptName, address, phoneNum, feeType, diagnose, date, doctorName,checkDoctorName, prescriptionTypeName, sendDoctorName;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_prescription_layout;
    }

    /**
     * 查找控件
     */
    private void getView() {
        hospitalName = (TextView) this.findViewById(R.id.hospital_name);
        userName = (TextView) this.findViewById(R.id.user_name);
        userSex = (TextView) this.findViewById(R.id.user_sex);
        userAge = (TextView) this.findViewById(R.id.user_age);
        caseNum = (TextView) this.findViewById(R.id.case_num);
        deptName = (TextView) this.findViewById(R.id.dept_name);
        address = (TextView) this.findViewById(R.id.address);
        phoneNum = (TextView) this.findViewById(R.id.phone_num);
        feeType = (TextView) this.findViewById(R.id.fee_type);
        diagnose = (TextView) this.findViewById(R.id.diagnose);
        number = (TextView) this.findViewById(R.id.number);
        date = (TextView) this.findViewById(R.id.date);
        checkDoctorName = (TextView) this.findViewById(R.id.check_doctor_name);
        sendDoctorName = (TextView) this.findViewById(R.id.send_doctor_name);
        prescriptionTypeName = (TextView) this.findViewById(R.id.prescription_type_name);
        doctorName = (TextView) this.findViewById(R.id.doctor_name);
        recyclerView = (RecyclerView) findViewById(R.id.medicine_rv);
        mAdapter = new MedicineRecyclrViewAdapter(getContext(), dataList);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setNestedScrollingEnabled(false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle("电子处方单");
        getView();
        String id = getIntent().getStringExtra(ID);
        getController().getPrescriptionDetail(id);
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
            hospitalName.setText(bean.getHospitalName()+"处方笺");
            userName.setText(bean.getName());
            userSex.setText(CommonUtils.getSex(bean.getSex()));
            userAge.setText(bean.getAge());
            caseNum.setText(bean.getOutpatientNumber());
            deptName.setText(bean.getDept());
            address.setText(bean.getAddress());
            phoneNum.setText(bean.getPhoneNumber());
            feeType.setText(bean.getFee());
            diagnose.setText(bean.getDiagnosis());
            date.setText(bean.getTime());
            doctorName.setText(bean.getDoctorName());
            number.setText(bean.getSerialNumber());
            prescriptionTypeName.setText(bean.getCate() == 1 ? "普通药品处方" : "儿科药品处方");
            checkDoctorName.setText(bean.getAuditer());
            sendDoctorName.setText(bean.getChecker());
            dataList.addAll(bean.getList());
            mAdapter.notifyDataSetChanged();
        }
    }

}
