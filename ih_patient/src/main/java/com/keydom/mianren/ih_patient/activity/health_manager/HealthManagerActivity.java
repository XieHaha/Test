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
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.health_manager.controller.HealthManagerController;
import com.keydom.mianren.ih_patient.activity.health_manager.view.HealthManagerView;
import com.keydom.mianren.ih_patient.bean.Event;
import com.keydom.mianren.ih_patient.bean.HealthManagerMainBean;
import com.keydom.mianren.ih_patient.bean.IndexData;
import com.keydom.mianren.ih_patient.bean.entity.ChronicDisease;
import com.keydom.mianren.ih_patient.constant.EventType;
import com.stx.xhb.xbanner.XBanner;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author 顿顿
 * @date 20/3/4 10:54
 * @des 健康管理
 */
public class HealthManagerActivity extends BaseControllerActivity<HealthManagerController> implements HealthManagerView, ChronicDisease {
    @BindView(R.id.health_manager_archives_status_iv)
    ImageView healthManagerArchivesStatusIv;
    @BindView(R.id.health_manager_archives_layout)
    LinearLayout healthManagerArchivesLayout;
    @BindView(R.id.health_manager_report_status_tv)
    TextView healthManagerReportStatusTv;
    @BindView(R.id.health_manager_report_layout)
    LinearLayout healthManagerReportLayout;
    @BindView(R.id.health_manager_online_layout)
    LinearLayout healthManagerOnlineLayout;
    @BindView(R.id.health_manager_notice_x_banner)
    XBanner healthManagerNoticeBanner;
    @BindView(R.id.health_manager_cardiovascular_tv)
    TextView healthManagerCardiovascularTv;
    @BindView(R.id.health_manager_cardiovascular_hint_tv)
    TextView healthManagerCardiovascularHintTv;
    @BindView(R.id.health_manager_cardiovascular_hint_iv)
    ImageView healthManagerCardiovascularHintIv;
    @BindView(R.id.health_manager_cardiovascular_hint1_iv)
    ImageView healthManagerCardiovascularHint1Iv;
    @BindView(R.id.health_manager_cardiovascular_layout)
    RelativeLayout healthManagerCardiovascularLayout;
    @BindView(R.id.health_manager_hypertension_tv)
    TextView healthManagerHypertensionTv;
    @BindView(R.id.health_manager_hypertension_hint_tv)
    TextView healthManagerHypertensionHintTv;
    @BindView(R.id.health_manager_hypertension_hint_iv)
    ImageView healthManagerHypertensionHintIv;
    @BindView(R.id.health_manager_hypertension_hint1_iv)
    ImageView healthManagerHypertensionHint1Iv;
    @BindView(R.id.health_manager_hypertension_layout)
    RelativeLayout healthManagerHypertensionLayout;
    @BindView(R.id.health_manager_diabetes_tv)
    TextView healthManagerDiabetesTv;
    @BindView(R.id.health_manager_diabetes_hint_tv)
    TextView healthManagerDiabetesHintTv;
    @BindView(R.id.health_manager_diabetes_hint_iv)
    ImageView healthManagerDiabetesHintIv;
    @BindView(R.id.health_manager_diabetes_hint1_iv)
    ImageView healthManagerDiabetesHint1Iv;
    @BindView(R.id.health_manager_diabetes_layout)
    RelativeLayout healthManagerDiabetesLayout;

    /**
     * 健康首页状态值
     */
    private HealthManagerMainBean mainBean;

    /**
     * 启动
     */
    public static void start(Context context) {
        context.startActivity(new Intent(context, HealthManagerActivity.class));
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_health_manager;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        setTitle(R.string.txt_health_manager_service);

        getController().patientHealthManageIndex();
        setNoticeData(null);

        healthManagerArchivesLayout.setOnClickListener(getController());
        healthManagerReportLayout.setOnClickListener(getController());
        healthManagerOnlineLayout.setOnClickListener(getController());
        healthManagerCardiovascularLayout.setOnClickListener(getController());
        healthManagerHypertensionLayout.setOnClickListener(getController());
        healthManagerDiabetesLayout.setOnClickListener(getController());
    }

    /**
     * 首页状态
     */
    private void initMainUi() {
        if (mainBean == null) {
            return;
        }
        //健康档案完善
        if (mainBean.getIsPerfect() == 0) {
            healthManagerArchivesStatusIv.setVisibility(View.VISIBLE);
        } else {
            healthManagerArchivesStatusIv.setVisibility(View.INVISIBLE);
        }
        //体检报告 默认不显示
        healthManagerReportStatusTv.setVisibility(View.INVISIBLE);

        cardiovascularData();
        hypertensionData();
        diabetesData();
    }

