package com.keydom.mianren.ih_patient.activity.reserve_obstetric_hospital;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.blankj.utilcode.util.KeyboardUtils;
import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.bean.DoctorInfo;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.ih_common.view.InterceptorEditText;
import com.keydom.mianren.ih_patient.App;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.diagnose_user_manager.ManageUserSelectActivity;
import com.keydom.mianren.ih_patient.activity.reserve_obstetric_hospital.controller.ReserveObstetricHospitalController;
import com.keydom.mianren.ih_patient.activity.reserve_obstetric_hospital.view.ReserveObstetricHospitalView;
import com.keydom.mianren.ih_patient.bean.DepartmentInfo;
import com.keydom.mianren.ih_patient.bean.Event;
import com.keydom.mianren.ih_patient.bean.ManagerUserBean;
import com.keydom.mianren.ih_patient.constant.EventType;
import com.keydom.mianren.ih_patient.utils.DateUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * @author 顿顿
 * @date 20/2/25 10:49
 * @des 产科住院预约
 */
public class ReserveObstetricHospitalActivity extends BaseControllerActivity<ReserveObstetricHospitalController> implements ReserveObstetricHospitalView {
    @BindView(R.id.tv_hospital_date)
    TextView tvHospitalDate;
    @BindView(R.id.tv_bed)
    TextView tvBed;
    @BindView(R.id.tv_depart)
    TextView tvDepart;
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
    @BindView(R.id.layout_obstetric)
    LinearLayout layoutObstetric;

    private ManagerUserBean curUserBean;

    private String reserveDate, bed, age, lastDate, dueDate, phone;
    private int fetus;

    /**
     * 科室
     */
    private DepartmentInfo curDepart;
    private List<DepartmentInfo> departmentList;
    private List<String> departName = new ArrayList<>();
    /**
     * 麻醉师
     */
    private DoctorInfo anesthesiologistDoctor;
    private List<DoctorInfo> anesthesiologistList;
    private List<String> anesthesiologistName = new ArrayList<>();
    /**
     * 手术医生
     */
    private DoctorInfo operationDoctor;
    private List<DoctorInfo> operationList;
    private List<String> operationName = new ArrayList<>();

    /**
     * 床位
     */
    private List<String> bedGradeDit = new ArrayList<>();
    /**
     * 胎数
     */
    private List<String> fetusDit = new ArrayList<>();


    /**
     * 手术医生
     */
    public static final int OPERATION_DOCTOR = 0;
    /**
     * 麻醉师
     */
    public static final int ANESTHESIOLOGIST_DOCTOR = 1;

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
        setRightTxt("预约记录");
        setRightBtnListener(getController());

        bedGradeDit.add("单人间");
        bedGradeDit.add("双人间");
        bedGradeDit.add("三人间");
        bedGradeDit.add("多人间");

        fetusDit.add("单胎");
        fetusDit.add("双胎");
        fetusDit.add("多胎");

        txNext.setOnClickListener(getController());

