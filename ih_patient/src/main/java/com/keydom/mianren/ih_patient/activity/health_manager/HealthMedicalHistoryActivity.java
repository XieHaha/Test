package com.keydom.mianren.ih_patient.activity.health_manager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.ih_common.view.InterceptorEditText;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.health_manager.controller.HealthMedicalHistoryController;
import com.keydom.mianren.ih_patient.activity.health_manager.view.HealthMedicalHistoryView;
import com.keydom.mianren.ih_patient.bean.Event;
import com.keydom.mianren.ih_patient.constant.Const;
import com.keydom.mianren.ih_patient.constant.EventType;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;

/**
 * @author 顿顿
 * @date 20/3/4 10:54
 * @des 病史
 */
public class HealthMedicalHistoryActivity extends BaseControllerActivity<HealthMedicalHistoryController> implements HealthMedicalHistoryView {
    @BindView(R.id.health_medical_title_tv)
    TextView healthMedicalTitleTv;
    @BindView(R.id.health_medical_hint_tv)
    TextView healthMedicalHintTv;
    @BindView(R.id.health_medical_content_flow_layout)
    TagFlowLayout healthMedicalContentFlowLayout;
    @BindView(R.id.health_medical_other_tv)
    TextView healthMedicalOtherTv;
    @BindView(R.id.health_medical_other_et)
    InterceptorEditText healthMedicalOtherEt;

    /**
     * 既往病史
     */
    public static final int PAST_MEDICAL_HISTORY = 100;
    /**
     * 遗传史
     */
    public static final int GENETIC_HISTORY = 200;
    private int medicalType;

    private TagAdapter<String> tagAdapter;

    private List<String> medicalData;
    private List<String> selectMedicalData = new ArrayList<>();
    private Set<Integer> medicalSet = new HashSet<>();
    private String selectValue;

    /**
     * 启动
     */
    public static void start(Context context, String history, int type) {
        Intent intent = new Intent(context, HealthMedicalHistoryActivity.class);
        intent.putExtra(Const.TYPE, type);
        intent.putExtra(Const.DATA, history);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_health_medical_history;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setRightTxt(getString(R.string.save));
        setRightBtnListener(v -> saveData());
        medicalType = getIntent().getIntExtra(Const.TYPE, -1);
        selectValue = getIntent().getStringExtra(Const.DATA);
        if (medicalType == PAST_MEDICAL_HISTORY) {
            setTitle(R.string.txt_past_medical);
            healthMedicalTitleTv.setVisibility(View.GONE);
            healthMedicalHintTv.setText(R.string.txt_past_medical_hint);
        } else {
            setTitle(R.string.txt_genetic);
            healthMedicalTitleTv.setVisibility(View.VISIBLE);
            healthMedicalHintTv.setText(R.string.txt_genetic_hint);
        }

        healthMedicalContentFlowLayout.setOnTagClickListener((view, position, parent) -> {
            filterMedical(position);
            return true;
        });
        healthMedicalOtherTv.setOnClickListener(v -> {
            if (healthMedicalOtherTv.isSelected()) {
                healthMedicalOtherTv.setSelected(false);
                healthMedicalOtherEt.setEnabled(false);
                healthMedicalOtherEt.setFocusableInTouchMode(false);
                healthMedicalOtherEt.setText("");
            } else {
                healthMedicalOtherTv.setSelected(true);
                healthMedicalOtherEt.setEnabled(true);
                healthMedicalOtherEt.setFocusableInTouchMode(true);
            }

            if (medicalSet.contains(0)) {
                medicalSet.remove(0);
                selectMedicalData.remove(medicalData.get(0));
                setFlows();
            }
        });
        localData();
        tagAdapter = new TagAdapter<String>(medicalData) {
            @Override
            public View getView(FlowLayout parent, int position, String o) {
                TextView tv =
                        (TextView) getLayoutInflater().inflate(R.layout.view_health_manager_flow,
                                healthMedicalContentFlowLayout, false);
                tv.setText(o);
                return tv;
            }
        };
        healthMedicalContentFlowLayout.setAdapter(tagAdapter);
        setFlows();
    }

    /**
     * 本地数据获取
     */
    private void localData() {
        String[] jobs = getResources().getStringArray(R.array.medical_past);
        medicalData = new ArrayList<>(jobs.length);
        Collections.addAll(medicalData, jobs);

        if (!TextUtils.isEmpty(selectValue)) {
            String[] temp = selectValue.split(",");
            for (int i = 0; i < temp.length; i++) {
                selectMedicalData.add(temp[i]);
            }
        }

        StringBuilder builder = new StringBuilder();
        for (String value : selectMedicalData) {
            if (!medicalData.contains(value)) {
                builder.append(value);
                builder.append(",");
            }
        }

        if (!TextUtils.isEmpty(builder.toString())) {
            healthMedicalOtherTv.setSelected(true);
            healthMedicalOtherEt.setText(builder.toString());
        }
    }

    private void filterMedical(Integer position) {
        if (!medicalSet.contains(position)) {
            if (position == 0) {
                medicalSet.clear();
                selectMedicalData.clear();
                //清除other选项
                healthMedicalOtherTv.setSelected(false);
                healthMedicalOtherEt.setText("");
                healthMedicalOtherEt.setEnabled(false);
                healthMedicalOtherEt.setFocusableInTouchMode(false);
            } else {
                medicalSet.remove(0);
                selectMedicalData.remove(medicalData.get(0));
            }
            medicalSet.add(position);
        } else {
            selectMedicalData.remove(medicalData.get(position));
            medicalSet.remove(position);
        }
        setFlows();
    }

    private void setFlows() {
        if (selectMedicalData != null && selectMedicalData.size() > 0) {
            for (int i = 0; i < medicalData.size(); i++) {
                for (int j = 0; j < selectMedicalData.size(); j++) {
                    if (selectMedicalData.get(j).equals(medicalData.get(i))) {
                        medicalSet.add(i);
                    }
                }
            }
        }
        tagAdapter.setSelectedList(medicalSet);
        tagAdapter.notifyDataChanged();
    }

    /**
     * 数据保存
     */
    private void saveData() {
        String other = healthMedicalOtherEt.getText().toString();
        if (medicalSet.size() == 0 && TextUtils.isEmpty(other)) {
            ToastUtil.showMessage(this, "请选择或手动输入");
            return;
        }
        StringBuilder builder = new StringBuilder();
        for (Integer i : medicalSet) {
            String value = medicalData.get(i);
            builder.append(value);
            builder.append(",");
        }
        builder.append(other);
        if (medicalType == PAST_MEDICAL_HISTORY) {
            EventBus.getDefault().post(new Event(EventType.UPDATE_MEDICAL_PAST,
                    builder.toString()));
        } else {
            EventBus.getDefault().post(new Event(EventType.UPDATE_GENETIC_PAST,
                    builder.toString()));
        }
        finish();
    }
}
