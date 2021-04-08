package com.keydom.mianren.ih_patient.activity.health_manager;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;

import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.CombinedData;
import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.view.JustifiedTextView;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.health_manager.controller.HealthSummaryDetailController;
import com.keydom.mianren.ih_patient.activity.health_manager.view.HealthSummaryDetailView;
import com.keydom.mianren.ih_patient.bean.HealthSummaryBean;
import com.keydom.mianren.ih_patient.constant.Const;
import com.keydom.mianren.ih_patient.utils.AndroidCartUtils;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author 顿顿
 * @date 20/3/4 10:54
 * @des 健康报告详情
 */
public class HealthSummaryDetailActivity extends BaseControllerActivity<HealthSummaryDetailController> implements HealthSummaryDetailView {
    @BindView(R.id.health_summary_detail_combined_chart)
    CombinedChart combinedChart;
    @BindView(R.id.health_summary_detail_radar_chart)
    RadarChart radarChart;
    @BindView(R.id.health_summary_detail_name_tv)
    TextView healthSummaryDetailNameTv;
    @BindView(R.id.health_summary_detail_date_tv)
    TextView healthSummaryDetailDateTv;
    @BindView(R.id.health_summary_detail_status_tv)
    TextView healthSummaryDetailStatusTv;
    @BindView(R.id.health_summary_detail_summary_tv)
    JustifiedTextView healthSummaryDetailSummaryTv;
    @BindView(R.id.health_summary_detail_notice_tv)
    JustifiedTextView healthSummaryDetailNoticeTv;
    @BindView(R.id.health_summary_detail_hint_tv)
    JustifiedTextView healthSummaryDetailHintTv;
    @BindView(R.id.health_summary_detail_next_tv)
    TextView healthSummaryDetailNextTv;
    @BindView(R.id.health_summary_detail_pressure_count_tv)
    TextView healthSummaryDetailPressureCountTv;
    @BindView(R.id.health_summary_detail_pressure_status_tv)
    TextView healthSummaryDetailPressureStatusTv;
    @BindView(R.id.health_summary_detail_sugar_count_tv)
    TextView healthSummaryDetailSugarCountTv;
    @BindView(R.id.health_summary_detail_sugar_status_tv)
    TextView healthSummaryDetailSugarStatusTv;
    @BindView(R.id.health_summary_detail_fat_count_tv)
    TextView healthSummaryDetailFatCountTv;
    @BindView(R.id.health_summary_detail_fat_status_tv)
    TextView healthSummaryDetailFatStatusTv;
    @BindView(R.id.health_summary_detail_heart_count_tv)
    TextView healthSummaryDetailHeartCountTv;
    @BindView(R.id.health_summary_detail_heart_status_tv)
    TextView healthSummaryDetailHeartStatusTv;

    private String summaryId;
    private HealthSummaryBean summaryBean;

    private ArrayList<Integer> lineData = new ArrayList<>();
    private ArrayList<Integer> barData = new ArrayList<>();

    private String[] typeData = {"血压", "血糖", "血脂", "心率"};
    private Integer[] lifestyleData;
    private List<String> lifestyleLabels;

    /**
     * 启动
     */
    public static void start(Context context, String summaryId) {
        Intent intent = new Intent(context, HealthSummaryDetailActivity.class);
        intent.putExtra(Const.RECORD_ID, summaryId);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_health_summary_detail;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        summaryId = getIntent().getStringExtra(Const.RECORD_ID);
        healthSummaryDetailNextTv.setOnClickListener(getController());

        initCombinedChart();
        initRadarChart();
        getController().patientHealthConclusionDetail(summaryId);
    }

    /**
     * 柱状图 折线图初始化
     */
    private void initCombinedChart() {
        //不显示描述内容
        combinedChart.getDescription().setEnabled(false);
        //背景颜色
        combinedChart.setBackgroundColor(Color.WHITE);
        //边界
        combinedChart.setDrawBorders(false);
        //设置是否可以缩放，false不可以放大缩小
        combinedChart.setScaleEnabled(false);
        //以下是为了解决 柱x状图 左右两边只显示了一半的问题 根据实际情况 而定
        combinedChart.getXAxis().setAxisMinimum(-0.5f);
        combinedChart.getXAxis().setAxisMaximum((float) (3.5));
        //设置图表最大可见和最小可见个数。既可以固定可见个数。
        combinedChart.setVisibleXRangeMaximum(5);
        //图例说明
        Legend legend = combinedChart.getLegend();
        //不显示图例 底部的什么颜色代表什么的说明
        legend.setEnabled(false);


        //Y轴设置
        YAxis rightAxis = combinedChart.getAxis(YAxis.AxisDependency.RIGHT);
        rightAxis.setDrawGridLines(true);
        rightAxis.setGridColor(ContextCompat.getColor(this, R.color.edit_hint_color));
        rightAxis.setAxisMinimum(0f);
        rightAxis.setLabelCount(5, true);
        combinedChart.getAxisLeft().setLabelCount(5, true);
        rightAxis.setValueFormatter((value, axis) -> "");

        // 去掉左右边线：
        combinedChart.getAxisLeft().setDrawAxisLine(false);
        combinedChart.getAxisRight().setDrawAxisLine(false);


        XAxis bottomAxis = combinedChart.getXAxis();
        //去掉x轴的网格竖线
        bottomAxis.setDrawGridLines(false);
        bottomAxis.setLabelCount(4);
        // 设置X轴标签位置，BOTTOM在底部显示，TOP在顶部显示
        bottomAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        bottomAxis.setTextColor(ContextCompat.getColor(this, R.color.tab_nol_color));
        bottomAxis.setValueFormatter((value, axis) -> typeData[(int) value]);

    }