        getController().getObstetricDepart();
    }

    @OnClick({R.id.layout_hospital_date, R.id.layout_bed, R.id.layout_depart, R.id.layout_doctor,
            R.id.layout_anesthetist, R.id.layout_visit, R.id.layout_menstruation,
            R.id.layout_due_date, R.id.layout_fetus, R.id.layout_select})
    public void onViewClicked(View view) {
        TimePickerView pickerView;
        switch (view.getId()) {
            case R.id.layout_hospital_date:
                KeyboardUtils.hideSoftInput(this);
                Calendar startDate = Calendar.getInstance();
                pickerView = new TimePickerBuilder(this,
                        (date, v1) -> {
                            reserveDate = DateUtils.dateToString(date);
                            tvHospitalDate.setText(reserveDate);
                        })
                        .setRangDate(startDate, null).build();
                pickerView.show();
                break;
            case R.id.layout_bed:
                OptionsPickerView pvOptions = new OptionsPickerBuilder(getContext(),
                        (options1, option2, options3, v) -> {
                            bed = bedGradeDit.get(options1);
                            tvBed.setText(bed);
                        }).build();
                pvOptions.setPicker(bedGradeDit);
                pvOptions.show();
                break;
            case R.id.layout_depart:
                OptionsPickerView depart = new OptionsPickerBuilder(getContext(),
                        (options1, option2, options3, v) -> {
                            curDepart = departmentList.get(options3);
                            tvDepart.setText(curDepart.getName());
                            getController().getDeptDoctor(String.valueOf(curDepart.getId()),
                                    OPERATION_DOCTOR);
                            getController().getDeptDoctor(String.valueOf(curDepart.getId()),
                                    ANESTHESIOLOGIST_DOCTOR);
                        }).build();
                depart.setPicker(departName);
                depart.show();
                break;
            case R.id.layout_doctor:
                if (curDepart != null) {
                    OptionsPickerView doctorPicker = new OptionsPickerBuilder(getContext(),
                            (options1, option2, options3, v) -> {
                                operationDoctor = operationList.get(options3);
                                tvDoctor.setText(operationDoctor.getName());
                            }).build();
                    doctorPicker.setPicker(operationName);
                    doctorPicker.show();
                } else {
                    ToastUtil.showMessage(this, "请先选择科室");
                }
                break;
            case R.id.layout_anesthetist:
                if (curDepart != null) {
                    OptionsPickerView anesthetistPicker = new OptionsPickerBuilder(getContext(),
                            (options1, option2, options3, v) -> {
                                anesthesiologistDoctor = anesthesiologistList.get(options3);
                                tvDoctor.setText(anesthesiologistDoctor.getName());
                            }).build();
                    anesthetistPicker.setPicker(anesthesiologistName);
                    anesthetistPicker.show();
                } else {
                    ToastUtil.showMessage(this, "请先选择科室");
                }
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
                        (date, v1) -> {
                            lastDate = DateUtils.dateToString(date);
                            tvLast.setText(lastDate);
                        }).build();
                pickerView.show();
                break;
            case R.id.layout_due_date:
                KeyboardUtils.hideSoftInput(this);
                pickerView = new TimePickerBuilder(this,
                        (date, v1) -> {
                            dueDate = DateUtils.dateToString(date);
                            tvDueDate.setText(dueDate);
                        }).setRangDate(Calendar.getInstance(), null).build();
                pickerView.show();
                break;
            case R.id.layout_fetus:
                OptionsPickerView fetusPicker = new OptionsPickerBuilder(getContext(),
                        (options1, option2, options3, v) -> {
                            fetus = options1;
                            tvFetus.setText(fetusDit.get(options1));
                        }).build();
                fetusPicker.setPicker(fetusDit);
                fetusPicker.show();
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
    public void requestDepartSuccess(List<DepartmentInfo> data) {
        departmentList = data;
        departName.clear();
        if (departmentList != null) {
            for (DepartmentInfo info : departmentList) {
                departName.add(info.getName());
            }
        }
    }

    @Override
    public void requestDepartFailed(String msg) {

    }

    @Override
    public void requestDoctorSuccess(List<DoctorInfo> data, int type) {
        switch (type) {
            case OPERATION_DOCTOR:
                operationList = data;
                operationName.clear();
                if (operationList != null) {
                    for (DoctorInfo info : operationList) {
                        operationName.add(info.getName());
                    }
                }
                break;
            case ANESTHESIOLOGIST_DOCTOR:
                anesthesiologistList = data;
                anesthesiologistName.clear();
                if (anesthesiologistList != null) {
                    for (DoctorInfo info : anesthesiologistList) {
                        anesthesiologistName.add(info.getName());
                    }
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void requestDoctorFailed(String msg) {

    }

    @Override
    public void reserveSuccess() {
        ToastUtil.showMessage(this, "预约成功");
        ObstetricHospitalListActivity.start(getContext());
        finish();
    }

    @Override
    public Map<String, Object> getParams() {
        Map<String, Object> map = new HashMap<>(20);
        map.put("age", age);
        map.put("appointmentTime", reserveDate);
        map.put("bed", bed);
        map.put("deptId", curDepart.getId());
        map.put("deptName", curDepart.getName());
        map.put("embryoNumber", fetus);
        map.put("expectedDateOfConfinement", dueDate);
        map.put("lastMenstrualPeriodTime", lastDate);
        map.put("eleCardNumber", curUserBean.getId());
        map.put("patientId", curUserBean.getId());
        map.put("patientName", curUserBean.getName());
        map.put("phoneNumber", phone);
        map.put("registerUserId", App.userInfo.getId());
        if (anesthesiologistDoctor != null) {
            map.put("anesthetistId", anesthesiologistDoctor.getId());
            map.put("anesthetistName", anesthesiologistDoctor.getName());
        }
        if (operationDoctor != null) {
            map.put("surgeonId", operationDoctor.getId());
            map.put("surgeonName", operationDoctor.getName());
        }
        return map;
    }

    @Override
    public boolean reserveAble() {
        age = etAge.getText().toString();
        phone = etPhone.getText().toString();
        if (TextUtils.isEmpty(reserveDate) || TextUtils.isEmpty(bed) || curDepart == null || curUserBean == null) {
            ToastUtil.showMessage(this, "请完善预约信息");
            return false;
        }
        if (ivSelect.getVisibility() == View.GONE) {
            ToastUtil.showMessage(this, "请仔细阅读并同意入院注意事项");
            return false;
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
