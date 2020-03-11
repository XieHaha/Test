package com.keydom.ih_patient.activity.reserve_obstetric_hospital;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.view.TimePickerView;
import com.blankj.utilcode.util.KeyboardUtils;
import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.view.InterceptorEditText;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.diagnose_user_manager.ManageUserSelectActivity;
import com.keydom.ih_patient.activity.reserve_obstetric_hospital.controller.ReserveObstetricHospitalController;
import com.keydom.ih_patient.activity.reserve_obstetric_hospital.view.ReserveObstetricHospitalView;
import com.keydom.ih_patient.bean.Event;
import com.keydom.ih_patient.bean.ManagerUserBean;
import com.keydom.ih_patient.constant.EventType;
import com.keydom.ih_patient.utils.DateUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.Nullable;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @date 20/2/25 10:49
 * @des 产科住院预约
 */
public class ReserveObstetricHospitalActivity extends BaseControllerActivity<ReserveObstetricHospitalController> implements ReserveObstetricHospitalView {
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

    private ManagerUserBean curUserBean;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_reserve_obstetric_hospital;
    }

    /**
     * 启动
     */
    public static void start(Context context) {
        context.startActivity(new Intent(context, ReserveObstetricHospitalActivity.class));
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        setTitle(getString(R.string.txt_obstetric_hospital_reserve));

        txNext.setOnClickListener(getController());
    }

    @OnClick({R.id.layout_hospital_date, R.id.layout_bed, R.id.layout_doctor,
            R.id.layout_anesthetist, R.id.layout_visit, R.id.layout_menstruation,
            R.id.layout_due_date, R.id.layout_fetus, R.id.layout_select})
    public void onViewClicked(View view) {
        TimePickerView pickerView;
        switch (view.getId()) {
            case R.id.layout_hospital_date:
                KeyboardUtils.hideSoftInput(this);
                pickerView = new TimePickerBuilder(this,
                        (date, v1) -> tvHospitalDate.setText(DateUtils.dateToString(date))).build();
                pickerView.show();
                break;
            case R.id.layout_bed:
                break;
            case R.id.layout_doctor:
                break;
            case R.id.layout_anesthetist:
                break;
            case R.id.layout_visit:
                long id = -1;
                if (curUserBean != null) {
                    id = curUserBean.getId();
                }
                ManageUserSelectActivity.start(this, id);
                break;
            case R.id.layout_menstruation:
                KeyboardUtils.hideSoftInput(this);
                pickerView = new TimePickerBuilder(this,
                        (date, v1) -> tvLast.setText(DateUtils.dateToString(date))).build();
                pickerView.show();
                break;
            case R.id.layout_due_date:
                KeyboardUtils.hideSoftInput(this);
                pickerView = new TimePickerBuilder(this,
                        (date, v1) -> tvDueDate.setText(DateUtils.dateToString(date))).build();
                pickerView.show();
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onVisitPeopleSelect(Event event) {
        if (event.getType() == EventType.SENDPATIENTINFO) {
            curUserBean = (ManagerUserBean) event.getData();
            tvVisitName.setText(curUserBean.getName());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
