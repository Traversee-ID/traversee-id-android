package com.alvindev.traverseeid.core.util

import android.util.Log
import com.alvindev.traverseeid.TraverseeApplication
import java.util.*

fun String?.toDate(): String {
    if (this == null) return ""

    //change GMT format to dd MMM yyyy
    return try{
        val locale = if(TraverseeApplication.LANGUAGE == "in") "id" else "en"
        val format = java.text.SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.ENGLISH)
        val date = format.parse(this)
        val dateFormat = java.text.SimpleDateFormat("dd MMM yyyy", Locale(locale))
        dateFormat.format(date)
    } catch (e: Exception){
        Log.d("DateUtil", "toDate: ${e.message}")
        this
    }
}