    /**
     * 心脑血管
     */
    private void cardiovascularData() {
        if (mainBean.getIsOpenHeartHeadBloodVessel() == 0) {
            healthManagerCardiovascularTv.setSelected(false);
            healthManagerCardiovascularHintTv.setSelected(false);
            healthManagerCardiovascularHintTv.setText(R.string.txt_non_enable_module);
            healthManagerCardiovascularHintIv.setVisibility(View.VISIBLE);
            healthManagerCardiovascularHint1Iv.setVisibility(View.GONE);
        } else {
            healthManagerCardiovascularTv.setSelected(true);
            healthManagerCardiovascularHintTv.setSelected(true);
            if (mainBean.getIsWriteHeartHeadBloodVessel() == 0) {
                healthManagerCardiovascularHintTv.setText(R.string.txt_non_data);
                healthManagerCardiovascularHintIv.setVisibility(View.GONE);
                healthManagerCardiovascularHint1Iv.setVisibility(View.VISIBLE);
            } else {
                healthManagerCardiovascularHintIv.setVisibility(View.VISIBLE);
                healthManagerCardiovascularHint1Iv.setVisibility(View.GONE);
                healthManagerCardiovascularHintTv.setText(R.string.txt_has_data);
            }
        }
    }

    /**
     * 高血压
     */
    private void hypertensionData() {
        if (mainBean.getIsOpenHighBloodPressure() == 0) {
            healthManagerHypertensionTv.setSelected(false);
            healthManagerHypertensionHintTv.setSelected(false);
            healthManagerHypertensionHintTv.setText(R.string.txt_non_enable_module);
            healthManagerHypertensionHintIv.setVisibility(View.VISIBLE);
            healthManagerHypertensionHint1Iv.setVisibility(View.GONE);
        } else {
            healthManagerHypertensionTv.setSelected(true);
            healthManagerHypertensionHintTv.setSelected(true);
            if (mainBean.getIsWriteHighBloodPressure() == 0) {
                healthManagerHypertensionHintTv.setText(R.string.txt_non_data);
                healthManagerHypertensionHintIv.setVisibility(View.GONE);
                healthManagerHypertensionHint1Iv.setVisibility(View.VISIBLE);
            } else {
                healthManagerHypertensionHintIv.setVisibility(View.VISIBLE);
                healthManagerHypertensionHint1Iv.setVisibility(View.GONE);
                healthManagerHypertensionHintTv.setText(R.string.txt_has_data);
            }
        }
    }

    /**
     * 糖尿病
     */
    private void diabetesData() {
        if (mainBean.getIsOpenDiabetes() == 0) {
            healthManagerDiabetesTv.setSelected(false);
            healthManagerDiabetesHintTv.setSelected(false);
            healthManagerDiabetesHintTv.setText(R.string.txt_non_enable_module);
            healthManagerDiabetesHintIv.setVisibility(View.VISIBLE);
            healthManagerDiabetesHint1Iv.setVisibility(View.GONE);
        } else {
            healthManagerDiabetesTv.setSelected(true);
            healthManagerDiabetesHintTv.setSelected(true);
            if (mainBean.getIsWriteDiabetes() == 0) {
                healthManagerDiabetesHintTv.setText(R.string.txt_non_data);
                healthManagerDiabetesHintIv.setVisibility(View.GONE);
                healthManagerDiabetesHint1Iv.setVisibility(View.VISIBLE);
            } else {
                healthManagerDiabetesHintIv.setVisibility(View.VISIBLE);
                healthManagerDiabetesHint1Iv.setVisibility(View.GONE);
                healthManagerDiabetesHintTv.setText(R.string.txt_has_data);
            }
        }
    }

    /**
     * 广告banner
     */
    public void setNoticeData(final List<IndexData.NotificationsBean> list) {
        List<String> data = new ArrayList<>();
        data.add("暂无通知");
        healthManagerNoticeBanner.setData(R.layout.notice_xbanner_item, data, null);
        healthManagerNoticeBanner.loadImage(new XBanner.XBannerAdapter() {
            @Override
            public void loadBanner(XBanner banner, Object model, View view, int position) {
                TextView indexNewsTv = view.findViewById(R.id.index_news_tv);
                indexNewsTv.setText(data.get(position));
            }
        });
        healthManagerNoticeBanner.setOnItemClickListener(new XBanner.OnItemClickListener() {
            @Override
            public void onItemClick(XBanner banner, Object model, View view, int position) {
                ToastUtil.showMessage(getContext(), "暂无通知");
            }
        });
    }

    @Override
    public HealthManagerMainBean getMainBean() {
        return mainBean;
    }

    @Override
    public void requestHealthManagerSuccess(HealthManagerMainBean bean) {
        mainBean = bean;
        initMainUi();
    }

    @Override
    public void openChronicDiseaseManageSuccess(int type, String data) {
        ToastUtil.showMessage(this, "开通成功");
        getController().patientHealthManageIndex();
        //        switch (type) {
        //            case CHRONIC_DISEASE_CARDIOVASCULAR:
        //                break;
        //            case CHRONIC_DISEASE_HYPERTENSION:
        //                break;
        //            case CHRONIC_DISEASE_DIABETES:
        //                break;
        //            default:
        //                break;
        //        }
    }

    /**
     * 开通健康管理
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateHealthManager(Event event) {
        if (event.getType() == EventType.OPEN_HEALTH_MANAGER) {
            getController().patientHealthManageIndex();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
