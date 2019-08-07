package com.shojishunsuke.kibunnsns.utils

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