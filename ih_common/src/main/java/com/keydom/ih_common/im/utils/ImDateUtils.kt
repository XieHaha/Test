package com.keydom.ih_common.im.utils

import android.provider.Settings.System
import com.keydom.Common
import com.keydom.ih_common.R
import java.text.SimpleDateFormat
import java.util.*

@Suppress("deprecation")
object ImDateUtils {
    private const val OTHER = 2014
    private const val TODAY = 6
    private const val YESTERDAY = 15

    private fun judgeDate(date: Date): Int {
        val calendarToday = Calendar.getInstance()
        calendarToday.set(Calendar.HOUR_OF_DAY, 0)
        calendarToday.set(Calendar.MINUTE, 0)
        calendarToday.set(Calendar.SECOND, 0)
        calendarToday.set(Calendar.MILLISECOND, 0)
        val calendarYesterday = Calendar.getInstance()
        calendarYesterday.add(Calendar.DAY_OF_MONTH, -1)
        calendarYesterday.set(Calendar.HOUR_OF_DAY, 0)
        calendarYesterday.set(Calendar.MINUTE, 0)
        calendarYesterday.set(Calendar.SECOND, 0)
        calendarYesterday.set(Calendar.MILLISECOND, 0)
        val calendarTomorrow = Calendar.getInstance()
        calendarTomorrow.add(Calendar.DAY_OF_MONTH, 1)
        calendarTomorrow.set(Calendar.HOUR_OF_DAY, 0)
        calendarTomorrow.set(Calendar.MINUTE, 0)
        calendarTomorrow.set(Calendar.SECOND, 0)
        calendarTomorrow.set(Calendar.MILLISECOND, 0)
        val calendarTarget = Calendar.getInstance()
        calendarTarget.time = date
        return if (calendarTarget.before(calendarYesterday)) {
            OTHER
        } else if (calendarTarget.before(calendarToday)) {
            YESTERDAY
        } else {
            if (calendarTarget.before(calendarTomorrow)) TODAY else OTHER
        }
    }

    private fun getWeekDay(dayInWeek: Int): String {
        var weekDay = ""
        when (dayInWeek) {
            1 -> weekDay = Common.getApplication().resources.getString(R.string.im_sunsay_format)
            2 -> weekDay = Common.getApplication().resources.getString(R.string.im_monday_format)
            3 -> weekDay = Common.getApplication().resources.getString(R.string.im_tuesday_format)
            4 -> weekDay = Common.getApplication().resources.getString(R.string.im_wednesday_format)
            5 -> weekDay = Common.getApplication().resources.getString(R.string.im_thuresday_format)
            6 -> weekDay = Common.getApplication().resources.getString(R.string.im_friday_format)
            7 -> weekDay = Common.getApplication().resources.getString(R.string.im_saturday_format)
        }

        return weekDay
    }

    private fun isTime24Hour(): Boolean {
        val timeFormat = System.getString(Common.getApplication().contentResolver, "time_12_24")
        return timeFormat != null && timeFormat == "24"
    }

    private fun getTimeString(dateMillis: Long): String {
        if (dateMillis <= 0L) {
            return ""
        } else {
            val date = Date(dateMillis)
            var formatTime: String? = null
            if (isTime24Hour()) {
                formatTime = formatDate(date, "HH:mm")
            } else {
                val calendarTime = Calendar.getInstance()
                calendarTime.timeInMillis = dateMillis
                var hour = calendarTime.get(Calendar.HOUR)
                if (calendarTime.get(Calendar.AM_PM) == 0) {
                    if (hour < 6) {
                        if (hour == 0) {
                            hour = 12
                        }

                        formatTime = Common.getApplication().resources.getString(R.string.im_daybreak_format)
                    } else if (hour in 6..11) {
                        formatTime = Common.getApplication().resources.getString(R.string.im_morning_format)
                    }
                } else if (hour == 0) {
                    formatTime = Common.getApplication().resources.getString(R.string.im_noon_format)
                    hour = 12
                } else if (hour in 1..5) {
                    formatTime = Common.getApplication().resources.getString(R.string.im_afternoon_format)
                } else if (hour in 6..11) {
                    formatTime = Common.getApplication().resources.getString(R.string.im_night_format)
                }

                val minuteInt = calendarTime.get(Calendar.MINUTE)
                var minuteStr = Integer.toString(minuteInt)
                val timeStr: String?
                if (minuteInt < 10) {
                    minuteStr = "0$minuteStr"
                }

                timeStr = Integer.toString(hour) + ":" + minuteStr
                formatTime = if (Common.getApplication().resources.configuration.locale.country == "CN") {
                    formatTime!! + timeStr
                } else {
                    "$timeStr $formatTime"
                }
            }

            return formatTime
        }
    }

