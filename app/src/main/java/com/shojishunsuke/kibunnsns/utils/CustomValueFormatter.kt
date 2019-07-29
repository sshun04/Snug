package com.shojishunsuke.kibunnsns.utils

import com.github.mikephil.charting.formatter.ValueFormatter

class CustomValueFormatter(private val stringValues :List<String>): ValueFormatter() {
    private var valueCount = stringValues.count()
    override fun getFormattedValue(value: Float): String {
        return ""
    }
}