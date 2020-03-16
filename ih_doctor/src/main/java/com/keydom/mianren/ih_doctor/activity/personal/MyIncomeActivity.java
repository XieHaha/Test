package com.keydom.mianren.ih_doctor.activity.personal;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ganxin.library.LoadDataLayout;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.utils.StatusBarUtils;
import com.keydom.mianren.ih_doctor.R;
import com.keydom.mianren.ih_doctor.activity.personal.controller.MyIncomeController;
import com.keydom.mianren.ih_doctor.activity.personal.view.MyIncomeView;
import com.keydom.mianren.ih_doctor.bean.MyIncomeBean;
import com.keydom.mianren.ih_doctor.view.MyMarkerView;
import com.keydom.mianren.ih_doctor.view.PercentPieView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @Name：com.keydom.ih_doctor.activity.personal
 * @Description：我的收入页面
 * @Author：song
 * @Date：18/11/14 下午3:05
 * 修改人：xusong
 * 修改时间：18/11/14 下午3:05
 */
public class MyIncomeActivity extends BaseControllerActivity<MyIncomeController> implements MyIncomeView {
    private LineChart lineChart;
    private PercentPieView picChart;
    /**
     * 收入列表
     */
    private List<Float> list = new ArrayList<>();
    /**
     * 收入对象
     */
    private MyIncomeBean incomeBean;
    private TextView sameMonthIncome, totalIncome, currentDate, imageDiagnose, phoneDiagnose, videoDiagnose, orderRecoderDate;

    /**
     * 启动
     */
    public static void start(Context context) {
        Intent starter = new Intent(context, MyIncomeActivity.class);
        context.startActivity(starter);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_my_income_layout;
    }


    @Override
    public void initData(@org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        setTitle("我的收入");
        getTitleLayout().setWhiteBar();
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
        StatusBarUtils.setWindowStatusBarColor(this, R.color.income_bg);
        lineChart = (LineChart) findViewById(R.id.lineChart);
        lineChart.setDragEnabled(false);
        lineChart.setScaleEnabled(false);
        lineChart.setDoubleTapToZoomEnabled(false);
        picChart = (PercentPieView) findViewById(R.id.pic_chat);
        initView();
        pageLoading();
        getController().getIncome();
        setReloadListener(new LoadDataLayout.OnReloadListener() {
            @Override
            public void onReload(View v, int status) {
                pageLoading();
                getController().getIncome();
            }
        });
    }

    /**
     * 查找控件
     */
    private void initView() {
        sameMonthIncome = this.findViewById(R.id.same_month_income);
        totalIncome = this.findViewById(R.id.total_income);
        currentDate = this.findViewById(R.id.current_date);
        imageDiagnose = this.findViewById(R.id.image_diagnose);
        phoneDiagnose = this.findViewById(R.id.phone_diagnose);
        videoDiagnose = this.findViewById(R.id.video_diagnose);
        orderRecoderDate = this.findViewById(R.id.order_recoder_date);
    }

    /**
     * 设置控件显示信息
     */
    private void setInfo(MyIncomeBean bean) {
        initLineChart(bean);
        initCircleChat(bean);
        sameMonthIncome.setText(String.valueOf(bean.getMonthIncome()));
        totalIncome.setText(String.valueOf(bean.getTotalIncome()));
        currentDate.setText("当前日期:" + bean.getCurrentDate());
        imageDiagnose.setText(String.valueOf(bean.getImageOrderNumber()));
        phoneDiagnose.setText(String.valueOf(bean.getTelOrderNumber()));
        videoDiagnose.setText(String.valueOf(bean.getVideoOrderNumber()));
        orderRecoderDate.setText("订单记录(" + bean.getCurrentDate() + ")");
    }