    private fun getDateTimeString(dateMillis: Long, showTime: Boolean): String {
        if (dateMillis <= 0L) {
            return ""
        } else {
            var formatDate = ""
            val date = Date(dateMillis)
            val type = judgeDate(date)
            val time = java.lang.System.currentTimeMillis()
            val calendarCur = Calendar.getInstance()
            val calendarDate = Calendar.getInstance()
            calendarDate.timeInMillis = dateMillis
            calendarCur.timeInMillis = time
            val month = calendarDate.get(Calendar.MONTH)
            val year = calendarDate.get(Calendar.YEAR)
            val weekInMonth = calendarDate.get(Calendar.WEEK_OF_MONTH)
            val monthCur = calendarCur.get(Calendar.MONTH)
            val yearCur = calendarCur.get(Calendar.YEAR)
            val weekInMonthCur = calendarCur.get(Calendar.WEEK_OF_MONTH)
            when (type) {
                TODAY -> formatDate = getTimeString(dateMillis)
                YESTERDAY -> {
                    val formatString = Common.getApplication().resources.getString(R.string.im_yesterday_format)
                    formatDate = if (showTime) {
                        formatString + " " + getTimeString(dateMillis)
                    } else {
                        formatString
                    }
                }
                OTHER -> {
                    formatDate = when {
                        year == yearCur -> if (month == monthCur && weekInMonth == weekInMonthCur) {
                            getWeekDay(calendarDate.get(Calendar.DAY_OF_WEEK))
                        } else if (Common.getApplication().resources.configuration.locale.country == "CN") {
                            formatDate(date, "M" + Common.getApplication().resources.getString(R.string.im_month_format) + "d" + Common.getApplication().resources.getString(R.string.im_day_format))
                        } else {
                            formatDate(date, "d/M")
                        }
                        Common.getApplication().resources.configuration.locale.country == "CN" -> formatDate(date, "yyyy" + Common.getApplication().resources.getString(R.string.im_year_format) + "M" + Common.getApplication().resources.getString(R.string.im_month_format) + "d" + Common.getApplication().resources.getString(R.string.im_day_format))
                        else -> formatDate(date, "d/M/yyyy")
                    }

                    if (showTime) {
                        formatDate = formatDate + " " + getTimeString(dateMillis)
                    }
                }
            }

            return formatDate
        }
    }

    fun getConversationListFormatDate(dateMillis: Long): String? {
        return getDateTimeString(dateMillis, false)
    }

    fun getConversationFormatDate(dateMillis: Long): String {
        return getDateTimeString(dateMillis, true)
    }

    fun isShowChatTime(currentTime: Long, preTime: Long, interval: Int): Boolean {
        val typeCurrent = judgeDate(Date(currentTime))
        val typePre = judgeDate(Date(preTime))
        return typeCurrent != typePre || currentTime - preTime > (interval * 1000).toLong()
    }

    private fun formatDate(date: Date, format: String): String {
        val sdf = SimpleDateFormat(format, Locale.getDefault())
        return sdf.format(date)
    }
}
