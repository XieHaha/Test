package com.keydom.mianren.ih_patient.activity.health_manager;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;

import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.CombinedData;
import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.health_manager.controller.HealthSummaryDetailController;
import com.keydom.mianren.ih_patient.activity.health_manager.view.HealthSummaryDetailView;
import com.keydom.mianren.ih_patient.utils.AndroidCartUtils;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

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


    private ArrayList<Integer> lineData = new ArrayList<>();
    private ArrayList<Integer> barData = new ArrayList<>();

    private String[] typeData = {"血压", "血糖", "血脂", "心率"};
    private String[] lifestyleLabels = {"饮食(95分)", "运动(30分)", "睡眠(50分)", "饮酒(100分)", "吸烟(80分)",};
    private Integer[] lifestyleData = {95, 30, 50, 100, 80};

    /**
     * 启动
     */
    public static void start(Context context) {
        context.startActivity(new Intent(context, HealthSummaryDetailActivity.class));
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_health_summary_detail;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initCombinedChart();

        initRadarChart();
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

        CombinedData combinedData = new CombinedData();

        lineData.add(15);
        lineData.add(10);
        lineData.add(7);
        lineData.add(18);

        barData.add(5);
        barData.add(16);
        barData.add(14);
        barData.add(11);
        combinedData.setData(AndroidCartUtils.generateLineData( lineData));
        combinedData.setData(AndroidCartUtils.generateBarData( barData));

        //以下是为了解决 柱x状图 左右两边只显示了一半的问题 根据实际情况 而定
        combinedChart.getXAxis().setAxisMinimum(-0.5f);
        combinedChart.getXAxis().setAxisMaximum((float) (3.5));
        //设置图表最大可见和最小可见个数。既可以固定可见个数。
        combinedChart.setVisibleXRangeMaximum(5);
        combinedChart.setData(combinedData);
        combinedChart.invalidate();

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
        xAxis.setValueFormatter((value, axis) -> lifestyleLabels[(int) value]);

        YAxis yAxis = radarChart.getYAxis();
        // 要达到100需要把该值设为80，
        yAxis.setAxisMaximum(90f);
        //隐藏每层数据
        yAxis.setValueFormatter((value, axis) -> "");
        yAxis.setAxisMinimum(0f);

        radarChart.setData(AndroidCartUtils.getChartData(lifestyleData));
    }

}
