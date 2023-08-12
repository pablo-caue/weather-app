package com.example.weatherapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp.service.constants.WeatherConstants
import com.example.weatherapp.service.listener.APIListener
import com.example.weatherapp.service.model.HourlyModel
import com.example.weatherapp.service.repository.NextDaysRepository
import com.example.weatherapp.service.repository.SharedPreferences
import com.google.gson.Gson

class NextDaysViewModel(application: Application) : AndroidViewModel(application) {

    fun <T> fromJsonToObject(json: String, clazz: Class<T>): T {
        return Gson().fromJson(json, clazz)
    }

}