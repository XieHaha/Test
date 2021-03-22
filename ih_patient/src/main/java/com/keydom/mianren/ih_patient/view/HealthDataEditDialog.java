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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.keydom.ih_common.view.InterceptorEditText;
import com.keydom.ih_common.view.picker.listener.AbstractAnimationListener;
import com.keydom.mianren.ih_patient.R;

/**
 * 填写健康数据
 *
 * @author 顿顿
 */
public class HealthDataEditDialog extends AppCompatDialog implements View.OnClickListener {
    private TextView hintTv, cancelTv, submitTv;
    private ImageView cancelIv;
    private InterceptorEditText systolicEdit, diastolicEdit, rateValueEdit;
    private LinearLayout bgLayout, contentLayout;
    private Context context;
    private OnCommitListener onCommitListener;

    private static int DURATION_TIME = 300;

    private boolean IS_ANIMING = false;

    /**
     * 构建方法
     */
    public HealthDataEditDialog(@NonNull Context context, OnCommitListener listener) {
        super(context, com.keydom.ih_common.R.style.trans_dialog);
        this.context = context;
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
    }

    /**
     * 初始化
     */
    private void initView() {
        hintTv = findViewById(R.id.health_data_dialog_hint_tv);
        cancelIv = findViewById(R.id.health_data_dialog_cancel_iv);
        cancelTv = findViewById(R.id.health_data_dialog_cancel_tv);
        submitTv = findViewById(R.id.health_data_dialog_submit_tv);
        bgLayout = findViewById(R.id.health_data_dialog_bg_layout);
        contentLayout = findViewById(R.id.health_data_dialog_content_layout);

        cancelIv.setOnClickListener(this);
        cancelTv.setOnClickListener(this);
        submitTv.setOnClickListener(this);
        startAnim();
    }

    private void startAnim() {
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.actionsheet_dialog_out);
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
        int i = view.getId();
        switch (view.getId()) {
            case R.id.health_data_dialog_cancel_iv:
            case R.id.health_data_dialog_cancel_tv:
                break;
            case R.id.health_data_dialog_submit_tv:
                if (onCommitListener != null) {
                    onCommitListener.backHealthManager();
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
        /**
         * 健康管理
         */
        void backHealthManager();

        /**
         * 返回首页
         */
        void backHome();
    }
}
