package com.example.weatherapp.service.repository

import com.example.weatherapp.service.constants.WeatherConstants
import com.example.weatherapp.service.listener.APIListener
import com.example.weatherapp.service.model.CityKey
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


    fun search(city: String, listener: APIListener<List<CityModel>>) {

        val key = WeatherConstants.APISearch.KEY
        val limit = WeatherConstants.APISearch.LIMIT

        val call = remoteOpen.search(city, limit, key)
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

    fun getKeyByPosition(query: String, key: String, listener: APIListener<CityKey>){
        val call = remoteAccu.getKeyByPosition(key, query)
        call.enqueue(object : Callback<CityKey>{
            override fun onResponse(call: Call<CityKey>, r: Response<CityKey>) {
                if (r.code() == WeatherConstants.HTTP.SUCCESS){
                    r.body()?.let { listener.onSuccess(it) }
                }else{
                    listener.onFailure(r.message())
                }
            }

            override fun onFailure(call: Call<CityKey>, t: Throwable) {
                val s = ""
            }

        })
    }
}