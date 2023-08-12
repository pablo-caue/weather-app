package com.example.weatherapp.service.repository

import android.content.Context
import com.example.weatherapp.service.constants.WeatherConstants
import com.google.gson.Gson

class SharedPreferences(context: Context) {
    private val sharedPreferences = context.getSharedPreferences("cityShared", Context.MODE_PRIVATE)

    fun store(key: String, value: String){
        sharedPreferences.edit().putString(key, value).apply()
    }

    fun get(key: String): String {
        return sharedPreferences.getString(key, "") ?: ""
    }

}