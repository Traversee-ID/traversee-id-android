package com.alvindev.traverseeid.core.util

fun Int?.digitSeparator(): String {
    return if (this == null) {
        "0"
    } else {
        val number = this.toLong()
        val numberFormat = java.text.NumberFormat.getNumberInstance(java.util.Locale.US)
        numberFormat.format(number).toString()
    }
}