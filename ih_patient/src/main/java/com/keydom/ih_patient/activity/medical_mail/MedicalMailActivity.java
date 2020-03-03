package com.keydom.ih_patient.activity.medical_mail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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

import java.util.ArrayList;
import java.util.List;

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
    private MedicalMailOneFragment oneFragment;
    private MedicalMailTwoFragment twoFragment;
    private MedicalMailThreeFragment threeFragment;
    private MedicalMailEndFragment endFragment;
    private List<Fragment> fragments;

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
        initFragment();


        ivBack.setOnClickListener(getController());
    }

    private void initFragment() {
        fragments = new ArrayList<>();
        oneFragment = new MedicalMailOneFragment();
        twoFragment = new MedicalMailTwoFragment();
        threeFragment = new MedicalMailThreeFragment();
        endFragment = new MedicalMailEndFragment();
        fragments.add(oneFragment);
        fragments.add(twoFragment);
        fragments.add(threeFragment);
        fragments.add(endFragment);
        updatePage(true);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onStepOneConfirm(Event event) {
        if (event.getType() == EventType.MEDICAL_STEP_ONE) {
            applyBean = (MedicalMailApplyBean) event.getData();
            updatePage(true);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onStepTwoConfirm(Event event) {
        if (event.getType() == EventType.MEDICAL_STEP_TWO) {
            updatePage(true);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onStepThreeConfirm(Event event) {
        if (event.getType() == EventType.MEDICAL_STEP_THREE) {
            updatePage(true);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onStepEndConfirm(Event event) {
        if (event.getType() == EventType.MEDICAL_STEP_END) {
        }
    }

    protected final void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.layout_frame_root, fragment);
        transaction.addToBackStack(fragment.getClass().getName());
        transaction.commit();
    }

    int index = -1;

    private void updatePage(boolean add) {
        if (add) {
            index++;
            replaceFragment(fragments.get(index));
        } else {
            index--;
        }
        step(index);
    }

    private void step(int step) {
        switch (step) {
            case 0:
                layoutBase.setSelected(true);
                viewBase.setSelected(false);
                layoutTwo.setSelected(false);
                break;
            case 1:
                layoutTwo.setSelected(true);
                viewBase.setSelected(true);
                layoutThree.setSelected(false);
                viewTwoRight.setSelected(false);
                break;
            case 2:
                layoutThree.setSelected(true);
                viewTwoRight.setSelected(true);
                layoutEnd.setSelected(false);
                viewThreeRight.setSelected(false);
                break;
            case 3:
                layoutEnd.setSelected(true);
                viewThreeRight.setSelected(true);
                break;
            default:
                break;
        }
    }


    @Override
    public boolean finishPage() {
        goBack();
        return false;
    }


    /**
     * index 回退
     */
    private void goBack() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
            finish();
        } else {
            updatePage(false);
            super.onBackPressed();
        }
    }

    @Override
    public void onBackPressed() {
        goBack();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
