package com.alvindev.traverseeid.feature_campaign.util

import android.util.Log
import java.util.*

object CampaignUtil {
    val months = arrayOf(
        "January", "February", "March", "April", "May", "June", "July", "August", "September",
        "October", "November", "December"
    )

    fun calculateDaysLeft(date: String?): Int {
       if(date==null) return 0

        val d = date.split(" ")
        val day = d[0].toInt()
        val month = months.find { it == d[1] }?.let { months.indexOf(it) } ?: return 0
        val year = d[2].toInt()

        val endDate = Calendar.getInstance()
        endDate.set(year, month, day)
        val today = Calendar.getInstance()
        val diff = endDate.timeInMillis - today.timeInMillis

        if(diff < 0) return 0

        val days = diff / (24 * 60 * 60 * 1000)
        return days.toInt()
    }
}