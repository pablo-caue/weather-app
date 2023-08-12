package com.example.weatherapp.service.repository

import android.location.LocationListener
import com.example.weatherapp.service.constants.WeatherConstants
import com.example.weatherapp.service.listener.APIListener
import com.example.weatherapp.service.model.HourlyModel
import com.example.weatherapp.service.repository.remote.AccuWeatherService
import com.example.weatherapp.service.repository.remote.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NextDaysRepository {

    private val remoteAccu = RetrofitClient.getServiceWeather(AccuWeatherService::class.java)

    fun listFiveDays(key: String, keyApi: String, lang: String, details: Boolean, metric: Boolean, listener: APIListener<HourlyModel>){
        val call = remoteAccu.listDays(key, keyApi, lang, details, metric)
        call.enqueue(object : Callback<HourlyModel>{
            override fun onResponse(call: Call<HourlyModel>, response: Response<HourlyModel>) {
                if (response.code() == WeatherConstants.HTTP.SUCCESS){
                    response.body()?.let { listener.onSuccess(it) }
                }else{
                    listener.onFailure(response.message())
                }
            }

            override fun onFailure(call: Call<HourlyModel>, t: Throwable) {
                val s = ""
            }

        })
    }
}