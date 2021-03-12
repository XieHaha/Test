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
import com.keydom.mianren.ih_patient.bean.IndexData;
import com.stx.xhb.xbanner.XBanner;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author 顿顿
 * @date 20/3/4 10:54
 * @des 健康管理
 */
public class HealthManagerActivity extends BaseControllerActivity<HealthManagerController> implements HealthManagerView {
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
    @BindView(R.id.health_manager_cardiovascular_layout)
    RelativeLayout healthManagerCardiovascularLayout;
    @BindView(R.id.health_manager_hypertension_tv)
    TextView healthManagerHypertensionTv;
    @BindView(R.id.health_manager_hypertension_hint_tv)
    TextView healthManagerHypertensionHintTv;
    @BindView(R.id.health_manager_hypertension_hint_iv)
    ImageView healthManagerHypertensionHintIv;
    @BindView(R.id.health_manager_hypertension_layout)
    RelativeLayout healthManagerHypertensionLayout;
    @BindView(R.id.health_manager_diabetes_tv)
    TextView healthManagerDiabetesTv;
    @BindView(R.id.health_manager_diabetes_hint_tv)
    TextView healthManagerDiabetesHintTv;
    @BindView(R.id.health_manager_diabetes_hint_iv)
    ImageView healthManagerDiabetesHintIv;
    @BindView(R.id.health_manager_diabetes_layout)
    RelativeLayout healthManagerDiabetesLayout;

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
        setTitle(R.string.txt_health_manager_service);

        setNoticeData(null);

        healthManagerCardiovascularTv.setSelected(true);
        healthManagerCardiovascularHintTv.setSelected(true);
        healthManagerCardiovascularHintIv.setSelected(true);

        healthManagerArchivesLayout.setOnClickListener(getController());
        healthManagerReportLayout.setOnClickListener(getController());
        healthManagerOnlineLayout.setOnClickListener(getController());
        healthManagerCardiovascularLayout.setOnClickListener(getController());
        healthManagerHypertensionLayout.setOnClickListener(getController());
        healthManagerDiabetesLayout.setOnClickListener(getController());
    }

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

}
