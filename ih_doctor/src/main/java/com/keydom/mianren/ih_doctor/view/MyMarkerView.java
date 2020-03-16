package com.keydom.mianren.ih_doctor.view;

import android.content.Context;
import android.text.format.DateFormat;
import android.widget.TextView;

import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;
import com.keydom.mianren.ih_doctor.R;

import java.text.DecimalFormat;

/**
 * @Name：com.keydom.ih_doctor.view
 * @Description：描述信息
 * @Author：song
 * @Date：18/12/18 下午1:41
 * 修改人：xusong
 * 修改时间：18/12/18 下午1:41
 */
public class MyMarkerView extends MarkerView {

    private TextView tvContent;
    private DecimalFormat format = new DecimalFormat("##.##");

    public MyMarkerView(Context context) {
        super(context, R.layout.marker_item_layout);
        tvContent = (TextView) findViewById(R.id.income_tv);
    }

    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        tvContent.setText("收入:" + format.format(e.getY()) + "元");
        super.refreshContent(e, highlight);
    }

    //标记相对于折线图的偏移量
    @Override
    public MPPointF getOffset() {
        return new MPPointF(-(getWidth() / 2), -getHeight());
    }

    //时间格式化（显示今日往前30天的每一天日期）
    public String format(float x) {
        CharSequence format = DateFormat.format("MM月dd日",
                System.currentTimeMillis() - (long) (30 - (int) x) * 24 * 60 * 60 * 1000);
        return format.toString();
    }
}
