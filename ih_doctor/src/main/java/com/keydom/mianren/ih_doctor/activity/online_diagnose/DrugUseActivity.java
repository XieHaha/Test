package com.keydom.mianren.ih_doctor.activity.online_diagnose;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.blankj.utilcode.util.ActivityUtils;
import com.keydom.ih_common.AbsTextWatcher;
import com.keydom.ih_common.base.BaseActivity;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.ih_common.view.InterceptorEditText;
import com.keydom.mianren.ih_doctor.R;
import com.keydom.mianren.ih_doctor.bean.DrugBean;
import com.keydom.mianren.ih_doctor.bean.DrugListBean;
import com.keydom.mianren.ih_doctor.bean.DrugUseConfigBean;
import com.keydom.mianren.ih_doctor.bean.Event;
import com.keydom.mianren.ih_doctor.bean.FrequencyBean;
import com.keydom.mianren.ih_doctor.bean.UseWayBean;
import com.keydom.mianren.ih_doctor.constant.Const;
import com.keydom.mianren.ih_doctor.constant.EventType;
import com.keydom.mianren.ih_doctor.m_interface.SingleClick;
import com.keydom.mianren.ih_doctor.net.PrescriptionService;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @link Author: song
 * <p>
 * Create: 19/3/7 上午10:46
 * <p>
 * Changes (from 19/3/7)
 * <p>
 * 19/3/7 : Create DrugUseActivity.java (song);
 */
public class DrugUseActivity extends BaseActivity {
    @BindView(R.id.medical_name_tv)
    TextView medicalNameTv;
    @BindView(R.id.medical_desc_tv)
    TextView medicalDescTv;
    @BindView(R.id.get_medical_way_tv)
    TextView getMedicalWayTv;
    @BindView(R.id.medical_dosage_scaler_minus_layout)
    RelativeLayout medicalDosageScalerMinusLayout;
    @BindView(R.id.medical_dosage_scaler_text_layout)
    EditText medicalDosageScalerTextLayout;
    @BindView(R.id.medical_dosage_scaler_add_layout)
    RelativeLayout medicalDosageScalerAddLayout;
    @BindView(R.id.medical_dosage_sacler)
    LinearLayout medicalDosageSacler;
    @BindView(R.id.dosage_unit_tv)
    TextView dosageUnitTv;
    @BindView(R.id.eat_medical_rate_tv)
    TextView eatMedicalRateTv;
    @BindView(R.id.eat_medical_day_scaler_minus_layout)
    RelativeLayout eatMedicalDayScalerMinusLayout;
    @BindView(R.id.eat_medical_day_scaler_text_layout)
    EditText eatMedicalDayScalerTextLayout;
    @BindView(R.id.eat_medical_day_scaler_add_layout)
    RelativeLayout eatMedicalDayScalerAddLayout;
    @BindView(R.id.eat_medical_day_sacler)
    LinearLayout eatMedicalDaySacler;
    @BindView(R.id.medical_num_scaler_text_layout)
    EditText medicalNumScalerTextLayout;
    @BindView(R.id.medical_num_sacler)
    LinearLayout medicalNumSacler;
    @BindView(R.id.eat_medical_day_labei)
    TextView eatMedicalDayLabei;
    @BindView(R.id.medical_num_scaler_minus_layout)
    RelativeLayout medicalNumScalerMinusLayout;
    @BindView(R.id.medical_num_scaler_add_layout)
    RelativeLayout medicalNumScalerAddLayout;
    @BindView(R.id.doctor_entrust)
    InterceptorEditText doctorEntrust;
    @BindView(R.id.save_drug)
    TextView saveDrug;
    private DrugListBean drugListBean;
    /**
     * 用法用量
     */
    private DrugUseConfigBean configBean;
    /**
     * 当前药品
     */
    private DrugBean drugBean;
    private DecimalFormat df1 = new DecimalFormat("#.#");
    /**
     * 用法途径数据
     */
    private List<UseWayBean> useWayBeans;
    /**
     * 用药频率
     */
    private List<FrequencyBean> frequencyBeans;

