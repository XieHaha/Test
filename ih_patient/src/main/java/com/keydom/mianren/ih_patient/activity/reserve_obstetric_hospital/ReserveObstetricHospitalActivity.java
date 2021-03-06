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
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.KeyboardUtils;
import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.bean.DoctorInfo;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.ih_common.view.GeneralDialog;
import com.keydom.ih_common.view.InterceptorEditText;
import com.keydom.mianren.ih_patient.App;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.common_document.CommonDocumentActivity;
import com.keydom.mianren.ih_patient.activity.diagnose_user_manager.AddManageUserActivity;
import com.keydom.mianren.ih_patient.activity.reserve_obstetric_hospital.controller.ReserveObstetricHospitalController;
import com.keydom.mianren.ih_patient.activity.reserve_obstetric_hospital.view.ReserveObstetricHospitalView;
import com.keydom.mianren.ih_patient.bean.CommonDocumentBean;
import com.keydom.mianren.ih_patient.bean.DepartmentInfo;
import com.keydom.mianren.ih_patient.bean.Event;
import com.keydom.mianren.ih_patient.bean.MedicalCardInfo;
import com.keydom.mianren.ih_patient.constant.EventType;
import com.keydom.mianren.ih_patient.utils.DateUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * @author ??????
 * @date 20/2/25 10:49
 * @des ????????????
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
    @BindView(R.id.et_fetus)
    InterceptorEditText tvFetus;
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

    private MedicalCardInfo medicalCardInfo;

    private String reserveDate, bed, age, lastDate, dueDate, phone;
    private String fetus;

    /**
     * ??????
     */
    private DepartmentInfo curDepart;
    private List<DepartmentInfo> departmentList;
    private List<String> departName = new ArrayList<>();
    /**
     * ?????????
     */
    private DoctorInfo anesthesiologistDoctor;
    private List<DoctorInfo> anesthesiologistList;
    private List<String> anesthesiologistName = new ArrayList<>();
    /**
     * ????????????
     */
    private DoctorInfo operationDoctor;
    private List<DoctorInfo> operationList;
    private List<String> operationName = new ArrayList<>();

    /**
     * ??????
     */
    private List<String> bedGradeDit = new ArrayList<>();
    /**
     * ??????
     */
    private List<String> fetusDit = new ArrayList<>();


    /**
     * ??????id
     */
    public static final String DEPART_ID = "376";

    /**
     * ????????????
     */
    public static final int OPERATION_DOCTOR = 0;
    /**
     * ?????????
     */
    public static final int ANESTHESIOLOGIST_DOCTOR = 1;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_reserve_obstetric_hospital;
    }

    /**
     * ??????
     */
    public static void start(Context context) {
        context.startActivity(new Intent(context, ReserveObstetricHospitalActivity.class));
    }

    @Override
    protected void onResume() {
        super.onResume();
        getController().queryAllCard();
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        setTitle(getString(R.string.txt_obstetric_hospital_reserve));
        setRightTxt("????????????");
        setRightBtnListener(getController());

        bedGradeDit.add("?????????");
        bedGradeDit.add("?????????");
        bedGradeDit.add("?????????");
        bedGradeDit.add("?????????");

        fetusDit.add("??????");
        fetusDit.add("??????");
        fetusDit.add("??????");

        txNext.setOnClickListener(getController());

        getController().getObstetricDepart();
    }

    @OnClick({R.id.layout_hospital_date, R.id.layout_bed, R.id.layout_depart, R.id.layout_doctor,
            R.id.layout_anesthetist, R.id.layout_visit, R.id.layout_menstruation,
            R.id.layout_due_date, R.id.layout_fetus, R.id.layout_select, R.id.tv_note})
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
                            curDepart = departmentList.get(options1);
                            initReserveView();
                        }).build();
                depart.setPicker(departName);
                depart.show();
                break;
            case R.id.layout_doctor:
                if (curDepart != null) {
                    if (operationName.size() > 0) {
                        OptionsPickerView doctorPicker = new OptionsPickerBuilder(getContext(),
                                (options1, option2, options3, v) -> {
                                    operationDoctor = operationList.get(options3);
                                    tvDoctor.setText(operationDoctor.getName());
                                }).build();
                        doctorPicker.setPicker(operationName);
                        doctorPicker.show();
                    } else {
                        ToastUtil.showMessage(this, "??????????????????");
                    }
                } else {
                    ToastUtil.showMessage(this, "??????????????????");
                }
                break;
            case R.id.layout_anesthetist:
                if (curDepart != null) {
                    if (anesthesiologistName.size() > 0) {
                        OptionsPickerView anesthetistPicker = new OptionsPickerBuilder(getContext(),
                                (options1, option2, options3, v) -> {
                                    anesthesiologistDoctor = anesthesiologistList.get(options3);
                                    tvDoctor.setText(anesthesiologistDoctor.getName());
                                }).build();
                        anesthetistPicker.setPicker(anesthesiologistName);
                        anesthetistPicker.show();
                    } else {
                        ToastUtil.showMessage(this, "???????????????");
                    }
                } else {
                    ToastUtil.showMessage(this, "??????????????????");
                }
                break;
            case R.id.layout_visit:
                //ChoosePatientActivity.start(this, -1, false);
                break;
            case R.id.layout_menstruation:
                KeyboardUtils.hideSoftInput(this);
                Calendar end = Calendar.getInstance();
                Calendar start = Calendar.getInstance();
                end.setTime(new Date());
                start.add(Calendar.MONTH, -12);
                pickerView = new TimePickerBuilder(this,
                        (date, v1) -> {
                            lastDate = DateUtils.dateToString(date);
                            tvLast.setText(lastDate);
                        }).setRangDate(start, end).build();
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
            //            case R.id.layout_fetus:
            //                OptionsPickerView fetusPicker = new OptionsPickerBuilder(getContext(),
            //                        (options1, option2, options3, v) -> {
            //                            fetus = options1;
            //                            tvFetus.setText(fetusDit.get(options1));
            //                        }).build();
            //                fetusPicker.setPicker(fetusDit);
            //                fetusPicker.show();
            //                break;
            case R.id.layout_select:
                if (ivSelect.getVisibility() == View.VISIBLE) {
                    ivSelect.setVisibility(View.GONE);
                } else {
                    ivSelect.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.tv_note:
                CommonDocumentActivity.start(this, CommonDocumentBean.CODE_3);
                break;
            default:
                break;
        }
    }


    /**
     * ????????????
     */
    private void initReserveView() {
        if (DEPART_ID.equals(curDepart.getId())) {
            layoutObstetric.setVisibility(View.VISIBLE);
        } else {
            layoutObstetric.setVisibility(View.GONE);
        }

        tvDepart.setText(curDepart.getName());
        getController().getDeptDoctor(curDepart.getId(), OPERATION_DOCTOR);
        getController().getDeptDoctor(curDepart.getId(), ANESTHESIOLOGIST_DOCTOR);
    }

    /**
     * ???????????????
     */
    private void bindVisitData() {
        if (medicalCardInfo != null) {
            tvVisitName.setText(medicalCardInfo.getName());
            if (!TextUtils.isEmpty(medicalCardInfo.getAge())) {
                age = medicalCardInfo.getAge().replace("???", "");
            }
            etAge.setText(age);
            etPhone.setText(medicalCardInfo.getPhoneNumber());
        }
    }

    /**
     * ?????????????????????
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getPatientCard(Event event) {
        if (event.getType() == EventType.SENDSELECTNURSINGPATIENT) {
            medicalCardInfo = (MedicalCardInfo) event.getData();
            bindVisitData();
        }
    }

    @Override
    public void getAllCardSuccess(List<MedicalCardInfo> dataList) {
        //???????????????????????????????????????
        for (MedicalCardInfo info : dataList) {
            if (App.userInfo.getIdCard().equals(info.getIdCard())) {
                medicalCardInfo = info;
                bindVisitData();
                break;
            }
        }
        if (medicalCardInfo == null) {
            new GeneralDialog(this, "?????????????????????????????????", new GeneralDialog.OnCloseListener() {
                @Override
                public void onCommit() {
                    Intent i = new Intent(getContext(), AddManageUserActivity.class);
                    i.putExtra(AddManageUserActivity.TYPE, AddManageUserActivity.ADD);
                    i.putExtra(AddManageUserActivity.ELECTRONIC_CARD, true);
                    ActivityUtils.startActivity(i);
                }
            }).setPositiveButton("?????????").setNegativeButtonIsGone(true).show();
        }
    }

    @Override
    public void getAllCardFailed(String errMsg) {
        ToastUtil.showMessage(getContext(), errMsg);
    }

    @Override
    public void requestDepartSuccess(List<DepartmentInfo> data) {
        departmentList = data;
        departName.clear();
        if (departmentList != null) {
            for (DepartmentInfo info : departmentList) {
                if (TextUtils.equals(info.getId(), DEPART_ID)) {
                    curDepart = info;
                    initReserveView();
                }
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
        ToastUtil.showMessage(this, "????????????");
        ObstetricHospitalListActivity.start(getContext());
        finish();
    }

    @Override
    public Map<String, Object> getParams() {
        Map<String, Object> map = new HashMap<>(20);
        map.put("age", age + "???");
        map.put("appointmentTime", reserveDate);
        map.put("bed", bed);
        map.put("deptId", curDepart.getId());
        map.put("deptName", curDepart.getName());
        if (layoutObstetric.getVisibility() == View.VISIBLE) {
            map.put("embryoNumber", fetus);
            map.put("lastMenstrualPeriodTime", lastDate);
            map.put("expectedDateOfConfinement", dueDate);
        }
        map.put("eleCardNumber", medicalCardInfo.getEleCardNumber());
        map.put("patientId", medicalCardInfo.getId());
        map.put("patientName", medicalCardInfo.getName());
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
        fetus = tvFetus.getText().toString();
        if (TextUtils.isEmpty(reserveDate) || TextUtils.isEmpty(bed) || TextUtils.isEmpty(fetus) || TextUtils.isEmpty(lastDate) || TextUtils.isEmpty(dueDate) || curDepart == null || medicalCardInfo == null) {
            ToastUtil.showMessage(this, "?????????????????????");
            return false;
        }
        if (!ivSelect.isShown()) {
            ToastUtil.showMessage(this, "??????????????????????????????????????????");
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
