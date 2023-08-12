package com.example.weatherapp.service.constants

class WeatherConstants private constructor() {

    object APIWeather {
        const val KEY_1 = "lkDtouBQJJ6izHvSVDe2KaghS498vLs4"
        const val KEY_2 = "5icfUGAM6D6nLAi6D0duYOQAE4CYFpAW"
        const val KEY_3 = "lkDtouBQJJ"
        const val BASE_URL = "http://dataservice.accuweather.com/"
    }

    object APISearch {
        const val KEY = "4c92203fbc81edf4f0d5195d180a3f77"
        const val BASE_URL = "http://api.openweathermap.org/geo/1.0/"
        const val LIMIT = 5
    }

    object HTTP {
        const val ERROR_KEY = "401"
        const val ERROR_EXCEEDED = "503"
        const val SUCCESS = 200
    }

    object SHARED {
        const val CITY = "city"
        const val KEY = "key"
    }

    object EXTRA{
        const val LIST_OF_DAYS = "listOfDays"
        const val API_KEY = "apiKey"
    }
    object TIME{
        const val HOUR_OF_NIGHT = 6
    }

}