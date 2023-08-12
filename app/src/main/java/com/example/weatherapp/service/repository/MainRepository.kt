package com.example.weatherapp.service.repository

import android.content.Context
import com.example.weatherapp.R
import com.example.weatherapp.service.constants.WeatherConstants
import com.example.weatherapp.service.listener.APIListener
import com.example.weatherapp.service.model.HourlyModel
import com.example.weatherapp.service.model.OneHourModel
import com.example.weatherapp.service.model.TwelveHoursModel
import com.example.weatherapp.service.repository.remote.AccuWeatherService
import com.example.weatherapp.service.repository.remote.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainRepository(val context: Context) {

    private val remoteWeather = RetrofitClient.getServiceWeather(AccuWeatherService::class.java)

    fun listOneHour(city: String, key: String, lang: String, details: Boolean, metric: Boolean, listener: APIListener<List<OneHourModel>>) {
        val call = remoteWeather.listOneHour(city, key, lang, details, metric)
        call.enqueue(object : Callback<List<OneHourModel>> {
            override fun onResponse(call: Call<List<OneHourModel>>, r: Response<List<OneHourModel>>) {
                if (r.code() == WeatherConstants.HTTP.SUCCESS) {
                    r.body()?.let { listener.onSuccess(it) }
                } else {
                    listener.onFailure(failResponse(r.toString()))
                }
            }

            override fun onFailure(call: Call<List<OneHourModel>>, t: Throwable) {
                listener.onFailure(context.getString(R.string.ERROR_UNEXPECTED))
            }

        })
    }

    fun listTwelveHours(city: String, key: String, lang: String, details: Boolean, metric: Boolean, listener: APIListener<List<TwelveHoursModel>>) {
        val call = remoteWeather.listTwelveHours(city, key, lang, details, metric)
        call.enqueue(object : Callback<List<TwelveHoursModel>>{
            override fun onResponse(call: Call<List<TwelveHoursModel>>, response: Response<List<TwelveHoursModel>>) {
                if (response.code() == WeatherConstants.HTTP.SUCCESS){
                    response.body()?.let { listener.onSuccess(it) }
                }else{
                    listener.onFailure(response.message())
                }
            }

            override fun onFailure(call: Call<List<TwelveHoursModel>>, t: Throwable) {
                val s = ""
            }

        })
    }

    fun listDays(city: String, key: String, lang: String, details: Boolean, metric: Boolean, listener: APIListener<HourlyModel>) {
        val call = remoteWeather.listDays(city, key, lang, metric, details)
        call.enqueue(object : Callback<HourlyModel> {
            override fun onResponse(call: Call<HourlyModel>, r: Response<HourlyModel>) {
                if (r.code() == WeatherConstants.HTTP.SUCCESS) {
                    r.body()?.let { listener.onSuccess(it) }
                } else {
                    listener.onFailure(r.message())
                }
            }

            override fun onFailure(call: Call<HourlyModel>, t: Throwable) {
                val s = ""
            }

        })
    }

    fun failResponse(str: String): String {
        val valuesList = str.split(", ")
            .flatMap { it.split(":") }
            .map { it.trim() }

        return valuesList[1]
    }
}