package com.example.weatherapp.service.model

import com.google.gson.annotations.SerializedName

class TwelveHoursModel {
    @SerializedName("DateTime")
    lateinit var time: String

    @SerializedName("WeatherIcon")
    lateinit var weatherIcon: String

    @SerializedName("Temperature")
    lateinit var temperature: Temperature
}