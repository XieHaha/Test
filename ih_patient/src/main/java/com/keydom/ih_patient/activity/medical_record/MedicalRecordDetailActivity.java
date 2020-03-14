package com.keydom.ih_patient.activity.medical_record;

import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.SpanUtils;
import com.blankj.utilcode.util.StringUtils;
import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.constant.Const;
import com.keydom.ih_common.utils.GlideUtils;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.medical_record.controller.MedicalRecordDetailController;
import com.keydom.ih_patient.activity.medical_record.view.MedicalRecordDetailView;
import com.keydom.ih_patient.bean.MedicalRecordBean;

import org.jetbrains.annotations.Nullable;

/**
 * created date: 2019/1/4 on 16:48
 * des:电子处方详情页面
 */
public class MedicalRecordDetailActivity extends BaseControllerActivity<MedicalRecordDetailController> implements MedicalRecordDetailView{
    public static final String MEDICAL_ID="medical_id";

    private TextView mHospital;
    private TextView mDepartment;
    private TextView mTime;
    private TextView mName;
    private TextView mAge;
    private TextView mSex;
    private TextView mContent1;
    private TextView mContent2;
    private TextView mContent3;
    private TextView mContent4;
    private TextView mContent5;
    private TextView mContent6;
    private TextView mDoctor;
    private ImageView mSeal;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_medical_record_detail;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle("门诊病历记录");
        getView();
        long id = getIntent().getLongExtra(MEDICAL_ID,0);
        getController().getMedicalDetail(id);
    }

    /**
     * 查找控件
     */
    public void getView() {
        mHospital = findViewById(R.id.hospital);
        mDepartment = findViewById(R.id.department);
        mTime = findViewById(R.id.time);
        mName = findViewById(R.id.name);
        mAge = findViewById(R.id.age);
        mSex = findViewById(R.id.sex);
        mContent1 = findViewById(R.id.content1);
        mContent2 = findViewById(R.id.content2);
        mContent3 = findViewById(R.id.content3);
        mContent4 = findViewById(R.id.content4);
        mContent5 = findViewById(R.id.content5);
        mContent6 = findViewById(R.id.content6);
        mDoctor = findViewById(R.id.doctor);
        mSeal = findViewById(R.id.seal);
    }

    @Override
    public void getDetailCallBack(MedicalRecordBean bean) {
        if (bean!=null){
            mHospital.setText(bean.getHospitalName());
            mDepartment.setText("科别："+bean.getDoctorDept());
            mTime.setText("日期："+bean.getTime());
            SpannableStringBuilder name = new SpanUtils().append("姓名：").setFontSize(13, true).setForegroundColor(getResources().getColor(R.color.edit_hint_color))
                    .append(StringUtils.isEmpty(bean.getName())?"":bean.getName()).setFontSize(13, true).setForegroundColor(getResources().getColor(R.color.color_333333))
                    .create();
            mName.setText(name);
            SpannableStringBuilder age = new SpanUtils().append("年龄：").setFontSize(13, true).setForegroundColor(getResources().getColor(R.color.edit_hint_color))
                    .append(bean.getAge()+"岁").setFontSize(13, true).setForegroundColor(getResources().getColor(R.color.color_333333))
                    .create();
            mAge.setText(age);
            String sex = "0".equals(bean.getSex())?"男":"女";
            SpannableStringBuilder sexTV = new SpanUtils().append("性别：").setFontSize(13, true).setForegroundColor(getResources().getColor(R.color.edit_hint_color))
                    .append(sex).setFontSize(13, true).setForegroundColor(getResources().getColor(R.color.color_333333))
                    .create();
            mSex.setText(sexTV);
            mDoctor.setText(bean.getDoctorName());
            mContent1.setText(bean.getMainComplaint());
            mContent2.setText(bean.getHistoryAllergy());
            mContent3.setText(bean.getHistoryIllness());
            mContent4.setText(bean.getAuxiliaryInspect());
            mContent5.setText(bean.getDiagnosis());
            mContent6.setText(bean.getHandleOpinion());
            GlideUtils.load(mSeal, Const.IMAGE_HOST+bean.getCommonSeal(),0,0,false,null);
        }
    }
}
