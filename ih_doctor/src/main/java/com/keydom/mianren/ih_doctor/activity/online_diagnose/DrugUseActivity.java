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
    @BindView(R.id.drug_item_quantity)
    TextView drugItemPackUnit;
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
    private DecimalFormat df1 = new DecimalFormat("#.###");
    private String singleDosage;
    /**
     * 用法途径数据
     */
    private List<UseWayBean> useWayBeans;
    private List<String> ways;
    /**
     * 用药频率
     */
    private List<FrequencyBean> frequencyBeans;
    /**
     * 当前选中的用药频率
     */
    private FrequencyBean curFrequencyBean;

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
        singleDosage = drugBean.getSingleDosage();
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
        drugItemPackUnit.setText(drugBean.getPackUnit());
        medicalNameTv.setText(drugBean.getDrugsName());
        medicalDescTv.setText(drugBean.getSpec());
        medicalNumScalerTextLayout.setText(String.valueOf(drugBean.getQuantity()));
        getMedicalWayTv.setText(drugBean.getWay());
        ways = new ArrayList<>();
        for (UseWayBean bean : useWayBeans) {
            ways.add(bean.getCodeValue());
            //当用法不为空时，需要轮训处与其对应的用法code
            if (TextUtils.equals(bean.getCodeValue(), drugBean.getWay())) {
                drugBean.setWayCode(bean.getCode());
            }
        }

        medicalDosageScalerTextLayout.setText("1");
        dosageUnitTv.setText(drugBean.getBasicUnit());
        eatMedicalRateTv.setText(drugBean.getFrequency());
        eatMedicalDayScalerTextLayout.setText(String.valueOf(drugBean.getDays()));
        doctorEntrust.setText(drugBean.getDoctorAdvice());

        drugUseWay();
        drugUseSingle();
        drugUseDays();
        drugUseFrequency();
        drugUseTotal();

        //医嘱事项
        doctorEntrust.addTextChangedListener(new AbsTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                drugBean.setDoctorAdvice(s.toString());
            }
        });
    }

    /**
     * 给药途径
     */
    private void drugUseWay() {
        OptionsPickerView wayPickerView = new OptionsPickerBuilder(DrugUseActivity.this,
                (options1, option2, options3, v) -> {
                    drugBean.setWay(useWayBeans.get(options1).getCodeValue());
                    drugBean.setWayCode(useWayBeans.get(options1).getCode());
                    getMedicalWayTv.setText(useWayBeans.get(options1).getCodeValue());
                }).build();

        wayPickerView.setPicker(ways);
        getMedicalWayTv.setOnClickListener(v -> wayPickerView.show());
    }

    /**
     * 用药频率
     */
    private void drugUseFrequency() {
        OptionsPickerView frequencyPickerView = new OptionsPickerBuilder(DrugUseActivity.this,
                (options1, option2, options3, v) -> {
                    curFrequencyBean = frequencyBeans.get(options1);
                    drugBean.setFrequency(curFrequencyBean.getFreqId());
                    drugBean.setFrequencyEnglish(curFrequencyBean.getName());
                    eatMedicalRateTv.setText(curFrequencyBean.getFreqId());
                    //判断发药量模式
                    drugUseTotalMode();
                }).build();
        ArrayList<String> frequencys = new ArrayList<>();
        for (FrequencyBean bean : configBean.getFrequencyList()) {
            frequencys.add(bean.getFreqId());
        }
        frequencyPickerView.setPicker(frequencys);
        eatMedicalRateTv.setOnClickListener(v -> frequencyPickerView.show());
    }

    /**
     * 单次药量
     */
    private void drugUseSingle() {
        medicalDosageScalerTextLayout.addTextChangedListener(new AbsTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                String value = s.toString().trim();
                if (!TextUtils.isEmpty(value)) {
                    if (value.contains(".")) {
                        if (value.indexOf(".") == value.length() - 1) {

                        } else if (value.indexOf(".") == value.length() - 4) {
                            ToastUtil.showMessage(DrugUseActivity.this, "最多支持输入两位小数");
                            value = value.substring(0, value.indexOf(".") + 3);
                            s.replace(0, s.length(), value);
                        } else {
                            drugBean.setSingleDosage(df1.format(Double.valueOf(value)));
                        }
                    } else {
                        drugBean.setSingleDosage(df1.format(Double.valueOf(value)));
                    }
                }
            }
        });

        //单次剂量 减号
        medicalDosageScalerMinusLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        //单次剂量 加号
        medicalDosageScalerAddLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

    /**
     * 用药天数
     */
    private void drugUseDays() {
        eatMedicalDayScalerTextLayout.addTextChangedListener(new AbsTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s.toString())) {
                    drugBean.setDays(Integer.valueOf(s.toString()));
                }

            }
        });

        // 用药天数  减
        eatMedicalDayScalerMinusLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        // 用药天数  加
        eatMedicalDayScalerAddLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

    /**
     * 用药总量
     */
    private void drugUseTotal() {
        medicalNumScalerTextLayout.addTextChangedListener(new AbsTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                super.afterTextChanged(s);
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
    }

    /**
     * 计算总量
     */
    private void drugUseTotalMode() {
        //T表示不自动计算总量
        if ("T".equalsIgnoreCase(curFrequencyBean.getFreqUnit())) {
            medicalNumScalerMinusLayout.setVisibility(View.VISIBLE);
            medicalNumScalerAddLayout.setVisibility(View.VISIBLE);
            medicalNumScalerTextLayout.setBackgroundResource(R.drawable.scaler_text_bg);
            medicalNumScalerTextLayout.setFocusable(true);
            medicalNumScalerTextLayout.setFocusableInTouchMode(true);
        } else {
            medicalNumScalerMinusLayout.setVisibility(View.GONE);
            medicalNumScalerAddLayout.setVisibility(View.GONE);
            medicalNumScalerTextLayout.setBackgroundResource(R.drawable.corner5_f9f9f9_bg);
            medicalNumScalerTextLayout.setFocusable(false);
            medicalNumScalerTextLayout.setFocusableInTouchMode(false);
        }
    }


    /**
     * 用药天数
     */
    private int days;
    /**
     *
     */
    float freqCn, freqDeg, rate, amount;

    private float computeDosage(DrugBean item) {
        if (item.getRate() == 0) {
            return item.getQuantity();
        }
        return (days / freqCn) * amount * freqDeg / rate;
    }

    /**
     * 判断是否可以提交
     *
     * @return true 可以提交，false 不能提交
     */
    private boolean checkSubmit() {
        for (DrugBean bean : drugListBean.getDrugList()) {
            if (TextUtils.isEmpty(bean.getWay()) || bean.getDays() == 0 || bean.getQuantity() == 0 || TextUtils.isEmpty(bean.getSingleDosage())) {
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
