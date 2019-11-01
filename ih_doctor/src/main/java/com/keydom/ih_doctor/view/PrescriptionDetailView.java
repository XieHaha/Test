package com.keydom.ih_doctor.view;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.keydom.ih_common.utils.CommonUtils;
import com.keydom.ih_common.utils.GlideUtils;
import com.keydom.ih_doctor.MyApplication;
import com.keydom.ih_doctor.R;
import com.keydom.ih_doctor.adapter.MedicineRecyclrViewAdapter;
import com.keydom.ih_doctor.bean.PrescriptionDetailBean;
import com.keydom.ih_doctor.bean.PrescriptionDrugBean;
import com.keydom.ih_doctor.constant.Const;

import java.util.ArrayList;
import java.util.List;

public class PrescriptionDetailView extends RelativeLayout {

    private RecyclerView recyclerView;
    private ImageView doctorSign;
    private TextView number, hospitalName, userName, userSex, userAge, caseNum, deptName, address, phoneNum, feeType, diagnose, date, doctorName, checkDoctorName, prescriptionTypeName, sendDoctorName,prescriptionNumTv;
    private MedicineRecyclrViewAdapter mAdapter;
    private List<PrescriptionDrugBean> dataList = new ArrayList<>();
    private int position=0;
    public PrescriptionDetailView(Context context) {
        this(context,null);
    }

    public PrescriptionDetailView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public PrescriptionDetailView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.prescription_detail_view_layout, this);
        prescriptionNumTv=this.findViewById(R.id.prescription_num_tv);
        hospitalName = (TextView) this.findViewById(R.id.hospital_name);
        doctorSign = (ImageView) this.findViewById(R.id.doctor_sign);
        userName = (TextView) this.findViewById(R.id.user_name);
        userSex = (TextView) this.findViewById(R.id.user_sex);
        userAge = (TextView) this.findViewById(R.id.user_age);
        caseNum = (TextView) this.findViewById(R.id.case_num);
        checkDoctorName = (TextView) this.findViewById(R.id.check_doctor_name);
        sendDoctorName = (TextView) this.findViewById(R.id.send_doctor_name);
        prescriptionTypeName = (TextView) this.findViewById(R.id.prescription_type_name);
        deptName = (TextView) this.findViewById(R.id.dept_name);
        address = (TextView) this.findViewById(R.id.address);
        phoneNum = (TextView) this.findViewById(R.id.phone_num);
        feeType = (TextView) this.findViewById(R.id.fee_type);
        diagnose = (TextView) this.findViewById(R.id.diagnose);
        number = (TextView) this.findViewById(R.id.number);
        date = (TextView) this.findViewById(R.id.date);
        doctorName = (TextView) this.findViewById(R.id.doctor_name);
        recyclerView = (RecyclerView) findViewById(R.id.medicine_rv);
        mAdapter = new MedicineRecyclrViewAdapter(getContext(), dataList);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setNestedScrollingEnabled(false);
    }

    public void  setData(PrescriptionDetailBean bean,int position){
        this.position=position;
        prescriptionNumTv.setText("处方"+CommonUtils.numberToChinese(position+1));
        hospitalName.setText(MyApplication.userInfo.getHospitalName() + "处方笺");
        userName.setText(bean.getName());
        userSex.setText(CommonUtils.getSex(bean.getSex()));
        userAge.setText(bean.getAge());
        caseNum.setText(bean.getOutpatientNumber());
        deptName.setText(bean.getDept());
        address.setText(bean.getAddress());
        phoneNum.setText(bean.getPhoneNumber());
        feeType.setText("自费");
        diagnose.setText(bean.getDiagnosis());
        date.setText(bean.getTime());
        doctorName.setText(bean.getDoctorName());
        number.setText(bean.getSerialNumber());
        prescriptionTypeName.setText(bean.getCate() == 1 ? "普通药品处方" : "儿科药品处方");
        checkDoctorName.setText(bean.getAuditer());
        sendDoctorName.setText(bean.getChecker());
        dataList.addAll(bean.getList().get(position));
        mAdapter.notifyDataSetChanged();
        GlideUtils.load(doctorSign, Const.IMAGE_HOST + bean.getCommonSeal(), 0, 0, false, null);
    }

}
