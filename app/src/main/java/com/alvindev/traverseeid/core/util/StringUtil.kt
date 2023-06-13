package com.alvindev.traverseeid.core.util

fun String.seperateNewLine(): String {
    return this.replace("\\n", "\r\n")
}