    /**
     * 绘制折线
     */
    private void initLineChart(MyIncomeBean bean) {
        //显示边界
        lineChart.setDrawBorders(false);
        //设置数据
        List<Entry> entries = new ArrayList<>();
        for (int i = 0; i < incomeBean.getList().size(); i++) {
            entries.add(new Entry(i, list.get(i)));
        }
        //一个LineDataSet就是一条线
        LineDataSet lineDataSet = new LineDataSet(entries, "");
        //线颜色
        lineDataSet.setColor(Color.parseColor("#3F98F7"));
        //线宽度
        lineDataSet.setLineWidth(1.6f);
        //不显示圆点
        lineDataSet.setDrawCircles(false);
        //线条平滑
        lineDataSet.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
        //设置折线图填充
//        lineDataSet.setDrawFilled(true);
        LineData data = new LineData(lineDataSet);
        //无数据时显示的文字
        lineChart.setNoDataText("暂无数据");
        //折线图不显示数值
        data.setDrawValues(false);
        //得到X轴
        XAxis xAxis = lineChart.getXAxis();
        //设置X轴的位置（默认在上方)
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        //设置X轴坐标之间的最小间隔
        xAxis.setGranularity(1f);
        //设置X轴的刻度数量，第二个参数为true,将会画出明确数量（带有小数点），但是可能值导致不均匀，默认（6，false）
        xAxis.setLabelCount(list.size(), false);
        //设置X轴的值（最小值、最大值、然后会根据设置的刻度数量自动分配刻度显示）
        xAxis.setAxisMinimum(0f);
        xAxis.setAxisMaximum((float) list.size());
        //不显示网格线
        xAxis.setDrawGridLines(false);
        // 标签倾斜
        xAxis.setLabelRotationAngle(0);
        //设置X轴值为字符串
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                int IValue = (int) value;
                if (IValue >= incomeBean.getList().size()) {
                    return "";
                } else {
                    return incomeBean.getList().get(IValue).getName();
                }

            }
        });
        //得到Y轴
        YAxis yAxis = lineChart.getAxisLeft();
        YAxis rightYAxis = lineChart.getAxisRight();
        //设置Y轴是否显示
        rightYAxis.setEnabled(false); //右侧Y轴不显示
        //设置y轴坐标之间的最小间隔
        //不显示网格线
        yAxis.setDrawGridLines(false);
        //设置Y轴坐标之间的最小间隔
        yAxis.setGranularity(1f);
        //设置y轴的刻度数量
        //+2：最大值n就有n+1个刻度，在加上y轴多一个单位长度，为了好看，so+2
        yAxis.setLabelCount(5, true);
        //设置从Y轴值
        yAxis.setAxisMinimum(0f);
        //+1:y轴多一个单位长度，为了好看
        float maxValue = Collections.max(list);
        int maxLableValueCount = (int) (maxValue / 40);
        maxLableValueCount++;
        yAxis.setAxisMaximum(maxLableValueCount * 40);

        //y轴
        yAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return String.valueOf(value);
            }
        });
        //图例：得到Lengend
        Legend legend = lineChart.getLegend();
        //隐藏Lengend
        legend.setEnabled(false);
        //隐藏描述
        Description description = new Description();
        description.setEnabled(false);
        lineChart.setDescription(description);
        //折线图点的标记
        MyMarkerView mv = new MyMarkerView(this);
        lineChart.setMarker(mv);
        //设置数据
        lineChart.setData(data);
        //图标刷新
        lineChart.invalidate();
    }

    private void initCircleChat(MyIncomeBean bean) {
        int[] data = new int[]{bean.getImageOrderNumber(), bean.getTelOrderNumber(), bean.getVideoOrderNumber()};
        String[] name = new String[]{"图文问诊", "电话问诊", "视频问诊"};
        int[] color = new int[]{
                getResources().getColor(R.color.income_diagnose),
                getResources().getColor(R.color.income_phone),
                getResources().getColor(R.color.income_video)};
        picChart.setData(data, name, color);
        picChart.setCenterText(bean.getTotalOrderNumber());

    }

    @Override
    public void getWalletSuccess(MyIncomeBean bean) {
        if (bean == null) {
            return;
        }
        incomeBean = bean;
        if (bean.getList() != null) {
            for (int i = 0; i < bean.getList().size(); i++) {
                list.add(Float.parseFloat(bean.getList().get(i).getValue()));
            }
        }
        setInfo(bean);
        pageLoadingSuccess();
    }

    @Override
    public void getWalletFailed(String errMsg) {
        pageLoadingFail();
    }
}
