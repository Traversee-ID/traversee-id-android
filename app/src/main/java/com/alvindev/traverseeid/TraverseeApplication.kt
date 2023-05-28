package com.alvindev.traverseeid

import android.app.Application
import android.content.Context
import com.alvindev.traverseeid.core.util.LocaleUtil
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class TraverseeApplication : Application(){
    companion object {
        var LANGUAGE = ""
    }
}