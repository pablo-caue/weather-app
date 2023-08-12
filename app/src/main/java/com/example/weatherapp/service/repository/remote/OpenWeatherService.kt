package com.example.weatherapp.service.repository.remote

import com.example.weatherapp.service.model.CityModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherService {
    @GET("direct?")
    fun search(
            @Query("q") city: String,
            @Query("limit") limit: Int,
            @Query("appid") key: String
    ): Call<List<CityModel>>


}