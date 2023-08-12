package com.example.weatherapp.service.model

import com.google.gson.annotations.SerializedName

class HourlyModel {
    @SerializedName("DailyForecasts")
    lateinit var dailyForecasts: List<DailyForecasts>
}

class DailyForecasts {

    @SerializedName("Date")
    lateinit var date: String

    @SerializedName("Temperature")
    lateinit var temperature: Temp

    @SerializedName("Day")
    lateinit var day: Day

    @SerializedName("Night")
    lateinit var night: Night
}

class Temp {
    @SerializedName("Minimum")
    lateinit var minimum: Minimum

    @SerializedName("Maximum")
    lateinit var maximum: Maximum
}

class Day {
    @SerializedName("Icon")
    lateinit var iconWeather: String

    @SerializedName("IconPhrase")
    lateinit var phraseWeather: String

    @SerializedName("RainProbability")
    lateinit var rainProbability: String

    @SerializedName("Wind")
    lateinit var wind: Wind

}

class Night {
    @SerializedName("RainProbability")
    lateinit var rainProbability: String
}


class Minimum {
    @SerializedName("Value")
    var value: Double = 0.0

    @SerializedName("Unit")
    lateinit var unit: String
}


class Maximum {
    @SerializedName("Value")
    var value: Double = 0.0

    @SerializedName("Unit")
    lateinit var unit: String
}





