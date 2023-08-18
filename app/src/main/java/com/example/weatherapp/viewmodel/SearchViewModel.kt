package com.example.weatherapp.viewmodel

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.content.Context
import android.location.Location
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.weatherapp.service.constants.WeatherConstants
import com.example.weatherapp.service.helper.StyleTheme
import com.example.weatherapp.service.listener.APIListener
import com.example.weatherapp.service.model.AccuCityModel
import com.example.weatherapp.service.model.CityModel
import com.example.weatherapp.service.repository.SearchRepository
import com.example.weatherapp.service.repository.SharedPreferences
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions

class SearchViewModel(private val application: Application) : AndroidViewModel(application) {

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private val _listSearch = MutableLiveData<List<CityModel>>()
    val listSearch: LiveData<List<CityModel>> = _listSearch

    private val _listCities = MutableLiveData<AccuCityModel>()
    val listCities: LiveData<AccuCityModel> = _listCities

    private val _isSaved = MutableLiveData<Boolean>()
    val isSaved : LiveData<Boolean> = _isSaved

    private val _userLocale = MutableLiveData<Location>()
    val userLocale: LiveData<Location> = _userLocale

    private val _isDarkThemeEnabled = MutableLiveData<Boolean>()
    val isDarkThemeEnabled : LiveData<Boolean> = _isDarkThemeEnabled

    private val sharedPreferences = SharedPreferences(application.applicationContext)
    private val searchRepository = SearchRepository()
    private val styleTheme = StyleTheme()

    fun searchByName(city: String) {
        searchRepository.searchByName(city, object : APIListener<List<CityModel>> {
            override fun onSuccess(result: List<CityModel>) {
                _listSearch.value = result
            }
            override fun onFailure(message: String) {
                val s = ""
            }

        })
    }

    fun getKeyByPosition(latitude: String, longitude: String, apiKey: String, city: String) {

        val query = "${latitude},${longitude}"

        searchRepository.getKeyByPosition(query, apiKey, object : APIListener<AccuCityModel> {
            override fun onSuccess(result: AccuCityModel) {
                sharedPreferences.store(WeatherConstants.SHARED.KEY, result.key)
                sharedPreferences.store(WeatherConstants.SHARED.CITY, city)
                _isSaved.value = true
            }

            override fun onFailure(message: String) {
                _isSaved.value = false
            }
        })
    }

    fun getKeyByLocale(latitude: String, longitude: String, apiKey: String, isClicked: Boolean) {

        val query = "${latitude},${longitude}"

        searchRepository.getKeyByPosition(query, apiKey, object : APIListener<AccuCityModel> {
            override fun onSuccess(result: AccuCityModel) {
                if (isClicked){
                    sharedPreferences.store(WeatherConstants.SHARED.KEY, result.key)
                    sharedPreferences.store(WeatherConstants.SHARED.CITY, result.city)
                }
                _listCities.value = result
            }

            override fun onFailure(message: String) {
                val s = ""
            }
        })
    }
    
    
    fun getPermissionLocale(activity: Activity) {
        EasyPermissions.requestPermissions(activity,
            "Permissão necessaria para prosseguir",
            WeatherConstants.PERMISSION.LOCATION_PERMISSION_REQUEST_CODE,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
    }

    @SuppressLint("MissingPermission")
    @AfterPermissionGranted(WeatherConstants.PERMISSION.LOCATION_PERMISSION_REQUEST_CODE)
    fun fetchLocation(activity: Activity) {
        if (EasyPermissions.hasPermissions(activity.applicationContext, Manifest.permission.ACCESS_FINE_LOCATION)) {
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity)
            fusedLocationClient.lastLocation.addOnSuccessListener {
                if (it != null) {
                    _userLocale.value = it
                } else {
                    Toast.makeText(activity.applicationContext, "ERRO", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    fun checkThemeMode(context: Context){
        _isDarkThemeEnabled.value = styleTheme.isDarkModeEnabled(context)
    }

}


