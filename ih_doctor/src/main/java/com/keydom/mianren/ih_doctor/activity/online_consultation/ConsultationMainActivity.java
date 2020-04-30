package com.keydom.mianren.ih_doctor.activity.online_consultation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.mianren.ih_doctor.R;
import com.keydom.mianren.ih_doctor.activity.online_consultation.controller.ConsultationMainController;
import com.keydom.mianren.ih_doctor.activity.online_consultation.fragment.ConsultationApplyFragment;
import com.keydom.mianren.ih_doctor.activity.online_consultation.fragment.ConsultationPatientOrderFragment;
import com.keydom.mianren.ih_doctor.activity.online_consultation.view.ConsultationMainView;
import com.keydom.mianren.ih_doctor.bean.InquiryBean;
import com.keydom.mianren.ih_doctor.constant.Const;

import org.jetbrains.annotations.Nullable;

import butterknife.BindView;

/**
 * @date 20/4/29 19:11
 * @des 会诊申请 会诊历史
 */
public class ConsultationMainActivity extends BaseControllerActivity<ConsultationMainController> implements ConsultationMainView {
    @BindView(R.id.consultation_main_back_iv)
    ImageView consultationMainBackIv;
    @BindView(R.id.consultation_main_apply_tv)
    TextView consultationMainApplyTv;
    @BindView(R.id.consultation_main_history_tv)
    TextView consultationMainHistoryTv;
    @BindView(R.id.consultation_main_container)
    FrameLayout consultationMainContainer;

    private InquiryBean inquiryBean;

    private FragmentManager manager;
    private FragmentTransaction transaction;
    private ConsultationApplyFragment applyFragment;
    private ConsultationPatientOrderFragment orderFragment;

    public static void start(Context context, InquiryBean inquiryBean) {
        Intent intent = new Intent(context, ConsultationMainActivity.class);
        intent.putExtra(Const.DATA, inquiryBean);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_consultation_main;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        inquiryBean = (InquiryBean) getIntent().getSerializableExtra(Const.DATA);

        manager = getSupportFragmentManager();

        consultationMainApplyTv.setOnClickListener(getController());
        consultationMainHistoryTv.setOnClickListener(getController());
        consultationMainBackIv.setOnClickListener(v -> finish());
        tabApplyView();
    }

    @Override
    public void tabApplyView() {
        transaction = manager.beginTransaction();
        hideAll(transaction);
        if (applyFragment == null) {
            applyFragment = new ConsultationApplyFragment();
            applyFragment.setInquiryBean(inquiryBean);
            transaction.add(R.id.consultation_main_container, applyFragment);
        } else {
            transaction.show(applyFragment);
            applyFragment.onResume();
        }
        transaction.commitAllowingStateLoss();
        step(0);
    }

    @Override
    public void tabHistoryView() {
        transaction = manager.beginTransaction();
        hideAll(transaction);
        if (orderFragment == null) {
            orderFragment = new ConsultationPatientOrderFragment();
            orderFragment.setInquiryBean(inquiryBean);
            transaction.add(R.id.consultation_main_container, orderFragment);
        } else {
            transaction.show(orderFragment);
            orderFragment.setInquiryBean(inquiryBean);
            orderFragment.onResume();
        }
        transaction.commitAllowingStateLoss();
        step(1);
    }

    /**
     * 隐藏所有碎片
     */
    private void hideAll(FragmentTransaction transaction) {
        if (applyFragment != null) {
            transaction.hide(applyFragment);
        }
        if (orderFragment != null) {
            transaction.hide(orderFragment);
        }
    }

    private void step(int step) {
        switch (step) {
            case 0:
                consultationMainApplyTv.setSelected(true);
                consultationMainHistoryTv.setSelected(false);
                break;
            case 1:
                consultationMainApplyTv.setSelected(false);
                consultationMainHistoryTv.setSelected(true);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (applyFragment != null) {
            applyFragment.onActivityResult(requestCode, resultCode, data);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
