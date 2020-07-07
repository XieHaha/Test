package com.keydom.mianren.ih_doctor.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

/**
 * @Name：com.keydom.ih_doctor.utils
 * @Description：描述信息
 * @Author：song
 * @Date：18/11/22 上午9:50
 * 修改人：xusong
 * 修改时间：18/11/22 上午9:50
 */
public class CalculateTimeUtils {

    /**
     * 选择设置默认时间
     */
    public static final int LAST_YEAR = 2050;
    /**
     * 选择设置默认小时
     */
    public static final int LAST_HOUR = 12;
    /**
     * 选择设置默认分钟
     */
    public static final int LAST_MINUTE = 30;

    public static String CalculateTime(String time) {
        long nowTime = System.currentTimeMillis();
        String msg = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date setTime = null;
        try {
            setTime = sdf.parse(time);
        } catch (ParseException e) {

            e.printStackTrace();
        }
        if (setTime != null) {
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
        }
        return msg;
    }


    public static String TimeDistance(String time) {
        if (time == null || "".equals(time)) {
            return "未知";
        }
        long nowTime = System.currentTimeMillis();
        String msg = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date setTime = null;
        try {
            setTime = sdf.parse(time);
        } catch (ParseException e) {

            e.printStackTrace();
        }
        long reset = setTime.getTime();
        long dateDiff = reset - nowTime;
        boolean isOutTime = false;
        if (dateDiff < 0) {
            isOutTime = true;
            dateDiff = -dateDiff;
        }
        long dateTemp1 = dateDiff / 1000;
        long dateTemp2 = dateTemp1 / 60;
        long dateTemp3 = dateTemp2 / 60;

        if (dateTemp3 > 0) {
            msg += dateTemp3 + "小时";
        }

        if (dateTemp2 > 0) {
            msg += dateTemp2 % 60 + "分钟";
        } else if (dateTemp1 > 0) {
            msg = "1分钟";
        } else {
            msg = "已超时" + msg;
        }

        if (isOutTime) {
            msg = "已超时" + msg;
        }

        return msg;
    }


    public static int hourDistance(long time) {
        return (int) (time / (1000 * 60 * 60L));
    }

    public static int minDistance(long time) {
        long minTime = (time % (1000 * 60 * 60L));
        return (int) (minTime / (1000 * 60L));
    }


    public static String requestDate(Date date) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat formatData = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return formatData.format(date);
    }

    public static String getY2mTimeStr(Date date) {
        SimpleDateFormat formatData = new SimpleDateFormat("HH:mm", Locale.getDefault());
        return formatData.format(date);
    }

    public static long getCurrentDayTime() {
        Date date = new Date();
        date.setHours(0);
        date.setMinutes(0);
        date.setSeconds(0);
        return date.getTime();
    }

    public static long getCurrentTime(Date startDate) {
        Date date = new Date();
        if (date.getTime() < startDate.getTime()) {
            startDate.setHours(0);
            startDate.setMinutes(0);
            startDate.setSeconds(0);
            return startDate.getTime();
        }
        return date.getTime();
    }

    public static long getCurrentTime() {
        Date date = new Date();
        return date.getTime();
    }

    public static String getWeek(String i) {
        if (i == null) {
            return "未知";
        }
        String weekStr = "";
        switch (i) {
            case "1":
                weekStr = "星期一";
                break;
            case "2":
                weekStr = "星期二";
                break;
            case "3":
                weekStr = "星期三";
                break;
            case "4":
                weekStr = "星期四";
                break;
            case "5":
                weekStr = "星期五";
                break;
            case "6":
                weekStr = "星期六";
                break;
            case "7":
                weekStr = "星期日";
                break;
            default:
                weekStr = "未知";
        }
        return weekStr;
    }


    public static String getWeekOfDate(Date date) {
        if (date == null) {
            return "";
        }
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0) {
            w = 0;
        }
        return weekDays[w];
    }


    public static String getUiDate(Date date) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat formatData = new SimpleDateFormat("MM月dd日", Locale.getDefault());
        return formatData.format(date);
    }

    public static String getTime(Date date) {
        SimpleDateFormat formatTime = new SimpleDateFormat("HH:mm", Locale.getDefault());
        return formatTime.format(date);
    }

    public static String getBeLeftTime(Date date) {
        SimpleDateFormat formatTime = new SimpleDateFormat("HH:mm", Locale.getDefault());
        formatTime.setTimeZone(TimeZone.getTimeZone("GMT"));
        return formatTime.format(date);
    }

    public static String getYMDTime(Date date) {
        SimpleDateFormat formatTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
                Locale.getDefault());
        return formatTime.format(date);
    }

    public static Date getDate(String dateStr) {
        if (dateStr == null || "".equals(dateStr)) {
            return null;
        }
        Date date = null;
        DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        try {
            date = format1.parse(dateStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }


    public static String getYMDTime(String dateStr) {
        Date date = null;
        DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        try {
            date = format1.parse(dateStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return requestDate(date);
    }

    public static Date getNextNurseServiceTime(String dateStr) {
        Date date = null;
        DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            date = format1.parse(dateStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    public static Date getTime(String dateStr) {
        Date date = null;
        DateFormat format1 = new SimpleDateFormat("HH:mm");
        try {
            date = format1.parse(dateStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String getRoundTime(String dateStr) {
        if ("23:59".equals(dateStr)) {
            dateStr = "24:00";
        }
        return dateStr;
    }

    public static String getAmPmTimeStr(Date date) {
        String value = "";
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTime(date);
        if (gregorianCalendar.get(Calendar.AM_PM) == 0) {
            value = "上午 ";
        } else {
            value = "下午 ";
        }
        SimpleDateFormat format = new SimpleDateFormat("hh:mm");
        value = value + format.format(date);
        return value;

    }

    public static String format(long timeStamp) {
        long curTimeMillis = System.currentTimeMillis();
        Date curDate = new Date(curTimeMillis);
        Date timeStampDate = new Date(timeStamp);
        int todayHoursSeconds = curDate.getHours() * 60 * 60;
        int todayMinutesSeconds = curDate.getMinutes() * 60;
        int todaySeconds = curDate.getSeconds();
        int todayMillis = (todayHoursSeconds + todayMinutesSeconds + todaySeconds) * 1000;
        long todayStartMillis = curTimeMillis - todayMillis;
        if (timeStamp >= todayStartMillis) {
            return getAmPmTimeStr(timeStampDate);
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(new Date(timeStamp));
    }

    public static Date getEndDate() {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return df.parse("2050-01-01");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date();
    }

    public static int getAgeByBirth(String birthDayStr) throws ParseException {
        Date birthDay = getDate(birthDayStr);
        int age;
        Calendar cal = Calendar.getInstance();
        if (cal.before(birthDay)) { //出生日期晚于当前时间，无法计算
            throw new IllegalArgumentException(
                    "The birthDay is before Now.It's unbelievable!");
        }
        int yearNow = cal.get(Calendar.YEAR);  //当前年份
        int monthNow = cal.get(Calendar.MONTH);  //当前月份
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH); //当前日期
        cal.setTime(birthDay);
        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);
        age = yearNow - yearBirth;   //计算整岁数
        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                if (dayOfMonthNow < dayOfMonthBirth) {
                    age--;//当前日期在生日之前，年龄减一
                }
            } else {
                age--;//当前月份在生日之前，年龄减一
            }
        }
        return age;
    }
}
