package com.keydom.mianren.ih_patient.view;

import android.animation.Animator;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatDialog;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.ih_common.view.InterceptorEditText;
import com.keydom.ih_common.view.picker.listener.AbstractAnimationListener;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.bean.HealthDataBean;

import static com.keydom.mianren.ih_patient.bean.entity.ChronicDisease.CHRONIC_DISEASE_CARDIOVASCULAR;
import static com.keydom.mianren.ih_patient.bean.entity.ChronicDisease.CHRONIC_DISEASE_DIABETES;
import static com.keydom.mianren.ih_patient.bean.entity.ChronicDisease.CHRONIC_DISEASE_HYPERTENSION;

/**
 * 填写健康数据
 *
 * @author 顿顿
 */
public class HealthDataEditDialog extends AppCompatDialog implements View.OnClickListener {
    private TextView hintTv, cancelTv, submitTv;
    private ImageView cancelIv;
    private TextView oneTitleTv, twoTitleTv, threeTitleTv, fourTitleTv;
    private InterceptorEditText oneValueTv, twoValueTv, threeValueTv, fourValueTv;
    private TextView oneUnitTv, twoUnitTv, threeUnitTv, fourUnitTv;
    private LinearLayout oneLayout, twoLayout, threeLayout, fourLayout;
    private TextView healthDataReferenceTv;
    private LinearLayout bgLayout, contentLayout;
    private Context context;
    private OnCommitListener onCommitListener;

    private HealthDataBean dataBean;

    /**
     * 慢病种类
     */
    private int type;

    private static int DURATION_TIME = 300;

    private boolean IS_ANIMING = false;

