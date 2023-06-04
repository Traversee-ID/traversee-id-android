package com.alvindev.traverseeid.feature_tourism.util

import android.content.Context
import android.content.Intent
import android.net.Uri

object TourismUtil {
    fun sendViaWhatsApp(phoneNumber: String, message: String, context: Context){
        val url = "https://api.whatsapp.com/send?phone=$phoneNumber&text=$message"
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        context.startActivity(intent)
    }
}