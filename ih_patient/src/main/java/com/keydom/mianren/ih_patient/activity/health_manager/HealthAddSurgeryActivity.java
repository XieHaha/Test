package com.keydom.mianren.ih_patient.activity.health_manager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.ih_common.view.IhTitleLayout;
import com.keydom.ih_common.view.InterceptorEditText;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.health_manager.controller.HealthAddSurgeryController;
import com.keydom.mianren.ih_patient.activity.health_manager.view.HealthAddSurgeryView;
import com.keydom.mianren.ih_patient.bean.Event;
import com.keydom.mianren.ih_patient.bean.PatientSurgeryHistoryBean;
import com.keydom.mianren.ih_patient.constant.Const;
import com.keydom.mianren.ih_patient.constant.EventType;
import com.keydom.mianren.ih_patient.utils.DateUtils;

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
 * @des 添加手术史
 */
public class HealthAddSurgeryActivity extends BaseControllerActivity<HealthAddSurgeryController> implements HealthAddSurgeryView {
    @BindView(R.id.add_surgery_name_tv)
    InterceptorEditText addSurgeryNameTv;
    @BindView(R.id.add_surgery_time_tv)
    TextView addSurgeryTimeTv;
    @BindView(R.id.add_surgery_status_tv)
    TextView addSurgeryStatusTv;
    @BindView(R.id.add_surgery_notice_tv)
    InterceptorEditText addSurgeryNoticeTv;

    private PatientSurgeryHistoryBean historyBean;

    private List<String> surgeryStatus;

    /**
     * 启动
     */
    public static void start(Context context) {
        context.startActivity(new Intent(context, HealthAddSurgeryActivity.class));
    }

    /**
     * 更新
     */
    public static void start(Context context, PatientSurgeryHistoryBean historyBean) {
        Intent intent = new Intent(context, HealthAddSurgeryActivity.class);
        intent.putExtra(Const.DATA, historyBean);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_health_add_surgery;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle(R.string.txt_add_surgery);
        setRightTxt(getString(R.string.save));
        setRightBtnListener(new IhTitleLayout.OnRightTextClickListener() {
            @Override
            public void OnRightTextClick(View v) {
                saveData();
            }
        });
        historyBean = (PatientSurgeryHistoryBean) getIntent().getSerializableExtra(Const.DATA);
        if (historyBean != null) {
            addSurgeryNameTv.setText(historyBean.getName());
            addSurgeryTimeTv.setText(historyBean.getSurgeryDate());
            addSurgeryStatusTv.setText(historyBean.getSurgeryStatus());
            addSurgeryNoticeTv.setText(historyBean.getRemark());
        } else {
            historyBean = new PatientSurgeryHistoryBean();
        }
        localData();

        addSurgeryTimeTv.setOnClickListener(getController());
        addSurgeryStatusTv.setOnClickListener(getController());
    }

    /**
     * 本地数据获取
     */
    private void localData() {
        String[] surgery = getResources().getStringArray(R.array.surgery_status);
        surgeryStatus = new ArrayList<>(surgery.length);
        Collections.addAll(surgeryStatus, surgery);
    }

    private void saveData() {
        String name = addSurgeryNameTv.getText().toString();
        String date = addSurgeryTimeTv.getText().toString();
        String status = addSurgeryStatusTv.getText().toString();
        String notice = addSurgeryNoticeTv.getText().toString();
        if (TextUtils.isEmpty(date) || TextUtils.isEmpty(name) || TextUtils.isEmpty(notice) || TextUtils.isEmpty(status)) {
            ToastUtil.showMessage(this, "请完善信息");
            return;
        }
        historyBean.setName(name);
        historyBean.setSurgeryDate(date);
        historyBean.setSurgeryStatus(status);
        historyBean.setRemark(notice);

        EventBus.getDefault().post(new Event(EventType.UPDATE_SURGERY_HISTORY, historyBean));
        finish();
    }


    @Override
    public List<String> getSurgeryStatus() {
        return surgeryStatus;
    }

    @Override
    public void setSurgeryStatus(int position) {
        addSurgeryStatusTv.setText(surgeryStatus.get(position));
    }

    @Override
    public void selectSurgeryDate(Date date) {
        addSurgeryTimeTv.setText(DateUtils.dateToString(date));
    }
}
