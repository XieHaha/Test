package com.keydom.mianren.ih_patient.utils;

import android.graphics.Color;

import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.interfaces.datasets.IRadarDataSet;

import java.util.ArrayList;
import java.util.List;

/**
 * @date 21/3/29 13:56
 * @des
 */
public class AndroidCartUtils {


    /**
     * 获取折线数据
     */
    public static LineData generateLineData(ArrayList<Integer> values) {
        LineData lineData = new LineData();
        List<Entry> customCounts = new ArrayList<>();
        for (int i = 0; i < values.size(); i++) {
            customCounts.add(new Entry(i, values.get(i)));
        }
        LineDataSet lineDataSet = new LineDataSet(customCounts, "风险线");
        lineDataSet.setAxisDependency(YAxis.AxisDependency.RIGHT);
        lineDataSet.setColor(Color.parseColor("#00D394"));
        lineDataSet.setCircleHoleColor(Color.parseColor("#00D394"));
        lineDataSet.setCircleHoleRadius(4f);
        lineDataSet.setCircleColor(Color.parseColor("#00D394"));
        lineDataSet.setCircleRadius(4f);
        lineDataSet.setLineWidth(2f);
        lineData.addDataSet(lineDataSet);
        //设置是否显示数据点的数值
        lineData.setDrawValues(false);
        lineData.setHighlightEnabled(false);
        return lineData;
    }

    /**
     * 柱状图数据
     */
    public static BarData generateBarData(List<Integer> values) {
        BarData barData = new BarData();
        //总量
        List<BarEntry> amounts = new ArrayList<>();
        //添加数据
        for (int i = 0; i < values.size(); i++) {
            amounts.add(new BarEntry(i, values.get(i)));
        }
        //设置总数的柱状图
        BarDataSet amountBar = new BarDataSet(amounts, "风险次数");
        amountBar.setAxisDependency(YAxis.AxisDependency.LEFT);
        amountBar.setColor(Color.parseColor("#F61717"));
        amountBar.setValueTextSize(10);
        barData.addDataSet(amountBar);
        //设置柱状图显示的大小
        float barWidth = 0.2f;
        barData.setBarWidth(barWidth);
        //设置是否显示数据点的数值
        barData.setDrawValues(false);
        barData.setHighlightEnabled(false);
        return barData;
    }


    /**
     * 设置图表数据   雷达图
     */
    public static RadarData getChartData(Integer[] lifestyleData) {
        List<IRadarDataSet> dataSets = new ArrayList<>();
        dataSets.add(creatingData(lifestyleData));
        RadarData data = new RadarData(dataSets);
        return data;
    }

    /**
     * 创建虚拟图表数据
     */
    public static RadarDataSet creatingData(Integer[] lable) {
        List<RadarEntry> entries = new ArrayList<>();
        for (int i = 0; i < lable.length; i++) {
            entries.add(new RadarEntry(lable[i]));
        }

        RadarDataSet ds = new RadarDataSet(entries, "生活方式");
        ds.setColor(Color.parseColor("#BBBBBB"));
        ds.setLineWidth(0);
        // 绘制填充，默认为false
        ds.setDrawFilled(true);
        // 填充颜色
        ds.setFillColor(Color.parseColor("#3F98F7"));
        // 填充内容透明度
        ds.setFillAlpha(210);
        // 指定那组数据（RadarDataSet对象）不显示标签
        ds.setDrawValues(false);
        ds.setHighlightEnabled(false);
        return ds;
    }

}
