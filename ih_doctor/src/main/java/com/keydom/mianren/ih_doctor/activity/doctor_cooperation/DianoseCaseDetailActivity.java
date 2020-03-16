package com.keydom.mianren.ih_doctor.activity.doctor_cooperation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ganxin.library.LoadDataLayout;
import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.utils.CommonUtils;
import com.keydom.ih_common.utils.GlideUtils;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.mianren.ih_doctor.R;
import com.keydom.mianren.ih_doctor.activity.doctor_cooperation.controller.DianoseCaseDetailController;
import com.keydom.mianren.ih_doctor.activity.doctor_cooperation.view.DianoseCaseDetailView;
import com.keydom.mianren.ih_doctor.bean.DiagnoseCaseBean;
import com.keydom.mianren.ih_doctor.constant.Const;

import java.util.HashMap;
import java.util.Map;

/**
 * @Name：com.keydom.ih_doctor.activity.personal
 * @Description：病历详情页面
 * @Author：song
 * @Date：18/11/14 上午10:37
 * 修改人：xusong
 * 修改时间：18/11/14 上午10:37
 */
public class DianoseCaseDetailActivity extends BaseControllerActivity<DianoseCaseDetailController> implements DianoseCaseDetailView {

    /**
     * 病历ID
     */
    private String medicalId = "";
    private TextView hospitalName, departmentTv, timeTv, userNameTv, userAgeTv, userSexTv, mainDec, diseaseHistory, oversensitiveHistory, auxiliaryCheck, diagnose, dealWith, doctorName;
    private ImageView doctorSign;

    /**
     * 启动病历记录详情页面
     *
     * @param context
     */
    public static void start(Context context, String applyNumber) {
        Intent starter = new Intent(context, DianoseCaseDetailActivity.class);
        starter.putExtra(Const.DATA, applyNumber);
        context.startActivity(starter);
    }

    private void initView() {
        hospitalName = this.findViewById(R.id.hospital_name);
        doctorSign = this.findViewById(R.id.doctor_sign);
        departmentTv = this.findViewById(R.id.department_tv);
        timeTv = this.findViewById(R.id.time_tv);
        userNameTv = this.findViewById(R.id.user_name_tv);
        userAgeTv = this.findViewById(R.id.user_age_tv);
        userSexTv = this.findViewById(R.id.user_sex_tv);
        mainDec = this.findViewById(R.id.main_dec);
        diseaseHistory = this.findViewById(R.id.disease_history);
        oversensitiveHistory = this.findViewById(R.id.oversensitive_history);
        auxiliaryCheck = this.findViewById(R.id.auxiliary_check);
        diagnose = this.findViewById(R.id.diagnose);
        dealWith = this.findViewById(R.id.deal_with);
        doctorName = this.findViewById(R.id.doctor_name);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_dianose_case_detail_layout;
    }

    @Override
    public void initData(@org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        medicalId = getIntent().getStringExtra(Const.DATA);
        if (medicalId == null || "".equals(medicalId)) {
            ToastUtil.showMessage(this, "查不到该病例");
            return;
        }
        setTitle("门诊病历记录");
        initView();
        pageLoading();
        getController().getDetail();
        setReloadListener(new LoadDataLayout.OnReloadListener() {
            @Override
            public void onReload(View v, int status) {
                pageLoading();
                getController().getDetail();
            }
        });
    }


    @Override
    public void getDetailSuccess(DiagnoseCaseBean bean) {
        setInfo(bean);
        pageLoadingSuccess();
    }

    @Override
    public void getDetailFailed(String errMsg) {
        ToastUtil.showMessage(this, errMsg);
        pageLoadingFail();
    }

    @Override
    public Map<String, Object> getQueryMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("medicalId", medicalId);
        return map;
    }

    /**
     * 设置病历
     *
     * @param bean 病历数据
     */
    private void setInfo(DiagnoseCaseBean bean) {
        if (bean == null) {
            ToastUtil.showMessage(this, "病历不存在");
            finish();
            return;
        }
        hospitalName.setText(bean.getHospitalName());
        departmentTv.setText("科别：" + bean.getDoctorDept());
        timeTv.setText("日期：" + bean.getTime());
        userNameTv.setText(bean.getName());
        userAgeTv.setText(String.valueOf(bean.getAge()));
        userSexTv.setText(CommonUtils.getSex(bean.getSex()));
        mainDec.setText(bean.getMainComplaint());
        diseaseHistory.setText(bean.getHistoryIllness());
        oversensitiveHistory.setText(bean.getHistoryAllergy());
        auxiliaryCheck.setText(bean.getAuxiliaryInspect());
        diagnose.setText(bean.getDiagnosis());
        dealWith.setText(bean.getHandleOpinion());
        doctorName.setText(bean.getDoctorName());
        GlideUtils.load(doctorSign, Const.IMAGE_HOST + bean.getCommonSeal(), 0, 0, false, null);

    }


}
