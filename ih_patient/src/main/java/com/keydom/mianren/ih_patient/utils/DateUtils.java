package com.keydom.mianren.ih_patient.utils;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.keydom.ih_common.utils.ToastUtil;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 日期工具类
 */
public class DateUtils {

    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public static final String YYYY_MM_DD_HH_MM_SS_SLASH = "yyyy/MM/dd HH:mm:ss";
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
        SimpleDateFormat sdf = new SimpleDateFormat(YYYY_MM_DD);
        java.util.Date date1 = sdf.parse(d1);
        java.util.Date date2 = sdf.parse(d2);
        c1.setTime(date1);
        c2.setTime(date2);
        if (c2.getTimeInMillis() < c1.getTimeInMillis()) {
            return 0;
        }
        int year1 = c1.get(Calendar.YEAR);
        int year2 = c2.get(Calendar.YEAR);
        int month1 = c1.get(Calendar.MONTH);
        int month2 = c2.get(Calendar.MONTH);
        int day1 = c1.get(Calendar.DAY_OF_MONTH);
        int day2 = c2.get(Calendar.DAY_OF_MONTH);
        int yearInterval = year2 - year1;
        if (month2 < month1 || month1 == month2 && day2 < day1) {
            yearInterval--;
        }
        int monthInterval = (month2 + 12) - month1;
        if (day2 < day1) {
            monthInterval--;
        }
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
        List<String> dateList = getBetweenDate(dateInterval);
        List<List<String>> startTimeList = new ArrayList<>();
        List<List<List<String>>> endTimeList = new ArrayList<>();
        for (int i = 0; i < dateList.size(); i++) {
            List<String> startTimeSonList = new ArrayList<>();
            List<List<String>> endTimeSonList = new ArrayList<>();
            if (i == 0) {
                Date today = new Date();
                SimpleDateFormat hour = new SimpleDateFormat("HH");
                SimpleDateFormat minute = new SimpleDateFormat("mm");
                int hourNum = Integer.parseInt(hour.format(today)) + 2;
                int minuteNum = Integer.parseInt(minute.format(today));
                if (minuteNum > 0) {
                    hourNum += 1;
                }
                if (hourNum < latestTime) {
                    for (int j = hourNum; j < latestTime; j++) {
                        if (j < 10) {
                            startTimeSonList.add("0" + j + ":00");
                        } else {
                            startTimeSonList.add(j + ":00");
                        }
                        List<String> endTimeGrandSonList = new ArrayList<>();
                        for (int k = j + 1; k <= latestTime; k++) {
                            if (k < 10) {
                                endTimeGrandSonList.add("0" + k + ":00");
                            } else {
                                if (k == 24) {
                                    endTimeGrandSonList.add("23:59");
                                } else {
                                    endTimeGrandSonList.add(k + ":00");
                                }
                            }
                        }
                        endTimeSonList.add(endTimeGrandSonList);
                    }
                } else {
                    startTimeSonList.add("当前不在");
                    List<String> endTimeGrandSonList = new ArrayList<>();
                    endTimeGrandSonList.add("服务时间段");
                    endTimeSonList.add(endTimeGrandSonList);
                }

            } else {
                for (int j = earliestTime; j < latestTime; j++) {
                    if (j < 10) {
                        startTimeSonList.add("0" + j + ":00");
                    } else {
                        startTimeSonList.add(j + ":00");
                    }
                    List<String> endTimeGrandSonList = new ArrayList<>();
                    for (int k = j + 1; k <= latestTime; k++) {
                        if (k < 10) {
                            endTimeGrandSonList.add("0" + k + ":00");
                        } else {
                            if (k == 24) {
                                endTimeGrandSonList.add("23:59");
                            } else {
                                endTimeGrandSonList.add(k + ":00");
                            }
                        }
                    }
                    endTimeSonList.add(endTimeGrandSonList);
                }
            }

            startTimeList.add(startTimeSonList);
            endTimeList.add(endTimeSonList);
        }
        OptionsPickerView optionsPickerView = new OptionsPickerBuilder(context,
                new OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int options2, int options3, View v) {
                        if ("服务时间段".equals(endTimeList.get(options1).get(options2).get(options3))) {
                            ToastUtil.showMessage(context, "当前不在服务时间段，请重新选择上门时间");
                        } else {
                            dateIntervalSelectedListener.getSelectedDateInterval(dateList.get(options1),
                                    startTimeList.get(options1).get(options2),
                                    endTimeList.get(options1).get(options2).get(options3));
                        }
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
     * 获取两个日期字符串之间的日期集合
     *
     * @return list:yyyy-MM-dd
     */
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

    /**
     * 时间格式 yyyy-MM-dd HH:mm:ss 转 yyyy-MM-dd
     *
     * @param dateStr
     * @return
     */
    public static String getYMDfromYMDHMS(String dateStr) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat valueSf = new SimpleDateFormat("yyyy-MM-dd");
        Date valueDate = null;
        try {
            valueDate = sf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return valueSf.format(valueDate);
    }

    /**
     * 时间格式 yyyy-MM-dd HH:mm:ss 转 yyyy-MM-dd
     *
     * @param dateStr
     * @return
     */
    public static String getYMDfromYMDHMSNoFormat(String dateStr) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat valueSf = new SimpleDateFormat("yyyyMMdd");
        Date valueDate = null;
        try {
            valueDate = sf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return valueSf.format(valueDate);
    }

    /**
     * 比较日期
     *
     * @param firstDate
     * @param secondDate
     * @return
     */
    public static boolean compareDate(String firstDate, String secondDate) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date dt1 = df.parse(firstDate);
            Date dt2 = df.parse(secondDate);
            if (dt1.getTime() > dt2.getTime()) {
                return true;
            } else if (dt1.getTime() < dt2.getTime()) {
                return false;
            } else {
                return true;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return false;
    }

    /**
     * 转换当前日期
     *
     * @param firstDate
     * @return
     */
    public static boolean compareDateWithNow(String firstDate) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date dt1 = df.parse(firstDate);
            Date nowDate = new Date();
            if (dt1.getTime() > nowDate.getTime()) {
                return true;
            } else if (dt1.getTime() < nowDate.getTime()) {
                return false;
            } else {
                return false;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return false;
    }


    /**
     * String 转 Date
     *
     * @param date
     * @return
     */
    public static String dateToString(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(date);
    }

    public static String transDate(String dateString, String formatSource, String formatResult) {
        if (TextUtils.isEmpty(dateString)) {
            return "";
        }
        SimpleDateFormat source = new SimpleDateFormat(formatSource);
        SimpleDateFormat result = new SimpleDateFormat(formatResult);

        try {
            return result.format(source.parse(dateString).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getDate(String date, String pattern) {
        if (TextUtils.isEmpty(date)) {
            return "";
        }
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(new Date(Long.valueOf(date)));
    }

    /**
     * 计算date天数差
     */
    public static int dateDifferent(Date date1, Date date2) {
        return (int) ((date2.getTime() - date1.getTime()) / (1000 * 3600 * 24));
    }

    /**
     * 获取日期区间 月份
     */
    public static String getInterValDate(Date date, int interval) {
        Calendar calendar = Calendar.getInstance();
        //当前时间设置给calendar
        calendar.setTime(date);
        //当前时间的前几个月
        calendar.add(Calendar.MONTH, interval);
        //得到个月前的时间
        Date ago = calendar.getTime();
        return dateToString(ago);
    }

    /**
     * 获取日期区间  天数
     */
    public static Date getInterValDay(Date date, int interval) {
        Calendar calendar = Calendar.getInstance();
        //当前时间设置给calendar
        calendar.setTime(date);
        //当前时间的前几个月
        calendar.add(Calendar.DAY_OF_YEAR, interval);
        //得到个月前的时间
        return calendar.getTime();
    }

    public static int getMonthDiffer(String start) {
        SimpleDateFormat sdf = new SimpleDateFormat(YYYY_MM_DD);
        Calendar bef = Calendar.getInstance();
        Calendar aft = Calendar.getInstance();
        try {
            bef.setTime(sdf.parse(start));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int month = (aft.get(Calendar.YEAR) - bef.get(Calendar.YEAR)) * 12;
        int result = aft.get(Calendar.MONTH) - bef.get(Calendar.MONTH);
        return month + result;
    }
}
