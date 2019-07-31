package com.keydom.ih_patient.adapter;

import android.annotation.SuppressLint;
import android.support.annotation.Nullable;

import com.blankj.utilcode.util.StringUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.keydom.ih_common.utils.GlideUtils;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.my_doctor_or_nurse.MyDoctorOrNurseActivity;
import com.keydom.ih_patient.bean.DoctorOrNurseBean;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * created date: 2019/1/2 on 14:46
 * des:我的医生适配器
 */
public class MyDoctorOrNurseAdapter extends BaseQuickAdapter<DoctorOrNurseBean, BaseViewHolder> {


    /**
     * 构建方法
     */
    public MyDoctorOrNurseAdapter(@Nullable List<DoctorOrNurseBean> data) {
        super(R.layout.item_my_doctor_or_nurse, data);
    }

    @Override
    @SuppressLint("SimpleDateFormat")
    protected void convert(BaseViewHolder helper, DoctorOrNurseBean item) {
        GlideUtils.load(helper.getView(R.id.head_img), item.getImage() == null ? "" : item.getImage(), 0, R.mipmap.im_default_head_image, false, null);
        String time = "";
        if (!StringUtils.isEmpty(item.getContent())) {
            time = getNewChatTime(item.getTime());
        }
        helper.setText(R.id.name, StringUtils.isEmpty(item.getName()) ? "" : item.getName())
                .setText(R.id.message, StringUtils.isEmpty(item.getContent()) ? "" : item.getContent())
                .setText(R.id.time, time)
                .setGone(R.id.msg_num, item.getContentNum() != 0)
                .setText(R.id.msg_num, item.getContentNum() + "");
        switch (item.getType()) {
            case MyDoctorOrNurseActivity.DOCTOR:
                helper.setText(R.id.department, StringUtils.isEmpty(item.getDeptName()) ? "" : item.getDeptName())
                        .setText(R.id.jobName, StringUtils.isEmpty(item.getJobTitleName()) ? "" : item.getJobTitleName());
                break;

            case MyDoctorOrNurseActivity.NURSE:
                helper.setText(R.id.department, item.getJobTitleName() + "");
                break;
        }
    }

    static String dayNames[] = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};

    /**
     * 时间戳格式转换
     */
    public static String getNewChatTime(long timestamp) {
        String result = "";

        Calendar curCalendar = Calendar.getInstance();
        Calendar showCalendar = Calendar.getInstance();
        showCalendar.setTimeInMillis(timestamp);

        String timeFormat;
        String yearTimeFormat;
        String am_pm = "";
        int hour = showCalendar.get(Calendar.HOUR_OF_DAY);
        if (hour >= 0 && hour < 6) {
            am_pm = "凌晨";
        } else if (hour >= 6 && hour < 12) {
            am_pm = "早上";
        } else if (hour == 12) {
            am_pm = "中午";
        } else if (hour > 12 && hour < 18) {
            am_pm = "下午";
        } else if (hour >= 18) {
            am_pm = "晚上";
        }
        timeFormat = "M月d日 " + am_pm + "HH:mm";
        yearTimeFormat = "yyyy年M月d日 " + am_pm + "HH:mm";

        boolean yearTemp = curCalendar.get(Calendar.YEAR) == showCalendar.get(Calendar.YEAR);
        if (yearTemp) {
            int curMonth = curCalendar.get(Calendar.MONTH);
            int showMonth = showCalendar.get(Calendar.MONTH);
            // 表示是同一个月
            if (curMonth == showMonth) {
                int temp = curCalendar.get(Calendar.DATE) - showCalendar.get(Calendar.DATE);
                switch (temp) {
                    case 0:
                        result = getHourAndMin(timestamp);
                        break;
                    case 1:
                        result = "昨天 " + getHourAndMin(timestamp);
                        break;
                    case 2:
                    case 3:
                    case 4:
                    case 5:
                    case 6:
                        int curDayOfMonth = curCalendar.get(Calendar.WEEK_OF_MONTH);
                        int showDayOfMonth = showCalendar.get(Calendar.WEEK_OF_MONTH);
                        // 表示是同一周
                        if (showDayOfMonth == curDayOfMonth) {
                            int dayOfWeek = showCalendar.get(Calendar.DAY_OF_WEEK);
                            //判断当前是不是星期日     如想显示为：周日 12:09 可去掉此判断
                            if (dayOfWeek != 1) {
                                result = dayNames[showCalendar.get(Calendar.DAY_OF_WEEK) - 1]
                                        + getHourAndMin(timestamp);
                            } else {
                                result = getTime(timestamp, timeFormat);
                            }
                        } else {
                            result = getTime(timestamp, timeFormat);
                        }
                        break;
                    default:
                        result = getTime(timestamp, timeFormat);
                        break;
                }
            } else {
                result = getTime(timestamp, timeFormat);
            }
        } else {
            result = getYearTime(timestamp, yearTimeFormat);
        }
        return result;
    }

    /**
     * 当天的显示时间格式
     */
    public static String getHourAndMin(long time) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        return format.format(new Date(time));
    }

    /**
     * 不同一周的显示时间格式
     */
    public static String getTime(long time, String timeFormat) {
        SimpleDateFormat format = new SimpleDateFormat(timeFormat);
        return format.format(new Date(time));
    }

    /**
     * 不同年的显示时间格式
     *
     */
    public static String getYearTime(long time, String yearTimeFormat) {
        SimpleDateFormat format = new SimpleDateFormat(yearTimeFormat);
        return format.format(new Date(time));
    }
}
