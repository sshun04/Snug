package com.shojishunsuke.kibunnsns.ext

import java.text.SimpleDateFormat
import java.util.*

/**
 * Dateの拡張関数群
 *
 * @author shun
 */
fun Date.postedTime():String{
    val currentDate = Date()
    val timeDiffInSec = (currentDate.time - this.time) / 1000

    val hourDiff = timeDiffInSec / 3600
    val minuteDiff = (timeDiffInSec % 3600) / 60
    val secDiff = timeDiffInSec % 60

    return when {
        timeDiffInSec in 3600 * 24 until 3600 * 48 -> {
            "昨日"
        }
        timeDiffInSec in 3600 until 3600 * 24 -> {
            "$hourDiff" + "時間前"
        }
        timeDiffInSec in 360 until 3600 -> {
            "$minuteDiff" + "分前"
        }
        timeDiffInSec < 360 -> {
            "$secDiff" + "秒前"
        }
        else -> {
            val formatter = SimpleDateFormat("MM月dd日", Locale.JAPAN)
            formatter.format(this)
        }
    }
}

fun Date.timeInDay():String{
    val formatter = SimpleDateFormat("HH:mm", Locale.JAPAN)
    return formatter.format(this)
}