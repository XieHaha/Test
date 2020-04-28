package com.keydom.ih_common.view.picker.view;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.keydom.ih_common.R;
import com.keydom.ih_common.view.picker.builder.TimePickerBuilder;
import com.keydom.ih_common.view.picker.listener.CustomListener;
import com.keydom.ih_common.view.picker.listener.OnTimeSelectListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author 顿顿
 * @date 19/6/28 16:40
 * @description
 */
public class TimePickerHelper {
    private static TimePickerView timePickerView;

    public static void showTimePicker(Context context, boolean[] show, final CallBack callBack) {
        Calendar startDate = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd-HH");
        String time = simpleDateFormat.format(new Date());
        String[] strings = time.split("-");
        startDate.set(Integer.parseInt(strings[0]), Integer.parseInt(strings[1]) - 1,
                Integer.parseInt(strings[02]),
                Integer.parseInt(strings[3]) + 1, 0);
        Calendar endDate = Calendar.getInstance();
        int endMonth = Integer.parseInt(strings[1]) == 1 ? 12 : Integer.parseInt(strings[1]) - 1;
        endDate.set(Integer.parseInt(strings[0]) + 1, endMonth, 31, 23, 0);
        timePickerView = new TimePickerBuilder(context, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                //选中事件回调
                if (callBack != null) {
                    callBack.result(date);
                }
            }
        }).setRangDate(startDate, endDate).setLayoutRes(R.layout.view_time_picker,
                new CustomListener() {
            @Override
            public void customLayout(View v) {
                final TextView tvSubmit = v.findViewById(R.id.tv_sure);
                TextView ivCancel = v.findViewById(R.id.tv_cancel);
                tvSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        timePickerView.returnData();
                        timePickerView.dismiss();
                    }
                });
                ivCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        timePickerView.dismiss();
                    }
                });
            }
        }).setType(show)
                .setLabel("年", "月", "日", "时", "分", "秒")
                .setLineSpacingMultiplier(2.5f)
                .setContentTextSize(18)
                .setTextColorCenter(ContextCompat.getColor(context, R.color.fontColorPrimary))
                .setTextColorOut(ContextCompat.getColor(context, R.color.color_69))
                .setTextXOffset(0, 0, 0, 0, 0, 0)
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setDividerColor(ContextCompat.getColor(context, R.color.color_69)).build();
        timePickerView.show();
    }

    public interface CallBack {
        void result(Date date);
    }
}
