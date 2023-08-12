package com.example.weatherapp.viewmodel

import android.Manifest
import android.annotation.SuppressLint
import android.app.Application
import android.location.Location
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.weatherapp.service.constants.WeatherConstants
import com.example.weatherapp.service.helper.PermissionHelper
import com.example.weatherapp.service.listener.APIListener
import com.example.weatherapp.service.model.CityKey
import com.example.weatherapp.service.model.CityModel
import com.example.weatherapp.service.repository.SearchRepository
import com.example.weatherapp.service.repository.SharedPreferences
import com.google.android.gms.location.FusedLocationProviderClient

class SearchViewModel(private val application: Application) : AndroidViewModel(application) {

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    private val _listSearch = MutableLiveData<List<CityModel>>()
    val listSearch: LiveData<List<CityModel>> = _listSearch

    private val _isSaved = MutableLiveData<Boolean>()
    val isSaved: LiveData<Boolean> = _isSaved

    private val _test = MutableLiveData<Location>()
    val test: LiveData<Location> = _test

    private val sharedPreferences = SharedPreferences(application.applicationContext)
    private val searchRepository = SearchRepository()

    fun search(city: String) {
        searchRepository.search(city, object : APIListener<List<CityModel>> {
            override fun onSuccess(result: List<CityModel>) {
                _listSearch.value = result
            }

            override fun onFailure(message: String) {

            }

        })
    }

    fun getKeyByPosition(city: String,latitude: String, longitude: String, key: String) {

        val query = "${latitude},${longitude}"

        searchRepository.getKeyByPosition(query, key, object : APIListener<CityKey> {
            override fun onSuccess(result: CityKey) {
                sharedPreferences.store(WeatherConstants.SHARED.KEY, result.key)
                sharedPreferences.store(WeatherConstants.SHARED.CITY, city)
                _isSaved.value = true
            }

            override fun onFailure(message: String) {
                _isSaved.value = false
            }

        })


    }

    @SuppressLint("MissingPermission")
    fun getLocale() {
        val permission = Manifest.permission.ACCESS_FINE_LOCATION
        if (PermissionHelper.isPermissionEnabled(application, permission)) {
            fusedLocationProviderClient.lastLocation.addOnSuccessListener {
                _test.value = it
            }
        }


    }
}
