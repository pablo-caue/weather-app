package com.example.weatherapp.viewmodel

import android.app.Application
import android.content.Context
import android.content.res.Configuration
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp.service.constants.WeatherConstants
import com.example.weatherapp.service.listener.APIListener
import com.example.weatherapp.service.model.*
import com.example.weatherapp.service.repository.MainRepository
import com.example.weatherapp.service.repository.SharedPreferences
import com.google.gson.Gson

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private var _oneHour = MutableLiveData<OneHourModel>()
    var oneHour : LiveData<OneHourModel> = _oneHour

    private var _twelveHours = MutableLiveData<List<TwelveHoursModel>>()
    var twelveHours : LiveData<List<TwelveHoursModel>> = _twelveHours

    private var _listDay = MutableLiveData<HourlyModel>()
    var listDay : LiveData<HourlyModel> = _listDay

    private var _error = MutableLiveData<String>()
    var error : LiveData<String> = _error


    private var _isDarkModeEnabled = MutableLiveData<Boolean>()
    var isDarkModeEnabled : LiveData<Boolean> = _isDarkModeEnabled

    private val mainRepository = MainRepository(application)

    private val sharedPreferences = SharedPreferences(application.applicationContext)

    private lateinit var key: String

    // Lista Onehour API
    fun listOneHour(city: String, key: String, lang: String,details: Boolean, metric: Boolean){
        mainRepository.listOneHour(city, key, lang,details, metric, object : APIListener<List<OneHourModel>>{
            override fun onSuccess(result: List<OneHourModel>) {
                _oneHour.value = result.get(0)
            }

            override fun onFailure(message: String) {
                _error.value = message
            }

        })

        }

    // Lista FiveDays API
    fun listDay(city: String, key: String, lang: String,details:Boolean, metric: Boolean){
      mainRepository.listDays(city, key, lang,details , metric, object : APIListener<HourlyModel>{
          override fun onSuccess(result: HourlyModel) {
              _listDay.value = result
          }

          override fun onFailure(message: String) {
              val s = ""
          }

      })
    }

    // Lista TwelveHours API
    fun listTwelveHours(city: String, key: String, lang: String,details:Boolean, metric: Boolean){
        mainRepository.listTwelveHours(city, key, lang, details, metric, object : APIListener<List<TwelveHoursModel>>{
            override fun onSuccess(result: List<TwelveHoursModel>) {
                _twelveHours.value = result
            }

            override fun onFailure(message: String) {
                val s = ""
            }

        })
    }

    // Converte objeto para string
    fun <T> fromObjectToJson(clazz: T): String {
        val gson = Gson()
        return gson.toJson(clazz)
    }

    // Pega nome cidade
    fun getCity(): String {
        return sharedPreferences.get(WeatherConstants.SHARED.CITY)
    }

    // Pega chave cidade
    fun geyKeyCity(): String{
        return sharedPreferences.get(WeatherConstants.SHARED.KEY)
    }

    // Pega chave API
    fun getKeyAPI(): String {
        return key
    }

    // Atribui chave API
    fun setKeyAPI(newKey: String){
        key = newKey
    }

    fun checkStyleMode(context: Context){
        val currentNightMode = context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_YES
        _isDarkModeEnabled.value = currentNightMode == Configuration.UI_MODE_NIGHT_YES
    }


}