    /**
     * 雷达图初始化
     */
    private void initRadarChart() {
        radarChart.getDescription().setEnabled(false);
        radarChart.setDragDecelerationEnabled(false);
        radarChart.setTouchEnabled(false);
        Legend legend = radarChart.getLegend();
        //不显示图例 底部的什么颜色代表什么的说明
        legend.setEnabled(false);
        XAxis xAxis = radarChart.getXAxis();
        // 文本颜色
        xAxis.setTextColor(ContextCompat.getColor(this, R.color.color_333333));
        // 文本大小
        xAxis.setTextSize(14);
        // 设置标签的显示格式
        xAxis.setValueFormatter((value, axis) -> lifestyleLabels.get((int) value));

        YAxis yAxis = radarChart.getYAxis();
        // 要达到100需要把该值设为80，
        yAxis.setAxisMaximum(90f);
        //隐藏每层数据
        yAxis.setValueFormatter((value, axis) -> "");
        yAxis.setAxisMinimum(0f);
    }


    @Override
    public void requestSummaryDetailSuccess(HealthSummaryBean summaryBean) {
        this.summaryBean = summaryBean;
        bindData();
    }

    private void bindData() {
        setTitle(summaryBean.getHealthConclusionName());
        healthSummaryDetailNameTv.setText(summaryBean.getDoctorName());
        healthSummaryDetailDateTv.setText(summaryBean.getCreateTime());
        healthSummaryDetailStatusTv.setText(summaryBean.getHealthStatus());
        healthSummaryDetailSummaryTv.setText(summaryBean.getContent());
        healthSummaryDetailNoticeTv.setText(summaryBean.getAttention());
        //        healthSummaryDetailHintTv.setText(summaryBean.getHealthStatus());
        lifestyleData = new Integer[5];
        lifestyleData[0] = summaryBean.getFoodScore();
        lifestyleData[1] = summaryBean.getExerciseScore();
        lifestyleData[2] = summaryBean.getSleepScore();
        lifestyleData[3] = summaryBean.getDrinkScore();
        lifestyleData[4] = summaryBean.getSmokeSize();

        lifestyleLabels = new ArrayList<>();
        lifestyleLabels.add(String.format(getString(R.string.txt_eat_score), lifestyleData[0]));
        lifestyleLabels.add(String.format(getString(R.string.txt_sports_score), lifestyleData[1]));
        lifestyleLabels.add(String.format(getString(R.string.txt_sleep_score), lifestyleData[2]));
        lifestyleLabels.add(String.format(getString(R.string.txt_drink_score), lifestyleData[3]));
        lifestyleLabels.add(String.format(getString(R.string.txt_smoke_score), lifestyleData[4]));

        healthSummaryDetailPressureCountTv.setText(summaryBean.getBloodPressureCount() + "次");
        healthSummaryDetailSugarCountTv.setText(summaryBean.getBloodSugarCount() + "次");
        healthSummaryDetailFatCountTv.setText(summaryBean.getBloodFatCount() + "次");
        healthSummaryDetailHeartCountTv.setText(summaryBean.getHeartRateCount() + "次");
        healthSummaryDetailPressureStatusTv.setText(getStatusString(summaryBean.getBloodPressureCount()));
        healthSummaryDetailPressureStatusTv.setTextColor(getStatusColor(summaryBean.getBloodPressureCount()));
        healthSummaryDetailSugarStatusTv.setText(getStatusString(summaryBean.getBloodSugarCount()));
        healthSummaryDetailSugarStatusTv.setTextColor(getStatusColor(summaryBean.getBloodSugarCount()));
        healthSummaryDetailFatStatusTv.setText(getStatusString(summaryBean.getBloodFatCount()));
        healthSummaryDetailFatStatusTv.setTextColor(getStatusColor(summaryBean.getBloodFatCount()));
        healthSummaryDetailHeartStatusTv.setText(getStatusString(summaryBean.getHeartRateCount()));
        healthSummaryDetailHeartStatusTv.setTextColor(getStatusColor(summaryBean.getHeartRateCount()));

        //风险线默认15
        lineData.add(15);
        lineData.add(15);
        lineData.add(15);
        lineData.add(15);

        barData.add(summaryBean.getBloodPressureCount());
        barData.add(summaryBean.getBloodSugarCount());
        barData.add(summaryBean.getBloodFatCount());
        barData.add(summaryBean.getHeartRateCount());

        CombinedData combinedData = new CombinedData();
        combinedData.setData(AndroidCartUtils.generateLineData(lineData));
        combinedData.setData(AndroidCartUtils.generateBarData(barData));
        combinedChart.setData(combinedData);
        combinedChart.invalidate();
        radarChart.setData(AndroidCartUtils.getChartData(lifestyleData));
        radarChart.invalidate();
    }

    private String getStatusString(int count) {
        if (count <= 15) {
            return "【安全】";
        } else if (count < 20) {
            return "【低风险】";
        } else {
            return "【高风险】";
        }
    }

    private int getStatusColor(int count) {
        if (count <= 15) {
            return ContextCompat.getColor(this, R.color.color_333333);
        } else if (count < 20) {
            return ContextCompat.getColor(this, R.color.color_00d394);
        } else {
            return ContextCompat.getColor(this, R.color.color_f61717);
        }
    }
}
