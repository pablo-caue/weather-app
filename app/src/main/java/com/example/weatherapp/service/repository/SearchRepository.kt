package com.example.weatherapp.service.repository

import com.example.weatherapp.service.constants.WeatherConstants
import com.example.weatherapp.service.listener.APIListener
import com.example.weatherapp.service.model.AccuCityModel
import com.example.weatherapp.service.model.CityModel
import com.example.weatherapp.service.repository.remote.RetrofitClient
import com.example.weatherapp.service.repository.remote.OpenWeatherService
import com.example.weatherapp.service.repository.remote.AccuWeatherService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchRepository {

    private val remoteOpen = RetrofitClient.getServiceSearch(OpenWeatherService::class.java)
    private val remoteAccu = RetrofitClient.getServiceWeather(AccuWeatherService::class.java)

    private val keyOpen = WeatherConstants.APISearch.KEY
    private val limitOpen = WeatherConstants.APISearch.LIMIT


    fun searchByName(city: String, listener: APIListener<List<CityModel>>) {

        val call = remoteOpen.searchByName(city, limitOpen, keyOpen)
        call.enqueue(object : Callback<List<CityModel>> {
            override fun onResponse(call: Call<List<CityModel>>, r: Response<List<CityModel>>) {
                if (r.code() == WeatherConstants.HTTP.SUCCESS) {
                    r.body()?.let { listener.onSuccess(it) }
                } else {
                    listener.onFailure(r.message())
                }
            }
            override fun onFailure(call: Call<List<CityModel>>, t: Throwable) {
                val s = ""
            }

        })
    }

    fun getKeyByPosition(query: String, apiKey: String, listener: APIListener<AccuCityModel>){
        val call = remoteAccu.getKeyByPosition(apiKey, query)
        call.enqueue(object : Callback<AccuCityModel>{
            override fun onResponse(call: Call<AccuCityModel>, r: Response<AccuCityModel>) {
                if (r.code() == WeatherConstants.HTTP.SUCCESS){
                    r.body()?.let { listener.onSuccess(it) }
                }else{
                    listener.onFailure(r.message())
                }
            }

            override fun onFailure(call: Call<AccuCityModel>, t: Throwable) {
                val s = ""
            }

        })
    }
}