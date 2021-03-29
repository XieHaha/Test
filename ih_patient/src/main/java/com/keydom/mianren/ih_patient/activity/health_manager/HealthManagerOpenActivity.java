package com.keydom.mianren.ih_patient.activity.health_manager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.utils.StatusBarUtils;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.health_manager.controller.HealthManagerOpenController;
import com.keydom.mianren.ih_patient.activity.health_manager.view.HealthManagerOpenView;
import com.keydom.mianren.ih_patient.bean.Event;
import com.keydom.mianren.ih_patient.bean.MedicalCardInfo;
import com.keydom.mianren.ih_patient.constant.EventType;
import com.keydom.mianren.ih_patient.view.HealthManagerDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.Nullable;

import butterknife.BindView;

/**
 * @author 顿顿
 * @date 20/3/4 10:54
 * @des 开通健康管理
 */
public class HealthManagerOpenActivity extends BaseControllerActivity<HealthManagerOpenController> implements HealthManagerOpenView {
    @BindView(R.id.health_manager_open_now_layout)
    LinearLayout healthManagerOpenNowLayout;
    @BindView(R.id.health_manager_select_iv)
    ImageView healthManagerSelectIv;
    @BindView(R.id.health_manager_select_layout)
    RelativeLayout healthManagerSelectLayout;
    @BindView(R.id.health_manager_protocol_tv)
    TextView healthManagerProtocolTv;
    @BindView(R.id.health_manager_open_tv)
    TextView healthManagerOpenTv;

    /**
     * 就诊人信息
     */
    private MedicalCardInfo medicalCardInfo;

    private HealthManagerDialog managerDialog;

    /**
     * 启动
     */
    public static void start(Context context) {
        context.startActivity(new Intent(context, HealthManagerOpenActivity.class));
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_health_manager_open;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        StatusBarUtils.setWindowStatusBarColor(this, R.color.color_f9f9f9);
        setTitle(R.string.txt_health_manager_service);

        healthManagerOpenNowLayout.setOnClickListener(getController());
        healthManagerSelectLayout.setOnClickListener(getController());
        healthManagerOpenTv.setOnClickListener(getController());
        healthManagerProtocolTv.setOnClickListener(getController());
    }

    @Override
    public boolean isSelected() {
        return healthManagerSelectIv.getVisibility() == View.VISIBLE;
    }

    @Override
    public void setSelect() {
        if (healthManagerSelectIv.getVisibility() == View.VISIBLE) {
            healthManagerSelectIv.setVisibility(View.INVISIBLE);
        } else {
            healthManagerSelectIv.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void openHealthManagerSuccess() {
        EventBus.getDefault().post(new Event(EventType.OPEN_HEALTH_MANAGER, null));
        showSuccessDialog();
    }

    /**
     * 获取就诊人
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getPatientCard(Event event) {
        if (event.getType() == EventType.SENDSELECTNURSINGPATIENT) {
            medicalCardInfo = (MedicalCardInfo) event.getData();
            getController().openHealthManager(medicalCardInfo.getEleCardNumber());
        }
    }

    private void showSuccessDialog() {
        if (managerDialog == null) {
            managerDialog = new HealthManagerDialog(this,
                    new HealthManagerDialog.OnCommitListener() {
                        @Override
                        public void backHealthManager() {
                            HealthManagerActivity.start(HealthManagerOpenActivity.this);
                            finish();
                        }

                        @Override
                        public void backHome() {
                            finish();
                        }
                    });
        }
        managerDialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
