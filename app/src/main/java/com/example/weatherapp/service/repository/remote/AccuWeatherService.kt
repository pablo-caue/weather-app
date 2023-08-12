package com.example.weatherapp.service.repository.remote

import com.example.weatherapp.service.model.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface AccuWeatherService {

    @GET("forecasts/v1/hourly/1hour/{city}")
    fun listOneHour(
        @Path("city") city: String,
        @Query("apikey") key: String,
        @Query("language") lang: String,
        @Query("details") details: Boolean,
        @Query("metric") metric: Boolean,
    ): Call<List<OneHourModel>>

    @GET("forecasts/v1/hourly/12hour/{city}")
    fun listTwelveHours(
        @Path("city") city: String,
        @Query("apikey") key: String,
        @Query("langueage") lang: String,
        @Query("details") details: Boolean,
        @Query("metric") metric: Boolean
    ):Call<List<TwelveHoursModel>>

    //http://dataservice.accuweather.com/forecasts/v1/hourly/12hour/2627449?&language=pt-br&details=true&metric=true

    @GET("forecasts/v1/daily/5day/{city}")
    fun listDays(
        @Path("city") city: String,
        @Query("apikey") key: String,
        @Query("language") lang: String,
        @Query("details") details: Boolean,
        @Query("metric") metric: Boolean,
    ): Call<HourlyModel>



    @GET("locations/v1/cities/geoposition/search")
    fun getKeyByPosition(
        @Query("apikey") apiKey: String,
        @Query("q") lat_and_long: String,
    ): Call<CityKey>


    //http://dataservice.accuweather.com/locations/v1/cities/geoposition/search?apikey=5icfUGAM6D6nLAi6D0duYOQAE4CYFpAW&q=48.2043985%2C15.6229118

}