    /**
     * 启动药品规格、用法设置页面
     *
     * @param drugListBean 选中的药品
     */
    public static void start(Context context, DrugListBean drugListBean) {
        Intent intent = new Intent(context, DrugUseActivity.class);
        intent.putExtra(Const.DATA, drugListBean);
        context.startActivity(intent);
    }


    @Override
    public int getLayoutRes() {
        return R.layout.activity_medicine_dosage_layout;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        drugListBean = (DrugListBean) getIntent().getSerializableExtra(Const.DATA);
        drugBean = drugListBean.getDrugList().get(0);
        setTitle("用法用量");
        getAllDrugsFrequencyList();

        //提交
        saveDrug.setOnClickListener(new View.OnClickListener() {
            @SingleClick(1000)
            @Override
            public void onClick(View v) {
                if (checkSubmit()) {
                    Event event = new Event(EventType.CHOOSE_DRUG_LIST, drugListBean);
                    EventBus.getDefault().post(event);
                    ActivityUtils.finishActivity(DrugChooseActivity.class);
                    finish();
                } else {
                    ToastUtil.showMessage(DrugUseActivity.this, "请完善药品信息");
                }
            }
        });
    }

    private void bindData() {
        double dose;
        if (drugBean.getSingleDose() != 0) {
            dose = drugBean.getSingleDose();
        } else {
            if (drugBean.getDosage() != null && Double.valueOf(drugBean.getDosage()) != 0) {
                dose = Double.valueOf(drugBean.getDosage());
            } else {
                dose = Double.valueOf(drugBean.getSingleMaximum());
            }
        }
        medicalNameTv.setText(drugBean.getDrugsName());
        medicalDescTv.setText(drugBean.getSpec());
        medicalNumScalerTextLayout.setText(String.valueOf(drugBean.getQuantity()));
        getMedicalWayTv.setText(drugBean.getWay());
        ArrayList<String> ways = new ArrayList<>();
        for (UseWayBean bean : useWayBeans) {
            ways.add(bean.getCodeValue());
            //当用法不为空时，需要轮训处与其对应的用法code
            if (TextUtils.equals(bean.getCodeValue(), drugBean.getWay())) {
                drugBean.setWayCode(bean.getCode());
            }
        }

        medicalDosageScalerTextLayout.setText(df1.format(dose));
        dosageUnitTv.setText(drugBean.getDosageUnit());
        eatMedicalRateTv.setText(drugBean.getFrequency());
        eatMedicalDayScalerTextLayout.setText(String.valueOf(drugBean.getDays()));
        doctorEntrust.setText(drugBean.getDoctorAdvice());

        drugBean.setSingleDose(Double.parseDouble(df1.format(dose)));

        //用法用量
        medicalDosageScalerTextLayout.addTextChangedListener(new AbsTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s.toString())) {
                    if (s.toString().contains(".")) {
                        if (s.toString().indexOf(".") == s.toString().length() - 1) {

                        } else if (s.toString().indexOf(".") == s.toString().length() - 3) {
                            ToastUtil.showMessage(DrugUseActivity.this, "仅支持输入一位小数");
                            String value = s.toString();
                            value = value.substring(0, s.toString().indexOf(".") + 2);
                            s.replace(0, s.length(), value);
                        } else {
                            drugBean.setSingleDose(Double.parseDouble(df1.format(Double.valueOf(s.toString()))));
                            drugBean.setQuantity(computeDosage(drugBean));
                        }
                    } else {
                        String s1 = s.toString();
                        if (TextUtils.isEmpty(s1)) {
                            return;
                        }
                        drugBean.setSingleDose(Double.parseDouble(df1.format(Double.valueOf(s.toString()))));
                        drugBean.setQuantity(computeDosage(drugBean));
                    }
                }
            }
        });

        //用药天数
        eatMedicalDayScalerTextLayout.addTextChangedListener(new AbsTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s.toString())) {
                    drugBean.setDays(Integer.valueOf(s.toString()));
                    drugBean.setQuantity(computeDosage(drugBean));
                }

            }
        });

        //医嘱事项
        doctorEntrust.addTextChangedListener(new AbsTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                drugBean.setDoctorAdvice(s.toString());
            }
        });

        //单次剂量 减号
        medicalDosageScalerMinusLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drugBean.getSingleDose() > 0.1) {
                    drugBean.setSingleDose(Double.parseDouble(df1.format(drugBean.getSingleDose() - 0.1)));
                    medicalDosageScalerTextLayout.setText(String.valueOf(drugBean.getSingleDose()));
                }
            }
        });

        //单次剂量 加号
        medicalDosageScalerAddLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drugBean.setSingleDose(Double.parseDouble(df1.format(drugBean.getSingleDose() + 0.1)));
                drugBean.setQuantity(computeDosage(drugBean));
                medicalDosageScalerTextLayout.setText(String.valueOf(drugBean.getSingleDose()));
            }
        });

        // 用药天数  减
        eatMedicalDayScalerMinusLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drugBean.getDays() > 0) {
                    drugBean.setDays(drugBean.getDays() - 1);
                    eatMedicalDayScalerTextLayout.setText(String.valueOf(drugBean.getDays()));
                }
            }
        });
        // 用药天数  加
        eatMedicalDayScalerAddLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drugBean.setDays(drugBean.getDays() + 1);
                eatMedicalDayScalerTextLayout.setText(String.valueOf(drugBean.getDays()));
            }
        });
        // 用药总量  减
        medicalNumScalerMinusLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drugBean.getQuantity() > 0) {
                    drugBean.setQuantity(drugBean.getQuantity() - 1);
                    medicalNumScalerTextLayout.setText(String.valueOf(drugBean.getQuantity()));
                }
            }
        });
        // 用药总量 加
        medicalNumScalerAddLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drugBean.setQuantity(drugBean.getQuantity() + 1);
                medicalNumScalerTextLayout.setText(String.valueOf(drugBean.getQuantity()));
            }
        });

        //用药频率
        OptionsPickerView frequencyPickerView = new OptionsPickerBuilder(DrugUseActivity.this,
                (options1, option2, options3, v) -> {
                    drugBean.setFrequency(frequencyBeans.get(options1).getFreqId());
                    drugBean.setFrequencyEnglish(frequencyBeans.get(options1).getName());
                    eatMedicalRateTv.setText(frequencyBeans.get(options1).getName());

                }).build();
        ArrayList<String> frequencys = new ArrayList<>();
        for (FrequencyBean bean : configBean.getFrequencyList()) {
            frequencys.add(bean.getName());
        }
        frequencyPickerView.setPicker(frequencys);
        eatMedicalRateTv.setOnClickListener(v -> frequencyPickerView.show());

        //给药途径
        OptionsPickerView wayPickerView = new OptionsPickerBuilder(DrugUseActivity.this,
                (options1, option2, options3, v) -> {
                    drugBean.setWay(useWayBeans.get(options1).getCodeValue());
                    drugBean.setWayCode(useWayBeans.get(options1).getCode());
                    getMedicalWayTv.setText(useWayBeans.get(options1).getCodeValue());
                }).build();

        wayPickerView.setPicker(ways);
        getMedicalWayTv.setOnClickListener(v -> wayPickerView.show());
    }

    private int computeDosage(DrugBean item) {
        if (item.getRate() == 0) {
            return item.getQuantity();
        }
        return Double.valueOf(Math.ceil(item.getSingleDose() * item.getTimes() * item.getDays() / item.getRate())).intValue();
    }

    /**
     * 判断是否可以提交
     *
     * @return true 可以提交，false 不能提交
     */
    private boolean checkSubmit() {
        for (DrugBean bean : drugListBean.getDrugList()) {
            if (TextUtils.isEmpty(bean.getWay()) || bean.getDays() == 0 || bean.getQuantity() == 0 || bean.getSingleDose() == 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * 获取药品用法等配置信息
     */
    private void getAllDrugsFrequencyList() {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(PrescriptionService.class).getAllDrugsFrequencyList(), new HttpSubscriber<DrugUseConfigBean>(this, getDisposable(), true) {
            @Override
            public void requestComplete(@Nullable DrugUseConfigBean data) {
                if (data == null) {
                    return;
                }
                configBean = data;
                useWayBeans = configBean.getWayList();
                frequencyBeans = configBean.getFrequencyList();
                bindData();
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code,
                                        @NotNull String msg) {
                return super.requestError(exception, code, msg);

            }
        });
    }
}
