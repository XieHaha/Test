package com.keydom.ih_common.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @Name：com.keydom.ih_doctor.utils
 * @Description：描述信息
 * @Author：song
 * @Date：18/11/22 上午9:50
 * 修改人：xusong
 * 修改时间：18/11/22 上午9:50
 */
public class CalculateTimeUtils {

    public static Long toLong(String time) {
        if (time == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date setTime = null;
        try {
            setTime = sdf.parse(time);
        } catch (ParseException e) {

            e.printStackTrace();
        }
        return setTime.getTime();
    }

    public static String CalculateTime(String time) {
        if (time == null) {
            return "";
        }
        long nowTime = System.currentTimeMillis();
        String msg = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date setTime = null;
        try {
            setTime = sdf.parse(time);
        } catch (ParseException e) {

            e.printStackTrace();
        }
        long reset = setTime.getTime();
        long dateDiff = nowTime - reset;
        if (dateDiff < 0) {
            msg = "";
        } else {
            long dateTemp1 = dateDiff / 1000;
            long dateTemp2 = dateTemp1 / 60;
            long dateTemp3 = dateTemp2 / 60;
            long dateTemp4 = dateTemp3 / 24;
            long dateTemp5 = dateTemp4 / 30;
            long dateTemp6 = dateTemp5 / 12;
            if (dateTemp6 > 0) {
                msg = dateTemp6 + "年前";
            } else if (dateTemp5 > 0) {
                msg = dateTemp5 + "个月前";
            } else if (dateTemp4 > 0) {
                msg = dateTemp4 + "天前";
            } else if (dateTemp3 > 0) {
                msg = dateTemp3 + "小时前";
            } else if (dateTemp2 > 0) {
                msg = dateTemp2 + "分钟前";
            } else if (dateTemp1 > 0) {
                msg = "刚刚";
            }
        }
        return msg;
    }


    public static String getYMDTime(String dateStr) {
        Date date = null;
        DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            date = format1.parse(dateStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return requestDate(date);
    }


    public static String requestDate(Date date) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat formatData = new SimpleDateFormat("MM.dd  HH:mm:ss", Locale.getDefault());
        return formatData.format(date);
    }

    private static long lastClickTime;

    public static boolean isFastClick(long ClickIntervalTime) {
        long ClickingTime = System.currentTimeMillis();
        if (ClickingTime - lastClickTime < ClickIntervalTime) {
            return true;
        }
        lastClickTime = ClickingTime;
        return false;
    }

    public static String getTimeDuration(String startTimeStr, String endTimeStr) {
        Date startTime;
        Date endTime;
        long duration;
        if (startTimeStr == null || "".equals(startTimeStr) || endTimeStr == null || "".equals(endTimeStr)) {
            return "0小时0分钟";
        }
        DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            startTime = format1.parse(startTimeStr);
            endTime = format1.parse(endTimeStr);
            duration = endTime.getTime() - startTime.getTime();
            if (duration > 0)
                return hourDistance(duration) + "小时" + minDistance(duration) + "分钟";
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "0小时0分钟";
    }

    public static int hourDistance(long time) {
        return (int) (time / (1000 * 60 * 60L));
    }

    public static int minDistance(long time) {
        long minTime = (time % (1000 * 60 * 60L));
        return (int) (minTime / (1000 * 60L));
    }

}
