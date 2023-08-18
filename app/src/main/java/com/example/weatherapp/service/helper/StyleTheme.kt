package com.example.weatherapp.service.helper

import android.content.Context
import android.content.res.Configuration
import android.graphics.Bitmap.Config

class StyleTheme {
    fun isDarkModeEnabled(context: Context): Boolean {
        val currentNightMode = context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_YES
        return currentNightMode == Configuration.UI_MODE_NIGHT_YES
    }
}
