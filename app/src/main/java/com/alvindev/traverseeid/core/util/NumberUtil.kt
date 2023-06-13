package com.alvindev.traverseeid.core.util

import java.text.NumberFormat
import java.util.*

fun Int?.digitSeparator(): String {
    return if (this == null) {
        "0"
    } else {
        val number = this.toLong()
        val numberFormat = java.text.NumberFormat.getNumberInstance(java.util.Locale.US)
        numberFormat.format(number).toString()
    }
}

fun Int?.currencyFormat(): String {
    return if (this == null) {
        "Rp0"
    } else {
        val price = this.toLong()
        val localeID =  Locale("in", "ID")
        val numberFormat = NumberFormat.getCurrencyInstance(localeID)
        numberFormat.maximumFractionDigits = 0
        numberFormat.format(price).toString()
    }
}

fun String?.currencyFormat(): String {
    return if (this == null) {
        "Rp0"
    } else {
        val price = this.toLong()
        val localeID =  Locale("in", "ID")
        val numberFormat = NumberFormat.getCurrencyInstance(localeID)
        numberFormat.maximumFractionDigits = 0
        numberFormat.format(price).toString()
    }
}