    /**
     * 构建方法
     */
    public HealthDataEditDialog(@NonNull Context context, HealthDataBean dataBean, int type,
                                OnCommitListener listener) {
        super(context, com.keydom.ih_common.R.style.trans_dialog);
        this.context = context;
        this.type = type;
        this.dataBean = dataBean;
        onCommitListener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //状态栏透明
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.health_data_dialog_layout);
        setCanceledOnTouchOutside(false);
        initView();
        initUi();
    }

    /**
     * 初始化
     */
    private void initView() {
        hintTv = findViewById(R.id.health_data_hint_tv);
        cancelIv = findViewById(R.id.health_data_cancel_iv);
        cancelTv = findViewById(R.id.health_data_cancel_tv);
        submitTv = findViewById(R.id.health_data_submit_tv);
        bgLayout = findViewById(R.id.health_data_dialog_bg_layout);
        contentLayout = findViewById(R.id.health_data_dialog_content_layout);

        oneLayout = findViewById(R.id.health_data_one_layout);
        twoLayout = findViewById(R.id.health_data_two_layout);
        threeLayout = findViewById(R.id.health_data_three_layout);
        fourLayout = findViewById(R.id.health_data_four_layout);

        oneTitleTv = findViewById(R.id.health_data_one_title_tv);
        twoTitleTv = findViewById(R.id.health_data_two_title_tv);
        threeTitleTv = findViewById(R.id.health_data_three_title_tv);
        fourTitleTv = findViewById(R.id.health_data_four_title_tv);

        oneValueTv = findViewById(R.id.health_data_one_value_tv);
        twoValueTv = findViewById(R.id.health_data_two_value_tv);
        threeValueTv = findViewById(R.id.health_data_three_value_tv);
        fourValueTv = findViewById(R.id.health_data_four_value_tv);

        oneUnitTv = findViewById(R.id.health_data_one_unit_tv);
        twoUnitTv = findViewById(R.id.health_data_two_unit_tv);
        threeUnitTv = findViewById(R.id.health_data_three_unit_tv);
        fourUnitTv = findViewById(R.id.health_data_four_unit_tv);

        healthDataReferenceTv = findViewById(R.id.health_data_reference_tv);

        cancelIv.setOnClickListener(this);
        cancelTv.setOnClickListener(this);
        submitTv.setOnClickListener(this);
        startAnim();
    }

    private void initUi() {
        switch (type) {
            case CHRONIC_DISEASE_CARDIOVASCULAR:
                oneTitleTv.setText("总胆固醇");
                twoTitleTv.setText("甘油三酯");
                threeTitleTv.setText("高密度脂蛋白胆固醇");
                fourTitleTv.setText("低密度脂蛋白胆固醇");
                oneUnitTv.setText("mmol/L");
                twoUnitTv.setText("mmol/L");
                threeUnitTv.setText("mmol/L");
                fourUnitTv.setText("mmol/L");
                if (dataBean != null) {
                    if (dataBean.getCholesterol() > 0) {
                        oneValueTv.setText(String.valueOf(dataBean.getCholesterol()));
                    }
                    if (dataBean.getTriglycerides() > 0) {
                        twoValueTv.setText(String.valueOf(dataBean.getTriglycerides()));
                    }
                    if (dataBean.getHighDensityLipoproteinCholesterol() > 0) {
                        threeValueTv.setText(String.valueOf(dataBean.getHighDensityLipoproteinCholesterol()));
                    }
                    if (dataBean.getLowDensityLipoproteinCholesterol() > 0) {
                        fourValueTv.setText(String.valueOf(dataBean.getLowDensityLipoproteinCholesterol()));
                    }
                }
                healthDataReferenceTv.setText(R.string.txt_data_cardiovascular_value);
                break;
            case CHRONIC_DISEASE_HYPERTENSION:
                fourLayout.setVisibility(View.GONE);
                if (dataBean != null) {
                    if (dataBean.getSystolicPressure() > 0) {
                        oneValueTv.setText(String.valueOf(dataBean.getSystolicPressure()));
                    }
                    if (dataBean.getDiastolicPressure() > 0) {
                        twoValueTv.setText(String.valueOf(dataBean.getDiastolicPressure()));
                    }
                    if (dataBean.getHeartRateValue() > 0) {
                        threeValueTv.setText(String.valueOf(dataBean.getHeartRateValue()));
                    }
                }
                healthDataReferenceTv.setText(R.string.txt_data_refer_value);
                break;
            case CHRONIC_DISEASE_DIABETES:
                oneTitleTv.setText("血糖");
                oneUnitTv.setText("mmol/L");
                if (dataBean != null) {
                    if (dataBean.getBloodSugar() > 0) {
                        oneValueTv.setText(String.valueOf(dataBean.getBloodSugar()));
                    }
                }
                twoLayout.setVisibility(View.GONE);
                threeLayout.setVisibility(View.GONE);
                fourLayout.setVisibility(View.GONE);
                healthDataReferenceTv.setText(R.string.txt_data_blood_pressure);
                break;
            default:
                break;
        }
    }


    private void startAnim() {
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.actionsheet_dialog_in);
        animation.setDuration(DURATION_TIME);
        animation.setAnimationListener(new AbstractAnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                contentLayout.setVisibility(View.VISIBLE);
            }
        });
        contentLayout.startAnimation(animation);

        Animation alphaAnimation = AnimationUtils.loadAnimation(context, R.anim.fade_in);
        alphaAnimation.setDuration(DURATION_TIME);
        bgLayout.startAnimation(alphaAnimation);
    }


    private void endAnim() {
        if (IS_ANIMING) {
            return;
        }
        IS_ANIMING = true;
        contentLayout.animate().translationY(contentLayout.getHeight()).setDuration(DURATION_TIME).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                contentLayout.setVisibility(View.GONE);
                IS_ANIMING = false;
                dismiss();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        }).start();

        bgLayout.animate().alpha(0).setDuration(DURATION_TIME).start();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.health_data_cancel_iv:
            case R.id.health_data_cancel_tv:
                break;
            case R.id.health_data_submit_tv:
                if (onCommitListener != null) {
                    if (dataBean == null) {
                        dataBean = new HealthDataBean();
                    }
                    String oneValue = oneValueTv.getText().toString();
                    String twoValue = twoValueTv.getText().toString();
                    String threeValue = threeValueTv.getText().toString();
                    String fourValue = fourValueTv.getText().toString();
                    switch (type) {
                        case CHRONIC_DISEASE_CARDIOVASCULAR:
                            if (TextUtils.isEmpty(oneValue) || TextUtils.isEmpty(twoValue)
                                    || TextUtils.isEmpty(threeValue) || TextUtils.isEmpty(fourValue)) {
                                ToastUtil.showMessage(context, "请完善以上信息");
                                return;
                            }
                            dataBean.setCholesterol(Float.parseFloat(oneValue));
                            dataBean.setTriglycerides(Float.parseFloat(twoValue));
                            dataBean.setHighDensityLipoproteinCholesterol(Float.parseFloat(threeValue));
                            dataBean.setLowDensityLipoproteinCholesterol(Float.parseFloat(fourValue));
                            break;
                        case CHRONIC_DISEASE_HYPERTENSION:
                            if (TextUtils.isEmpty(oneValue) || TextUtils.isEmpty(twoValue)
                                    || TextUtils.isEmpty(threeValue)) {
                                ToastUtil.showMessage(context, "请完善以上信息");
                                return;
                            }
                            dataBean.setSystolicPressure(Integer.parseInt(oneValue));
                            dataBean.setDiastolicPressure(Integer.parseInt(twoValue));
                            dataBean.setHeartRateValue(Integer.parseInt(threeValue));
                            break;
                        case CHRONIC_DISEASE_DIABETES:
                            if (TextUtils.isEmpty(oneValue)) {
                                ToastUtil.showMessage(context, "请完善以上信息");
                                return;
                            }
                            dataBean.setBloodSugar(Float.parseFloat(oneValue));
                            break;
                        default:
                            break;
                    }
                    onCommitListener.commitData(dataBean);
                }
                break;
            default:
                break;
        }
        endAnim();
    }

    @Override
    public void onBackPressed() {
        endAnim();
    }

    /**
     * 提交监听
     */
    public interface OnCommitListener {
        void commitData(HealthDataBean bean);
    }
}
