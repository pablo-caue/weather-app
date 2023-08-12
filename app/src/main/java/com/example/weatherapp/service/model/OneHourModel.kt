package com.example.weatherapp.service.model

import com.google.gson.annotations.SerializedName


class OneHourModel {
    @SerializedName("WeatherIcon")
    lateinit var weatherIcon: String

    @SerializedName("IconPhrase")
    lateinit var iconPhrase: String

    @SerializedName("Temperature")
    lateinit var temperature: Temperature

    @SerializedName("Wind")
    lateinit var wind: Wind

    @SerializedName("RelativeHumidity")
    lateinit var humidity: String
}


class Temperature {

    @SerializedName("Value")
    var value: Double = 0.0
    @SerializedName("Unit")
    lateinit var unit: String
}

class Wind {
    @SerializedName("Speed")
    lateinit var speed: Speed
}

class Speed{
    @SerializedName("Value")
    var value: Double = 0.0

    @SerializedName("Unit")
    var unit: String = ""
}
