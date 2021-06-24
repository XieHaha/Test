package com.keydom.mianren.ih_doctor.activity.online_diagnose;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
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
import com.keydom.ih_common.utils.ArithUtil;
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

import java.math.BigDecimal;
import java.math.RoundingMode;
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
    @BindView(R.id.drug_use_time_tv)
    TextView drugUseTimeTv;
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
    private DecimalFormat df1 = new DecimalFormat("#.##");
    /**
     * 用药天数
     */
    private int days;
    /**
     * 总量计算参数
     */
    private float freqCn, freqDeg, rate, amount;
    /**
     * 药品库存
     */
    private float drugStock;
    /**
     * 用药时长
     */
    private String freqUnit = "";
    /**
     * 用法途径数据
     */
    private List<UseWayBean> useWayBeans;
    /**
     * 当前选中的用药途径
     */
    private UseWayBean curUseWayBean;
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
        try {
            if (!TextUtils.isEmpty(drugBean.getStock())) {
                drugStock = Float.valueOf(drugBean.getStock());
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        drugItemPackUnit.setText(drugBean.getPackUnit());
        medicalNameTv.setText(drugBean.getDrugsName());
        medicalDescTv.setText(drugBean.getSpec());
        medicalNumScalerTextLayout.setText(String.valueOf(drugBean.getQuantity()));
        getMedicalWayTv.setText(drugBean.getWay());
        ways = new ArrayList<>();
        for (UseWayBean bean : useWayBeans) {
            ways.add(bean.getCodeName() + ":" + bean.getCodeValue());
            //当用法不为空时，需要轮训处与其对应的用法code
            if (TextUtils.equals(bean.getCodeValue(), drugBean.getWay())) {
                drugBean.setWayCode(bean.getCode());
                drugBean.setWayEnglish(bean.getCodeName());
            }
        }
        medicalDosageScalerTextLayout.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        dosageUnitTv.setText(drugBean.getBasicUnit());
        eatMedicalRateTv.setText(drugBean.getFrequency());
        eatMedicalDayScalerTextLayout.setText(String.valueOf(drugBean.getDays()));
        doctorEntrust.setText(drugBean.getDoctorAdvice());

        rate = drugBean.getRate();
        days = drugBean.getDays();
        amount = drugBean.getAmount();

        drugUseTotalMode();
        drugUseWay();
        drugUseSingle();
        drugUseDays();
        drugUseFrequency();
        drugUseTotal();
    }

    /**
     * 给药途径
     */
    private void drugUseWay() {
        OptionsPickerView wayPickerView = new OptionsPickerBuilder(DrugUseActivity.this,
                (options1, option2, options3, v) -> {
                    curUseWayBean = useWayBeans.get(options1);
                    getMedicalWayTv.setText(curUseWayBean.getCodeName() + ":" + curUseWayBean.getCodeValue());
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
                    drugBean.setFrequency(curFrequencyBean.getName());
                    drugBean.setFrequencyEnglish(curFrequencyBean.getFreqId());
                    eatMedicalRateTv.setText(curFrequencyBean.getFreqId());
                    //判断发药量模式
                    drugUseTotalMode();
                }).build();
        ArrayList<String> frequencies = new ArrayList<>();
        for (FrequencyBean bean : configBean.getFrequencyList()) {
            frequencies.add(bean.getFreqId());
        }
        frequencyPickerView.setPicker(frequencies);
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
                if (value.contains(".")) {
                    if (value.indexOf(".") == value.length() - 4) {
                        ToastUtil.showMessage(DrugUseActivity.this, "最多支持输入两位小数");
                        value = value.substring(0, value.indexOf(".") + 3);
                        s.replace(0, s.length(), value);
                        amount = Float.valueOf(value);
                        computeDosage();
                    } else {
                        amount = Float.valueOf(value);
                        computeDosage();
                    }
                } else if (!TextUtils.isEmpty(value)) {
                    amount = Float.valueOf(value);
                    computeDosage();
                }
            }
        });

        //单次剂量 减号
        medicalDosageScalerMinusLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (amount > 0) {
                    amount -= 0.01;
                    if (amount < 0) {
                        amount = 0;
                    }
                    medicalDosageScalerTextLayout.setText(df1.format(amount));
                }
            }
        });

        //单次剂量 加号
        medicalDosageScalerAddLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amount += 0.01;
                medicalDosageScalerTextLayout.setText(df1.format(amount));
            }
        });
    }

    /**
     * 用药时长
     */
    private void drugUseDays() {
        eatMedicalDayScalerTextLayout.addTextChangedListener(new AbsTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                String value = s.toString().trim();
                if (!TextUtils.isEmpty(value)) {
                    computeDosage();
                }
            }
        });

        // 用药天数  减
        eatMedicalDayScalerMinusLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (days > 0) {
                    days--;
                    eatMedicalDayScalerTextLayout.setText(String.valueOf(days));
                }
            }
        });
        // 用药天数  加
        eatMedicalDayScalerAddLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                days++;
                eatMedicalDayScalerTextLayout.setText(String.valueOf(days));
            }
        });
    }

    /**
     * 用药总量
     */
    private void drugUseTotal() {
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
     * 总量自动计算模式 默认自动计算
     */
    private boolean autoDrugUseMode = true;

    /**
     * 计算总量mode
     */
    private void drugUseTotalMode() {
        //T表示不自动计算总量
        if (curFrequencyBean != null) {
            freqUnit = curFrequencyBean.getFreqUnit();
            autoDrugUseMode = !"T".equalsIgnoreCase(freqUnit);
            if (autoDrugUseMode) {
                freqCn = Float.valueOf(curFrequencyBean.getFreqCn());
                freqDeg = Float.valueOf(curFrequencyBean.getFreqDeg());
                computeDosage();
            }
        }
        medicalNumScalerMinusLayout.setVisibility(autoDrugUseMode ? View.GONE : View.VISIBLE);
        medicalNumScalerAddLayout.setVisibility(autoDrugUseMode ? View.GONE : View.VISIBLE);
        medicalNumScalerTextLayout.setBackgroundResource(autoDrugUseMode ?
                R.drawable.corner5_f9f9f9_bg : R.drawable.scaler_text_bg);
        medicalNumScalerTextLayout.setFocusable(!autoDrugUseMode);
        medicalNumScalerTextLayout.setFocusableInTouchMode(!autoDrugUseMode);
        switch (freqUnit) {
            case "H":
                drugUseTimeTv.setText("小时");
                break;
            case "W":
                drugUseTimeTv.setText("周");
                break;
            case "D":
            case "T":
                drugUseTimeTv.setText("天");
                break;
            default:
                drugUseTimeTv.setText("天");
                break;
        }
    }

    /**
     * 总量计算
     * Math.ceil（24.1）--> 25
     * Math.floor（24.8）--> 24
     * Math.round（24.1）--> 24
     * Math.round（24.8）--> 25
     */
    private void computeDosage() {
        if (autoDrugUseMode) {
            if (days > 0 && amount > 0) {
                float value = (days / freqCn) * amount * freqDeg / rate;
//                if (drugStock > 0 && value > drugStock) {
//                    ToastUtil.showMessage(this, "库存不足！");
//                }
                medicalNumScalerTextLayout.setText(String.valueOf((int) Math.ceil(value)));
            } else {
                medicalNumScalerTextLayout.setText("0");
            }
        }
    }

    /**
     * 判断是否可以提交
     *
     * @return true 可以提交，false 不能提交
     */
    private boolean checkSubmit() {
        String quantity = medicalNumScalerTextLayout.getText().toString().trim();
        if (days > 0 && amount > 0 && curUseWayBean != null && curFrequencyBean != null && !TextUtils.isEmpty(quantity)) {
            int total = Integer.valueOf(quantity);
//            if (total > drugStock) {
//                ToastUtil.showMessage(this, "库存不足！");
//                return false;
//            }
            String doctorAdvice = doctorEntrust.getText().toString().trim();
            drugBean.setWay(curUseWayBean.getCodeValue());
            drugBean.setWayCode(curUseWayBean.getCode());
            drugBean.setWayEnglish(curUseWayBean.getCodeName());
            drugBean.setDays(days);
            drugBean.setDaysUnit(freqUnit);
            drugBean.setQuantity(total);
            drugBean.setDoctorAdvice(doctorAdvice);
            drugBean.setAmount(amount);
            if (!TextUtils.isEmpty(drugBean.getSingleDosage())) {
                BigDecimal b = ArithUtil.mul(String.valueOf(amount), drugBean.getSingleDosage());
                b = b.setScale(2, RoundingMode.CEILING);
                drugBean.setDosage(b.toString());
            }
            BigDecimal price = ArithUtil.mul(quantity, drugBean.getPrice().toString());
            price = price.setScale(2, RoundingMode.CEILING);
            drugBean.setFee(price);
            return true;
        }
        return false;
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
