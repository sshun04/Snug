package com.shojishunsuke.kibunnsns.clean_arc.utils

import java.math.BigDecimal

val Double.bd: BigDecimal get() = BigDecimal(this)