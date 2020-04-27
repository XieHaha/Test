package com.keydom.mianren.ih_doctor.utils;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateUtils {

    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public static final String YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
    public static final String YYYY_MM_DD = "yyyy-MM-dd";
    public static final String MM_DD_CH = "MM月dd日";
    public static final String YYYY_MM_DD_CH = "yyyy年MM月dd日";
    /**
     *     * 获取两个日期相差的月数
     *     * @param d2  较大的日期
     *     * @param d1  较小的日期
     *     * @return 如果d1>d2返回 月数差 否则返回0
     *    
     */
    public static int getMonthDiff(String d1, String d2) throws ParseException {
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
        Date date1 = sdf.parse(d1);
        Date date2 = sdf.parse(d2);
        c1.setTime(date1);
        c2.setTime(date2);
        if (c2.getTimeInMillis() < c1.getTimeInMillis())
            return 0;
        int year1 = c1.get(Calendar.YEAR);
        int year2 = c2.get(Calendar.YEAR);
        int month1 = c1.get(Calendar.MONTH);
        int month2 = c2.get(Calendar.MONTH);
        int day1 = c1.get(Calendar.DAY_OF_MONTH);
        int day2 = c2.get(Calendar.DAY_OF_MONTH);
        int yearInterval = year2 - year1;
        if (month2 < month1 || month1 == month2 && day2 < day1)
            yearInterval--;
        int monthInterval = (month2 + 12) - month1;
        if (day2 < day1)
            monthInterval--;
        monthInterval %= 12;
        return yearInterval * 12 + monthInterval;
    }

    /**
     * 弹出时间区间选择器
     *
     * @param context
     * @param dateInterval 日期区间大小，区间为从当天算起至往后推dateInterval天
     * @param earliestTime 单日开始时间 可以是0到24之间任意值
     * @param latestTime   单日结束时间 可以是0到24之间任意值
     */
    public static OptionsPickerView showDateIntervalChooseDialog(Context context,
                                                                 int dateInterval,
                                                                 int earliestTime, int latestTime
            , DateIntervalSelectedListener dateIntervalSelectedListener) {
        Date date = new Date();
        int currentHours = date.getHours();
        int mEarliestTime = earliestTime;
        int mLatestTime = latestTime;
        List<String> dateList;
        dateList = getBetweenDate(dateInterval, !(currentHours >= latestTime - 1));
        List<List<String>> startTimeList = new ArrayList<>();
        List<List<List<String>>> endTimeList = new ArrayList<>();
        for (int i = 0; i < dateList.size(); i++) {
            List<String> startTimeSonList = new ArrayList<>();
            List<List<String>> endTimeSonList = new ArrayList<>();
            if (i == 0 && currentHours >= mEarliestTime && currentHours < mLatestTime - 1) {
                mEarliestTime = currentHours + 1;
            } else {
                mEarliestTime = earliestTime;
            }
            for (int j = mEarliestTime; j < mLatestTime; j++) {
                if (j < 10)
                    startTimeSonList.add("0" + j + ":00");
                else if (j == 24)
                    startTimeSonList.add("23:59");

                else
                    startTimeSonList.add(j + ":00");
                List<String> endTimeGrandSonList = new ArrayList<>();
                for (int k = j + 1; k <= mLatestTime; k++) {
                    if (k < 10)
                        endTimeGrandSonList.add("0" + k + ":00");
                    else if (k == 24)
                        endTimeGrandSonList.add("23:59");
                    else
                        endTimeGrandSonList.add(k + ":00");
                }
                endTimeSonList.add(endTimeGrandSonList);
            }
            startTimeList.add(startTimeSonList);
            endTimeList.add(endTimeSonList);
        }
        OptionsPickerView optionsPickerView = new OptionsPickerBuilder(context,
                new OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int options2, int options3, View v) {
                        dateIntervalSelectedListener.getSelectedDateInterval(dateList.get(options1),
                                startTimeList.get(options1).get(options2),
                                endTimeList.get(options1).get(options2).get(options3));
                    }
                })
                .setCancelColor(Color.parseColor("#00FFFFFF"))
                .setTitleText("预约上门时间")
                .setTitleColor(Color.parseColor("#333333"))
                .setSubmitColor(Color.parseColor("#3F98F7"))
                .build();
        optionsPickerView.setPicker(dateList, startTimeList, endTimeList);
        return optionsPickerView;

    }


    /**
     * 弹出时间区间选择器
     *
     * @param context
     * @param earliestTime 单日开始时间 可以是0到24之间任意值
     * @param latestTime   单日结束时间 可以是0到24之间任意值
     */
    public static OptionsPickerView showDateIntervalChooseDialog(Context context,
                                                                 int earliestTime, int latestTime
            , DateIntervalSelectedListener dateIntervalSelectedListener) {
        List<String> startTimeList = new ArrayList<>();
        List<List<String>> endTimeList = new ArrayList<>();
        List<String> startTimeSonList = new ArrayList<>();
        List<List<String>> endTimeSonList = new ArrayList<>();
        for (int j = earliestTime; j < latestTime; j++) {
            if (j < 10)
                startTimeSonList.add("0" + j + ":00");
            else
                startTimeSonList.add(j + ":00");
            List<String> endTimeGrandSonList = new ArrayList<>();
            for (int k = j + 1; k <= latestTime; k++) {
                if (k < 10)
                    endTimeGrandSonList.add("0" + k + ":00");
                else if (k == 24)
                    endTimeGrandSonList.add("24:00");
                else
                    endTimeGrandSonList.add(k + ":00");
            }
            endTimeSonList.add(endTimeGrandSonList);
        }
        startTimeList.addAll(startTimeSonList);
        endTimeList.addAll(endTimeSonList);
        OptionsPickerView optionsPickerView = new OptionsPickerBuilder(context,
                new OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int options2, int options3, View v) {
                        dateIntervalSelectedListener.getSelectedDateInterval(null,
                                startTimeList.get(options1),
                                endTimeList.get(options1).get(options2).equals("24:00") ? "23:59" :
                                        endTimeList.get(options1).get(options2));
                    }
                })
                .setCancelColor(Color.parseColor("#00FFFFFF"))
                .setTitleText("选择起止时间")
                .setTitleColor(Color.parseColor("#333333"))
                .setSubmitColor(Color.parseColor("#3F98F7"))
                .build();
        optionsPickerView.setPicker(startTimeList, endTimeList);
        return optionsPickerView;

    }


    /**
     * 获取两个日期字符串之间的日期集合
     *
     * @return list:yyyy-MM-dd
     */
    private static List<String> getBetweenDate(int dateInterval, boolean isToday) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List<String> list = new ArrayList<String>();
        Date startDate = isToday ? new Date() :
                new Date((new Date().getTime() + (24 * 60 * 60 * 1000)));
        Calendar calendar = Calendar.getInstance();
        for (int i = 0; i < dateInterval; i++) {
            list.add(sdf.format(startDate));
            calendar.setTime(startDate);
            calendar.add(Calendar.DATE, 1);
            startDate = calendar.getTime();
        }
        return list;
    }

    private static List<String> getBetweenDate(int dateInterval) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List<String> list = new ArrayList<String>();
        Date startDate = new Date();
        Calendar calendar = Calendar.getInstance();
        for (int i = 0; i < dateInterval; i++) {
            list.add(sdf.format(startDate));
            calendar.setTime(startDate);
            calendar.add(Calendar.DATE, 1);
            startDate = calendar.getTime();
        }
        return list;
    }

    public interface DateIntervalSelectedListener {
        void getSelectedDateInterval(String date, String startTime, String endTime);
    }


    public static String numberToCH(int intInput) {
        String si = String.valueOf(intInput);
        String sd = "";
        if (si.length() == 1) // 個
        {
            sd += GetCH(intInput);
            return sd;
        } else if (si.length() == 2)// 十
        {
            if (si.substring(0, 1).equals("1"))
                sd += "十";
            else
                sd += (GetCH(intInput / 10) + "十");
            sd += numberToCH(intInput % 10);
        } else if (si.length() == 3)// 百
        {
            sd += (GetCH(intInput / 100) + "百");
            if (String.valueOf(intInput % 100).length() < 2)
                sd += "零";
            sd += numberToCH(intInput % 100);
        } else if (si.length() == 4)// 千
        {
            sd += (GetCH(intInput / 1000) + "千");
            if (String.valueOf(intInput % 1000).length() < 3)
                sd += "零";
            sd += numberToCH(intInput % 1000);
        } else if (si.length() == 5)// 萬
        {
            sd += (GetCH(intInput / 10000) + "萬");
            if (String.valueOf(intInput % 10000).length() < 4)
                sd += "零";
            sd += numberToCH(intInput % 10000);
        }

        return sd;
    }

    private static String GetCH(int input) {
        String sd = "";
        switch (input) {
            case 1:
                sd = "一";
                break;
            case 2:
                sd = "二";
                break;
            case 3:
                sd = "三";
                break;
            case 4:
                sd = "四";
                break;
            case 5:
                sd = "五";
                break;
            case 6:
                sd = "六";
                break;
            case 7:
                sd = "七";
                break;
            case 8:
                sd = "八";
                break;
            case 9:
                sd = "九";
                break;
            default:
                break;
        }
        return sd;
    }

    public static String getDate(String date) {
        if (TextUtils.isEmpty(date)) {
            return "";
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh-mm-ss");
        return format.format(new Date(Long.valueOf(date)));
    }

    /**
     * String 转 Date
     */
    public static String dateToString(Date date,String pattern) {
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        return formatter.format(date);
    }

}
