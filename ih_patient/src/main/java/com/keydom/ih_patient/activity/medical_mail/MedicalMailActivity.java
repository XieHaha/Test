package com.keydom.ih_patient.activity.medical_mail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.utils.StatusBarUtils;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.medical_mail.controller.MedicalMailController;
import com.keydom.ih_patient.activity.medical_mail.fragment.MedicalMailEndFragment;
import com.keydom.ih_patient.activity.medical_mail.fragment.MedicalMailOneFragment;
import com.keydom.ih_patient.activity.medical_mail.fragment.MedicalMailThreeFragment;
import com.keydom.ih_patient.activity.medical_mail.fragment.MedicalMailTwoFragment;
import com.keydom.ih_patient.activity.medical_mail.view.MedicalMailView;
import com.keydom.ih_patient.bean.Event;
import com.keydom.ih_patient.bean.MedicalMailApplyBean;
import com.keydom.ih_patient.constant.EventType;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.Nullable;

import butterknife.BindView;

/**
 * @date 20/3/2 13:52
 * @des 病案邮寄
 */
public class MedicalMailActivity extends BaseControllerActivity<MedicalMailController> implements MedicalMailView {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_base)
    TextView tvBase;
    @BindView(R.id.iv_base)
    ImageView ivBase;
    @BindView(R.id.view_base)
    View viewBase;
    @BindView(R.id.layout_base)
    LinearLayout layoutBase;
    @BindView(R.id.iv_two)
    ImageView ivTwo;
    @BindView(R.id.view_two_left)
    View viewTwoLeft;
    @BindView(R.id.view_two_right)
    View viewTwoRight;
    @BindView(R.id.layout_two)
    LinearLayout layoutTwo;
    @BindView(R.id.iv_three)
    ImageView ivThree;
    @BindView(R.id.view_three_left)
    View viewThreeLeft;
    @BindView(R.id.view_three_right)
    View viewThreeRight;
    @BindView(R.id.layout_three)
    LinearLayout layoutThree;
    @BindView(R.id.iv_end)
    ImageView ivEnd;
    @BindView(R.id.view_end)
    View viewEnd;
    @BindView(R.id.layout_end)
    LinearLayout layoutEnd;
    @BindView(R.id.layout_frame_root)
    FrameLayout layoutFrameRoot;

    private MedicalMailApplyBean applyBean;

    private FragmentManager manager;
    private FragmentTransaction transaction;
    private MedicalMailOneFragment oneFragment;
    private MedicalMailTwoFragment twoFragment;
    private MedicalMailThreeFragment threeFragment;
    private MedicalMailEndFragment endFragment;

    /**
     * 启动
     */
    public static void start(Context context) {
        context.startActivity(new Intent(context, MedicalMailActivity.class));
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_medical_mail;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
        StatusBarUtils.setWindowStatusBarColor(this, R.color.other_login_color);
        EventBus.getDefault().register(this);
        tvTitle.setText(getString(R.string.txt_medical_mail));

        manager = getSupportFragmentManager();
        tabOneView();
        ivBack.setOnClickListener(getController());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onStepOneConfirm(Event event) {
        if (event.getType() == EventType.MEDICAL_STEP_ONE) {
            applyBean = (MedicalMailApplyBean) event.getData();
            tabTwoView();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onStepTwoConfirm(Event event) {
        if (event.getType() == EventType.MEDICAL_STEP_TWO) {
            tabThreeView();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onStepThreeConfirm(Event event) {
        if (event.getType() == EventType.MEDICAL_STEP_THREE) {
            tabEndView();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onStepEndConfirm(Event event) {
        if (event.getType() == EventType.MEDICAL_STEP_END) {
            //提交
        }
    }

    private void tabOneView() {
        transaction = manager.beginTransaction();
        hideAll(transaction);
        if (oneFragment == null) {
            oneFragment = new MedicalMailOneFragment();
            transaction.add(R.id.layout_frame_root, oneFragment);
        } else {
            transaction.show(oneFragment);
            oneFragment.onResume();
        }
        transaction.commitAllowingStateLoss();
        step(0);
    }

    private void tabTwoView() {
        transaction = manager.beginTransaction();
        hideAll(transaction);
        if (twoFragment == null) {
            twoFragment = new MedicalMailTwoFragment();
            transaction.add(R.id.layout_frame_root, twoFragment);
        } else {
            transaction.show(twoFragment);
            twoFragment.onResume();
        }
        transaction.commitAllowingStateLoss();
        step(1);
    }

    private void tabThreeView() {
        transaction = manager.beginTransaction();
        hideAll(transaction);
        if (threeFragment == null) {
            threeFragment = new MedicalMailThreeFragment();
            transaction.add(R.id.layout_frame_root, threeFragment);
        } else {
            transaction.show(threeFragment);
            threeFragment.onResume();
        }
        transaction.commitAllowingStateLoss();
        step(2);
    }

    private void tabEndView() {
        transaction = manager.beginTransaction();
        hideAll(transaction);
        if (endFragment == null) {
            endFragment = new MedicalMailEndFragment();
            transaction.add(R.id.layout_frame_root, endFragment);
        } else {
            transaction.show(endFragment);
            endFragment.onResume();
        }
        transaction.commitAllowingStateLoss();
        step(3);
    }

    /**
     * 隐藏所有碎片
     */
    private void hideAll(FragmentTransaction transaction) {
        if (oneFragment != null) {
            transaction.hide(oneFragment);
        }
        if (twoFragment != null) {
            transaction.hide(twoFragment);
        }
        if (threeFragment != null) {
            transaction.hide(threeFragment);
        }
        if (endFragment != null) {
            transaction.hide(endFragment);
        }
    }

    private void step(int step) {
        switch (step) {
            case 0:
                curPage = 0;
                layoutBase.setSelected(true);
                viewBase.setSelected(false);
                layoutTwo.setSelected(false);
                break;
            case 1:
                curPage = 1;
                layoutTwo.setSelected(true);
                viewBase.setSelected(true);
                layoutThree.setSelected(false);
                viewTwoRight.setSelected(false);
                break;
            case 2:
                curPage = 2;
                layoutThree.setSelected(true);
                viewTwoRight.setSelected(true);
                layoutEnd.setSelected(false);
                viewThreeRight.setSelected(false);
                break;
            case 3:
                curPage = 3;
                layoutEnd.setSelected(true);
                viewThreeRight.setSelected(true);
                break;
            default:
                break;
        }
    }


    int curPage;

    /**
     * 页面逻辑处理
     */
    private boolean finishPage() {
        if (curPage == 3) {
            curPage = 2;
            tabThreeView();
            return false;
        } else if (curPage == 2) {
            curPage = 1;
            tabTwoView();
            return false;
        } else if (curPage == 1) {
            curPage = 0;
            tabOneView();
            return false;
        }
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (!finishPage()) {
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
