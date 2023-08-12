package com.example.weatherapp.service.repository.remote

import com.example.weatherapp.service.constants.WeatherConstants
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {
    companion object {

        private lateinit var instanceWeather: Retrofit
        private lateinit var instanceSearch: Retrofit

        private fun getRetrofitWeatherInstance(): Retrofit {
            val httpClient = OkHttpClient.Builder()
            if (!::instanceWeather.isInitialized) {
                synchronized(RetrofitClient::class){
                    instanceWeather = Retrofit.Builder()
                        .baseUrl(WeatherConstants.APIWeather.BASE_URL)
                        .client(httpClient.build())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                }
            }
            return instanceWeather
        }

        private fun getRetrofitSearchInstance(): Retrofit {
            val httpClient = OkHttpClient.Builder()
            if (!::instanceSearch.isInitialized) {
                synchronized(RetrofitClient::class){
                    instanceSearch = Retrofit.Builder()
                        .baseUrl(WeatherConstants.APISearch.BASE_URL)
                        .client(httpClient.build())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                }
            }
            return instanceSearch
        }


        fun <T> getServiceWeather(serviceClass: Class <T>): T {
            return getRetrofitWeatherInstance().create(serviceClass)
        }

        fun <T> getServiceSearch(serviceClass: Class <T>): T {
            return getRetrofitSearchInstance().create(serviceClass)
        }
    }

}