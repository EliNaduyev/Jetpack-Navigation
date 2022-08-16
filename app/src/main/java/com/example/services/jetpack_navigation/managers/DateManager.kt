package com.example.services.jetpack_navigation.managers

import android.content.Context
import android.text.format.DateFormat
import android.util.Log
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Eli Naduyev 13.8.22
 */
object DateManager {
    private const val TAG = "MyDateManager"

    fun getCurrentDateWithTime(): String {
        val pattern = "yyyy-MM-dd HH:mm:ss"
        val simpleDateFormat = SimpleDateFormat(pattern)
        val date: String = simpleDateFormat.format(Date())
        Log.d(TAG, "printDateFormats: date - $date") // 2022-08-16 17:38:16
        return date
    }

    /**
     * Important - Formats changes according to the user preference in the PHONE settings
     * 1. use localized time of the user (+,- from the UTC)
     * 2. use 12(AM, PM) OR 24 time format
     */
    fun printDateFormatsAccordingPhone(context: Context){
        val longDateFormat = DateFormat.getLongDateFormat(context).format(Date())
        Log.d(TAG, "test date: getLongDateFormat - $longDateFormat") //July 31, 2022

        val mediumDateFormat = DateFormat.getMediumDateFormat(context).format(Date())
        Log.d(TAG, "test date: getMediumDateFormat - $mediumDateFormat") // July 31, 2022

        val dateFormat = DateFormat.getDateFormat(context).format(Date())
        Log.d(TAG, "test date: getDateFormat - $dateFormat") // 7/31/22

        val timeFormat = DateFormat.getTimeFormat(context).format(Date())
        Log.d(TAG, "test date: getDateFormat - $timeFormat") // 3:01 PM

        Log.d(TAG, "test date: exampleOfCustomFormat - ${exampleOfCustomFormat(context, Date())}") // 7/31/22 | 3:01 PM
    }

    private fun exampleOfCustomFormat(context: Context, date: Date): String {
        val d = DateFormat.getDateFormat(context).format(date)
        val time = DateFormat.getTimeFormat(context).format(date)
        return "$d | $time"
    }

    private fun getFormattedTime(timeInSeconds: Long): String {
        val hours = ((timeInSeconds / (60 * 60) % 24).toString().padStart(2, '0'))
        val minutes = (timeInSeconds / 60 % 60).toString().padStart(2, '0')
        val seconds = (timeInSeconds % 60).toString().padStart(2, '0')
        return "$hours:$minutes:$seconds"
    }
}