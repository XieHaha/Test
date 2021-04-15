package com.keydom.mianren.ih_patient.activity.health_manager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.keydom.ih_common.AbsTextWatcher;
import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.ih_common.view.IhTitleLayout;
import com.keydom.ih_common.view.InterceptorEditText;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.health_manager.controller.HealthArchivesBaseController;
import com.keydom.mianren.ih_patient.activity.health_manager.view.HealthArchivesBaseView;
import com.keydom.mianren.ih_patient.bean.Event;
import com.keydom.mianren.ih_patient.bean.HealthArchivesBean;
import com.keydom.mianren.ih_patient.constant.Const;
import com.keydom.mianren.ih_patient.constant.EventType;
import com.keydom.mianren.ih_patient.utils.CommUtil;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import butterknife.BindView;

/**
 * @author 顿顿
 * @date 20/3/4 10:54
 * @des 健康档案-基本信息
 */
public class HealthArchivesBaseActivity extends BaseControllerActivity<HealthArchivesBaseController> implements HealthArchivesBaseView {
    @BindView(R.id.archives_base_name_et)
    InterceptorEditText archivesBaseNameEt;
    @BindView(R.id.archives_base_male_tv)
    TextView archivesBaseMaleTv;
    @BindView(R.id.archives_base_female_tv)
    TextView archivesBaseFemaleTv;
    @BindView(R.id.archives_base_birth_tv)
    TextView archivesBaseBirthTv;
    @BindView(R.id.archives_base_card_et)
    InterceptorEditText archivesBaseCardEt;
    @BindView(R.id.archives_base_height_et)
    InterceptorEditText archivesBaseHeightEt;
    @BindView(R.id.archives_base_weight_et)
    InterceptorEditText archivesBaseWeightEt;
    @BindView(R.id.archives_base_bmi_et)
    TextView archivesBaseBmiEt;
    @BindView(R.id.archives_base_address_et)
    InterceptorEditText archivesBaseAddressEt;
    @BindView(R.id.archives_base_phone_et)
    InterceptorEditText archivesBasePhoneEt;
    @BindView(R.id.archives_base_job_tv)
    TextView archivesBaseJobTv;
    @BindView(R.id.archives_base_company_et)
    InterceptorEditText archivesBaseCompanyEt;
    @BindView(R.id.archives_base_nation_tv)
    TextView archivesBaseNationTv;
    @BindView(R.id.health_archives_smoke_frequency_layout)
    LinearLayout healthArchivesSmokeFrequencyLayout;
    @BindView(R.id.archives_base_marital_tv)
    TextView archivesBaseMaritalTv;
    @BindView(R.id.archives_base_unmarried_tv)
    TextView archivesBaseUnmarriedTv;
    @BindView(R.id.archives_base_birth_status_tv)
    TextView archivesBaseBirthStatusTv;

    private HealthArchivesBean archivesBean;

    private List<String> jobTypes;
    private List<String> birthStatus;
    private List<String> contactPeople;

    /**
     * 民族
     */
    private String height, weight, address, company, nation, jobType, birthState, bmi,
            maritalHistory;

