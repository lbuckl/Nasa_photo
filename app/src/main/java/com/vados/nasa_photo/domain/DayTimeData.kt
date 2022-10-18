package com.vados.nasa_photo.domain

import android.annotation.SuppressLint
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.icu.util.GregorianCalendar

@SuppressLint("SimpleDateFormat")
class DayTimeData() {
    private var simpleDateFormatSlash:SimpleDateFormat = SimpleDateFormat("yyyy/MM/dd")
    private var simpleDateFormatDash:SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
    private val gcalendar = GregorianCalendar()

    fun getCurrentDate():String{
        simpleDateFormatDash.calendar = gcalendar
        return simpleDateFormatDash.format(gcalendar.time)
    }

    /**
     * получить прошлую дату со смещением на количество дней
     * формат разделителя "/"
     */
    fun getPastDateWithSlash(bias: Int):String{
        val minusBias = bias * (-1)
        gcalendar.add(Calendar.DAY_OF_YEAR, minusBias)
        simpleDateFormatSlash.calendar = gcalendar
        return simpleDateFormatSlash.format(gcalendar.time)
    }

    /**
     * получить прошлую дату со смещением на количество дней
     * формат разделителя "/"
     */
    fun getPastDateWithDash(bias: Int):String{
        val minusBias = bias * (-1)
        gcalendar.add(Calendar.DAY_OF_YEAR, minusBias)
        simpleDateFormatDash.calendar = gcalendar
        return simpleDateFormatDash.format(gcalendar.time)
    }

}