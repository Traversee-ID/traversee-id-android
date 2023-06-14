package com.alvindev.traverseeid.feature_campaign.util

import android.util.Log
import com.alvindev.traverseeid.TraverseeApplication
import java.util.*

object CampaignUtil {
    val months = arrayOf(
        "January", "February", "March", "April", "May", "June", "July", "August", "September",
        "October", "November", "December"
    )

    val monthsId = arrayOf(
        "Januari", "Februari", "Maret", "April", "Mei", "Juni", "Juli", "Agustus",
        "September", "Oktober", "November", "Desember"
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

    fun calculateRewardClaimDeadline(endDateCampaign: String): String{
        val d = endDateCampaign.split(" ")
        val day = d[0].toInt()
        val month = months.find { it == d[1] }?.let { months.indexOf(it) } ?: return ""
        val year = d[2].toInt()

        val endDate = Calendar.getInstance()
        endDate.set(year, month, day)
        endDate.add(Calendar.DAY_OF_MONTH, 14)

        val dayOfMonth = endDate.get(Calendar.DAY_OF_MONTH)
        val monthOfYear = endDate.get(Calendar.MONTH)
        val yearOfDate = endDate.get(Calendar.YEAR)

        val monthName = if(TraverseeApplication.LANGUAGE == "id") monthsId[monthOfYear] else months[monthOfYear]

        return "$dayOfMonth $monthName $yearOfDate"
    }
}