    /**
     * 启动
     */
    public static void start(Context context, HealthArchivesBean archivesBean) {
        Intent intent = new Intent(context, HealthArchivesBaseActivity.class);
        intent.putExtra(Const.DATA, archivesBean);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_health_archives_base;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle(R.string.txt_health_archives);
        setRightTxt(getString(R.string.save));
        setRightBtnListener(new IhTitleLayout.OnRightTextClickListener() {
            @Override
            public void OnRightTextClick(View v) {
                initParams();
            }
        });
        archivesBean = (HealthArchivesBean) getIntent().getSerializableExtra(Const.DATA);
        localData();


        archivesBaseNameEt.setEnabled(false);
        archivesBaseNameEt.setFocusableInTouchMode(false);
        archivesBaseCardEt.setEnabled(false);
        archivesBaseCardEt.setFocusableInTouchMode(false);
        archivesBasePhoneEt.setEnabled(false);
        archivesBasePhoneEt.setFocusableInTouchMode(false);
        //        archivesBaseMaleTv.setOnClickListener(getController());
        //        archivesBaseFemaleTv.setOnClickListener(getController());
        //        archivesBaseBirthTv.setOnClickListener(getController());
        archivesBaseJobTv.setOnClickListener(getController());

        archivesBaseNationTv.setOnClickListener(getController());
        archivesBaseBirthStatusTv.setOnClickListener(getController());
        archivesBaseWeightEt.addTextChangedListener(new AbsTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                weight = s.toString();
                setBmi();
            }

        });
        archivesBaseHeightEt.addTextChangedListener(new AbsTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                height = s.toString();
                setBmi();
            }
        });
        bindData();
    }

    private void bindData() {
        archivesBaseNameEt.setText(archivesBean.getName());
        if ("0".equals(archivesBean.getSex())) {
            archivesBaseMaleTv.setSelected(true);
            archivesBaseFemaleTv.setSelected(false);
        } else {
            archivesBaseMaleTv.setSelected(false);
            archivesBaseFemaleTv.setSelected(true);
        }
        archivesBaseBirthTv.setText(archivesBean.getBirthDate());
        archivesBaseCardEt.setText(archivesBean.getIdCard());
        archivesBasePhoneEt.setText(archivesBean.getPhoneNumber());

        maritalHistory = archivesBean.getMaritalHistory();
        if (TextUtils.isEmpty(maritalHistory)) {
            archivesBaseMaritalTv.setOnClickListener(getController());
            archivesBaseUnmarriedTv.setOnClickListener(getController());
        } else {
            if ("已婚".equals(maritalHistory)) {
                archivesBaseMaritalTv.setSelected(true);
                archivesBaseUnmarriedTv.setSelected(false);
            } else {
                archivesBaseMaritalTv.setSelected(false);
                archivesBaseUnmarriedTv.setSelected(true);
            }
        }
        archivesBaseWeightEt.setText(archivesBean.getWeight());
        archivesBaseHeightEt.setText(archivesBean.getHeight());

        bmi = archivesBean.getBmi();
        archivesBaseBmiEt.setText(bmiStatus());
        archivesBaseAddressEt.setText(archivesBean.getAddress());
        archivesBaseJobTv.setText(archivesBean.getProfessionalCategory());
        archivesBaseCompanyEt.setText(archivesBean.getWorkUnits());
        archivesBaseNationTv.setText(archivesBean.getNation());
        archivesBaseBirthStatusTv.setText(archivesBean.getFertilityStatus());
    }

    /**
     * 本地数据获取
     */
    private void localData() {
        String[] jobs = getResources().getStringArray(R.array.job_type);
        jobTypes = new ArrayList<>(jobs.length);
        Collections.addAll(jobTypes, jobs);

        String[] birth = getResources().getStringArray(R.array.birth_status);
        birthStatus = new ArrayList<>(birth.length);
        Collections.addAll(birthStatus, birth);
    }

    public void initParams() {
        height = archivesBaseHeightEt.getText().toString();
        weight = archivesBaseWeightEt.getText().toString();
        address = archivesBaseAddressEt.getText().toString();
        company = archivesBaseCompanyEt.getText().toString();
        jobType = archivesBaseJobTv.getText().toString();
        nation = archivesBaseNationTv.getText().toString();
        birthState = archivesBaseBirthStatusTv.getText().toString();

        if (TextUtils.isEmpty(height) || TextUtils.isEmpty(weight) || TextUtils.isEmpty(address) || TextUtils.isEmpty(company) || TextUtils.isEmpty(jobType) || TextUtils.isEmpty(nation) || TextUtils.isEmpty(birthState) || TextUtils.isEmpty(bmi) || TextUtils.isEmpty(maritalHistory)) {
            ToastUtil.showMessage(this, "请完善以上所有信息");
            return;
        }
        archivesBean.setHeight(height);
        archivesBean.setWeight(weight);
        archivesBean.setAddress(address);
        archivesBean.setWorkUnits(company);
        archivesBean.setBmi(bmi);
        archivesBean.setProfessionalCategory(jobType);
        archivesBean.setNation(nation);
        archivesBean.setFertilityStatus(birthState);
        archivesBean.setMaritalHistory(maritalHistory);
        //通知更新
        EventBus.getDefault().post(new Event(EventType.UPDATE_ARCHIVES, archivesBean));
        finish();
    }

    @Override
    public List<String> getJobTypes() {
        return jobTypes;
    }

    @Override
    public List<String> getBirthStatus() {
        return birthStatus;
    }

    public void setBmi() {
        if (TextUtils.isEmpty(height) || TextUtils.isEmpty(weight)) {
            return;
        }
        bmi = CommUtil.getBMI(Float.parseFloat(weight), Float.parseFloat(height) / 100f);
        archivesBaseBmiEt.setText(bmiStatus());
    }

    /**
     * BMI说明：
     * BMI<18.5 体重过轻
     * 18.5<=BMI<=23.9 正常
     * 23.9<BMI<=27 体重过重
     * 27<BMI<=32 肥胖
     * BMI>32 非常肥胖
     */
    private String bmiStatus() {
        float value = Float.parseFloat(bmi);
        if (value <= 0) {
            return "";
        } else if (value < 18.5) {
            return bmi + " 体重过轻";
        } else if (value >= 18.5 && value <= 23.9) {
            return bmi + " 正常";
        } else if (value > 23.9 && value <= 27) {
            return bmi + " 体重过重";
        } else if (value > 27 && value <= 32) {
            return bmi + " 肥胖";
        } else {
            return bmi + " 非常肥胖";
        }
    }

    @Override
    public void setSexSelect(int type) {
        if (type == 0) {
            archivesBaseMaleTv.setSelected(true);
            archivesBaseFemaleTv.setSelected(false);
        } else {
            archivesBaseMaleTv.setSelected(false);
            archivesBaseFemaleTv.setSelected(true);
        }
    }

    @Override
    public void setMarrySelect(int type) {
        if (type == 0) {
            maritalHistory = "已婚";
            archivesBaseMaritalTv.setSelected(true);
            archivesBaseUnmarriedTv.setSelected(false);
        } else {
            maritalHistory = "未婚";
            archivesBaseMaritalTv.setSelected(false);
            archivesBaseUnmarriedTv.setSelected(true);
        }
    }

    @Override
    public String getNation() {
        return nation;
    }

    @Override
    public void setNation(String nation) {
        this.nation = nation;
        archivesBaseNationTv.setText(nation);
    }

    @Override
    public void setJobType(int position) {
        this.jobType = jobTypes.get(position);
        archivesBaseJobTv.setText(jobType);
    }

    @Override
    public void setBirthStatus(int position) {
        this.birthState = birthStatus.get(position);
        archivesBaseBirthStatusTv.setText(birthState);
    }

    @Override
    public void onBirthDaySelect(Date date) {
    }
}
