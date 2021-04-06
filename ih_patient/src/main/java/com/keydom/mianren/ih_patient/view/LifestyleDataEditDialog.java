package com.keydom.mianren.ih_patient.view;

import android.animation.Animator;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatDialog;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.keydom.ih_common.view.InterceptorEditText;
import com.keydom.ih_common.view.picker.listener.AbstractAnimationListener;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.bean.EatBean;
import com.keydom.mianren.ih_patient.bean.EatItemBean;
import com.keydom.mianren.ih_patient.bean.SportsItemBean;

/**
 * 填写健康数据
 *
 * @author 顿顿
 */
public class LifestyleDataEditDialog extends AppCompatDialog implements View.OnClickListener {
    private TextView hintTv, submitTv, quantityTv;
    private TextView valueAmountTv;
    private TextView valueKjTv;
    private HorizontalScaleScrollView scaleScrollView;
    private InterceptorEditText systolicEdit, diastolicEdit, rateValueEdit;
    private LinearLayout bgLayout, contentLayout;
    private Context context;
    private OnEatCommitListener onCommitListener;

    private EatItemBean eatItemBean;
    private EatBean eatBean;
    private SportsItemBean sportsItemBean;

    /**
     * 总克数  份数 热量
     */
    private int amount, kcal;
    private float copies;

    /**
     * 就餐类型 0 早餐 1午餐 2晚餐 3加餐
     */
    private int mealType = -1;
    private String patientId;
    private String curSelectDate;

    private static int DURATION_TIME = 300;

    private boolean IS_ANIMING = false;

    /**
     * 构建方法
     */
    public LifestyleDataEditDialog(@NonNull Context context, int mealType, String patientId,
                                   String curSelectDate, EatItemBean bean,
                                   OnEatCommitListener listener) {
        super(context, com.keydom.ih_common.R.style.trans_dialog);
        this.context = context;
        this.mealType = mealType;
        this.patientId = patientId;
        this.curSelectDate = curSelectDate;
        this.eatItemBean = bean;
        onCommitListener = listener;
    }

    /**
     * 构建方法
     */
    public LifestyleDataEditDialog(@NonNull Context context, SportsItemBean bean,
                                   OnEatCommitListener listener) {
        super(context, com.keydom.ih_common.R.style.trans_dialog);
        this.context = context;
        this.sportsItemBean = bean;
        onCommitListener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //状态栏透明
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.lifestyle_data_edit_dialog_layout);
        setCanceledOnTouchOutside(true);
        initView();
    }

    /**
     * 初始化
     */
    private void initView() {
        scaleScrollView = findViewById(R.id.dialog_lifestyle_data_edit_scroll_view);
        quantityTv = findViewById(R.id.dialog_lifestyle_data_edit_quantity);
        hintTv = findViewById(R.id.health_data_dialog_hint_tv);
        TextView valueOneTv = findViewById(R.id.dialog_lifestyle_data_value_one_tv);
        TextView valueTwoTv = findViewById(R.id.dialog_lifestyle_data_value_two_tv);
        TextView valueThreeTv = findViewById(R.id.dialog_lifestyle_data_value_three_tv);
        valueAmountTv = findViewById(R.id.dialog_lifestyle_data_amount_tv);
        valueKjTv = findViewById(R.id.dialog_lifestyle_data_kj_tv);
        submitTv = findViewById(R.id.health_data_dialog_submit_tv);
        bgLayout = findViewById(R.id.health_data_dialog_bg_layout);
        contentLayout = findViewById(R.id.health_data_dialog_content_layout);

        if (eatItemBean != null) {
            hintTv.setText(String.format(context.getString(R.string.txt_eat_unit),
                    eatItemBean.getCopies(), eatItemBean.getAmount()));
            valueOneTv.setText(eatItemBean.getCarbohydrate() + "克");
            valueTwoTv.setText(eatItemBean.getProtein() + "克");
            valueThreeTv.setText(eatItemBean.getFat() + "克");
            valueAmountTv.setText(String.format(context.getString(R.string.txt_gram),
                    eatItemBean.getAmount()));
            valueKjTv.setText(String.format(context.getString(R.string.txt_kcal),
                    eatItemBean.getHeat()));
        }

        bgLayout.setOnClickListener(this);
        contentLayout.setOnClickListener(this);
        submitTv.setOnClickListener(this);

        scaleScrollView.setCurScale(2);
        scaleScrollView.setOnScrollListener(new BaseScaleView.OnScrollListener() {
            @Override
            public void onScaleScroll(int scale) {
                copies = 0.5f * scale;
                amount = (int) (copies * eatItemBean.getAmount());
                kcal = (int) (copies * eatItemBean.getHeat());

                valueAmountTv.setText(String.format(context.getString(R.string.txt_gram), amount));
                valueKjTv.setText(String.format(context.getString(R.string.txt_kcal), kcal));
                quantityTv.setText(copies + "份");
            }
        });
        startAnim();
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
            case R.id.health_data_dialog_submit_tv:
                if (onCommitListener != null) {
                    eatBean = new EatBean();
                    eatBean.setAmount(amount);
                    eatBean.setCopies(copies);
                    eatBean.setCreateTime(eatItemBean.getCreateTime());
                    eatBean.setFoodId(eatItemBean.getId());
                    eatBean.setName(eatItemBean.getName());
                    eatBean.setPatientId(patientId);
                    eatBean.setRecordTime(curSelectDate);
                    eatBean.setSumHeat(kcal);
                    eatBean.setType(mealType);
                    onCommitListener.commit(eatBean);
                }
                break;
            case R.id.health_data_dialog_bg_layout:
                endAnim();
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
    public interface OnEatCommitListener {
        void commit(EatBean eatBean);
    }
}
