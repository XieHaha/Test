package com.keydom.mianren.ih_patient.activity.health_manager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.view.IhTitleLayout;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.health_manager.controller.HealthArchivesController;
import com.keydom.mianren.ih_patient.activity.health_manager.view.HealthArchivesView;
import com.zhy.view.flowlayout.TagFlowLayout;

import org.jetbrains.annotations.Nullable;

import butterknife.BindView;

/**
 * @author 顿顿
 * @date 20/3/4 10:54
 * @des 健康档案
 */
public class HealthArchivesActivity extends BaseControllerActivity<HealthArchivesController> implements HealthArchivesView {
    @BindView(R.id.health_archives_base_info_tv)
    TextView healthArchivesBaseInfoTv;
    @BindView(R.id.health_archives_base_info_layout)
    LinearLayout healthArchivesBaseInfoLayout;
    @BindView(R.id.health_archives_add_contact_tv)
    TextView healthArchivesAddContactTv;
    @BindView(R.id.health_archives_select_past_tv)
    TextView healthArchivesSelectPastTv;
    @BindView(R.id.health_archives_past_flow_layout)
    TagFlowLayout healthArchivesPastFlowLayout;
    @BindView(R.id.health_archives_genetic_tv)
    TextView healthArchivesGeneticTv;
    @BindView(R.id.health_archives_genetic_flow_layout)
    TagFlowLayout healthArchivesGeneticFlowLayout;
    @BindView(R.id.health_archives_add_surgery_tv)
    TextView healthArchivesAddSurgeryTv;
    @BindView(R.id.health_archives_look_more_tv)
    TextView healthArchivesLookMoreTv;
    @BindView(R.id.health_archives_drink)
    TextView healthArchivesDrink;
    @BindView(R.id.health_archives_non_drink)
    TextView healthArchivesNonDrink;
    @BindView(R.id.health_archives_drink_frequency_layout)
    LinearLayout healthArchivesDrinkFrequencyLayout;
    @BindView(R.id.health_archives_drink_quantity_layout)
    LinearLayout healthArchivesDrinkQuantityLayout;
    @BindView(R.id.health_archives_drink_year_layout)
    LinearLayout healthArchivesDrinkYearLayout;
    @BindView(R.id.health_archives_smoke)
    TextView healthArchivesSmoke;
    @BindView(R.id.health_archives_non_smoke)
    TextView healthArchivesNonSmoke;
    @BindView(R.id.health_archives_smoke_frequency_layout)
    LinearLayout healthArchivesSmokeFrequencyLayout;
    @BindView(R.id.health_archives_smoke_quantity_layout)
    LinearLayout healthArchivesSmokeQuantityLayout;
    @BindView(R.id.health_archives_smoke_year_layout)
    LinearLayout healthArchivesSmokeYearLayout;

    /**
     * 启动
     */
    public static void start(Context context) {
        context.startActivity(new Intent(context, HealthArchivesActivity.class));
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_health_archives;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle(R.string.txt_health_archives);
        setRightTxt(getString(R.string.save));
        setRightBtnListener(new IhTitleLayout.OnRightTextClickListener() {
            @Override
            public void OnRightTextClick(View v) {
                finish();
            }
        });

        healthArchivesBaseInfoLayout.setOnClickListener(getController());
        healthArchivesAddContactTv.setOnClickListener(getController());
        healthArchivesSelectPastTv.setOnClickListener(getController());
        healthArchivesGeneticTv.setOnClickListener(getController());
        healthArchivesAddSurgeryTv.setOnClickListener(getController());
        healthArchivesLookMoreTv.setOnClickListener(getController());
        healthArchivesDrink.setOnClickListener(getController());
        healthArchivesNonDrink.setOnClickListener(getController());
        healthArchivesDrinkFrequencyLayout.setOnClickListener(getController());
        healthArchivesDrinkQuantityLayout.setOnClickListener(getController());
        healthArchivesDrinkYearLayout.setOnClickListener(getController());
        healthArchivesSmoke.setOnClickListener(getController());
        healthArchivesNonSmoke.setOnClickListener(getController());
        healthArchivesSmokeFrequencyLayout.setOnClickListener(getController());
        healthArchivesSmokeQuantityLayout.setOnClickListener(getController());
        healthArchivesSmokeYearLayout.setOnClickListener(getController());
        healthArchivesPastFlowLayout.setVisibility(View.GONE);
        healthArchivesGeneticFlowLayout.setVisibility(View.GONE);
    }
}
