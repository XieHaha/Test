package com.keydom.ih_patient.activity.reserve_examination;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.keydom.ih_common.base.BaseActivity;
import com.keydom.ih_common.view.InterceptorEditText;
import com.keydom.ih_patient.R;

import org.jetbrains.annotations.Nullable;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @date 20/2/25 10:49
 * @des 检验检查预约
 */
public class ExaminationReserveActivity extends BaseActivity {
    @BindView(R.id.tv_hospital_date)
    TextView tvHospitalDate;
    @BindView(R.id.tv_bed)
    TextView tvBed;
    @BindView(R.id.tv_doctor)
    TextView tvDoctor;
    @BindView(R.id.tv_anesthetist)
    TextView tvAnesthetist;
    @BindView(R.id.tv_visit_name)
    TextView tvVisitName;
    @BindView(R.id.et_age)
    InterceptorEditText etAge;
    @BindView(R.id.tv_last)
    TextView tvLast;
    @BindView(R.id.tv_due_date)
    TextView tvDueDate;
    @BindView(R.id.tv_fetus)
    TextView tvFetus;
    @BindView(R.id.et_phone)
    InterceptorEditText etPhone;
    @BindView(R.id.iv_select)
    ImageView ivSelect;
    @BindView(R.id.tv_note)
    TextView tvNote;
    @BindView(R.id.tx_next)
    TextView txNext;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_reserve_obstetric_hospital;
    }

    /**
     * 启动
     */
    public static void start(Context context) {
        context.startActivity(new Intent(context, ExaminationReserveActivity.class));
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle(getString(R.string.txt_obstetric_hospital_reserve));
    }

    @OnClick({R.id.layout_hospital_date, R.id.layout_bed, R.id.layout_doctor,
            R.id.layout_anesthetist, R.id.layout_visit, R.id.layout_menstruation,
            R.id.layout_due_date, R.id.layout_fetus, R.id.layout_select})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layout_hospital_date:
                break;
            case R.id.layout_bed:
                break;
            case R.id.layout_doctor:
                break;
            case R.id.layout_anesthetist:
                break;
            case R.id.layout_visit:
                break;
            case R.id.layout_menstruation:
                break;
            case R.id.layout_due_date:
                break;
            case R.id.layout_fetus:
                break;
            case R.id.layout_select:
                if (ivSelect.getVisibility() == View.VISIBLE) {
                    ivSelect.setVisibility(View.GONE);
                } else {
                    ivSelect.setVisibility(View.VISIBLE);
                }
                break;
            default:
                break;
        }
    }
}
