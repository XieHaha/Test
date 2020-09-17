package com.keydom.mianren.ih_patient.view;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.keydom.ih_common.constant.Const;
import com.keydom.ih_common.utils.CommonUtils;
import com.keydom.ih_common.utils.GlideUtils;
import com.keydom.mianren.ih_patient.App;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.adapter.MedicineRecordAdapter;
import com.keydom.mianren.ih_patient.adapter.MedicineRecyclrViewAdapter;
import com.keydom.mianren.ih_patient.bean.PrescriptionDetailBean;
import com.keydom.mianren.ih_patient.bean.PrescriptionDrugBean;
import com.keydom.mianren.ih_patient.bean.PrescriptionRecordBean;
import com.keydom.mianren.ih_patient.bean.PrescriptionRecordDrugBean;
import com.keydom.mianren.ih_patient.bean.PrescriptionRootBean;
import com.keydom.mianren.ih_patient.utils.DateUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 顿顿
 */
public class PrescriptionLayoutView extends RelativeLayout {

    private RecyclerView recyclerView, recordRecycler;
    private ImageView doctorSign;
    private TextView number, hospitalName, userName, userSex, userAge, caseNum, deptName, address
            , phoneNum, feeType, diagnose, date, doctorName, checkDoctorName,
            prescriptionTypeName, sendDoctorName, prescriptionNumTv;
    private MedicineRecyclrViewAdapter mAdapter;
    private MedicineRecordAdapter recordAdapter;
    private List<PrescriptionDrugBean> dataList = new ArrayList<>();
    private List<PrescriptionRecordDrugBean> recordList = new ArrayList<>();
    private int position = 0;

    public PrescriptionLayoutView(Context context) {
        this(context, null);
    }

    public PrescriptionLayoutView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PrescriptionLayoutView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.prescription_detail_view_layout, this);
        prescriptionNumTv = this.findViewById(R.id.prescription_num_tv);
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
        //
        recyclerView = (RecyclerView) findViewById(R.id.medicine_rv);
        mAdapter = new MedicineRecyclrViewAdapter(getContext(), dataList);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setNestedScrollingEnabled(false);

        //处方记录
        recordRecycler = (RecyclerView) findViewById(R.id.record_recycler);
        recordAdapter = new MedicineRecordAdapter(getContext(), recordList);
        recordRecycler.setAdapter(recordAdapter);
        recordRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        recordRecycler.setNestedScrollingEnabled(false);
    }

    public void setData(PrescriptionDetailBean bean, int position) {
        this.position = position;
        prescriptionNumTv.setText("处方" + CommonUtils.numberToChinese(position + 1));
        hospitalName.setText(App.hospitalName + "处方笺");
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
        dataList.addAll(bean.getList().get(position));
        mAdapter.notifyDataSetChanged();
        recyclerView.setVisibility(VISIBLE);
        recordRecycler.setVisibility(GONE);
        GlideUtils.load(doctorSign, Const.IMAGE_HOST + bean.getCommonSeal(), 0, 0, false, null);
    }

    public void setData(PrescriptionRootBean bean, PrescriptionRecordBean recordBean) {
        prescriptionNumTv.setText("处方1");
        hospitalName.setText(App.hospitalName + "处方笺");
        userName.setText(bean.getPatientName());
        userSex.setText(bean.getSex());
        userAge.setText(bean.getAge());
        caseNum.setText(recordBean.getInquiryNo());
        deptName.setText(recordBean.getDeptName());
        address.setText(bean.getAddress());
        phoneNum.setText(bean.getPhoneNumber());
        //                feeType.setText(bean.getFee());
        diagnose.setText(recordBean.getDiagnosis());
        date.setText(DateUtils.transDate(recordBean.getCreateTime(),
                DateUtils.YYYY_MM_DD_HH_MM_SS_SLASH, DateUtils.YYYY_MM_DD));
        doctorName.setText(recordBean.getDoctorName());
        number.setText(recordBean.getPrescriptionNo());
        prescriptionTypeName.setText(recordBean.getType());
        checkDoctorName.setText(recordBean.getAuditorDoctorName());
        sendDoctorName.setText(recordBean.getDispensingDoctor());
        recordList.addAll(recordBean.getPrescriptionItem());
        recordAdapter.notifyDataSetChanged();
        recyclerView.setVisibility(GONE);
        recordRecycler.setVisibility(VISIBLE);
        //        GlideUtils.load(doctorSign, Const.IMAGE_HOST + bean.getCommonSeal(), 0, 0,
        //        false, null);
    }

}
