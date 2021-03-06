package com.shojishunsuke.kibunnsns.ext

import java.text.SimpleDateFormat
import java.util.*

/**
 * Calendarの拡張関数群
 *
 * @author shun
 */
fun Calendar.month(): Int = get(Calendar.MONTH) + 1

fun Calendar.dayOfMonth(): Int = get(Calendar.DAY_OF_MONTH)

fun Calendar.year(): Int = get(Calendar.YEAR)

fun Calendar.monthDays() = getActualMaximum(Calendar.DAY_OF_MONTH)

fun Calendar.diffToMonday(): Int {
    val dayOfWeek = get(Calendar.DAY_OF_WEEK)
    return 2 - dayOfWeek
}

fun Calendar.timeOfDayString(): String {
    val formatter = SimpleDateFormat("HH:mm", Locale.JAPAN)
    return formatter.format(this.time)
}