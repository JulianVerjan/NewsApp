package com.test.bonialtest.view.utils

import android.content.Context
import com.test.bonialtest.R
import com.test.bonialtest.view.utils.Constants.DATE_FORMAT
import com.test.bonialtest.view.utils.Constants.SIMPLE_PATTERN
import com.test.bonialtest.view.utils.Constants.TIME_FORMAT
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object TimePassed {

    private lateinit var dateTimeNow: Date
    private lateinit var timeFromData: String
    private lateinit var pastDate: String

    private const val SECOND_MILLIS = 1000
    private const val MINUTE_MILLIS = 60 * SECOND_MILLIS
    private const val HOUR_MILLIS = 60 * MINUTE_MILLIS
    private const val DAY_MILLIS = 24 * HOUR_MILLIS
    private const val WEEKS_MILLIS = 7 * DAY_MILLIS

    fun getTimeAgo(startDate: Date, context: Context): String {

        val simpleDateFormat = SimpleDateFormat(SIMPLE_PATTERN, Locale.ENGLISH)
        val dateFormat = SimpleDateFormat(DATE_FORMAT, Locale.ENGLISH)
        val timeFormat = SimpleDateFormat(TIME_FORMAT, Locale.ENGLISH)

        val now = Date()
        val sDateTimeNow = simpleDateFormat.format(now)

        try {
            dateTimeNow = simpleDateFormat.parse(sDateTimeNow)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        val endDate = dateTimeNow

        val different = endDate.time - startDate.time

        when {
            different < MINUTE_MILLIS -> return context.resources.getString(R.string.just_now)
            different < 2 * MINUTE_MILLIS -> return context.resources.getString(R.string.a_min_ago)
            different < 50 * MINUTE_MILLIS -> return (different / MINUTE_MILLIS).toString() + context.getString(R.string.minutes_ago)
            different < 90 * MINUTE_MILLIS -> return context.getString(R.string.a_hour_ago)
            different < 24 * HOUR_MILLIS -> {
                timeFromData = timeFormat.format(startDate)
                return timeFromData
            }
            different < 48 * HOUR_MILLIS -> return context.getString(R.string.yesterday)
            different < 7 * DAY_MILLIS -> return (different / DAY_MILLIS).toString() + context.getString(R.string.days_ago)
            different < 2 * WEEKS_MILLIS -> return (different / WEEKS_MILLIS).toString() + context.getString(R.string.week_ago)
            different < 3.5 * WEEKS_MILLIS -> return (different / WEEKS_MILLIS).toString() + context.getString(R.string.weeks_ago)
            else -> {
                pastDate = dateFormat.format(startDate)
                return pastDate
            }
        }
    }
}