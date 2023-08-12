package com.example.weatherapp.viewmodel

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.location.Location
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.weatherapp.service.constants.WeatherConstants
import com.example.weatherapp.service.listener.APIListener
import com.example.weatherapp.service.model.CityKey
import com.example.weatherapp.service.model.CityModel
import com.example.weatherapp.service.repository.SearchRepository
import com.example.weatherapp.service.repository.SharedPreferences
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions
import pub.devrel.easypermissions.PermissionRequest

class SearchViewModel(private val application: Application) : AndroidViewModel(application) {

    private lateinit var fusedLocationClient: FusedLocationProviderClient

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

    @AfterPermissionGranted(123)
    fun getPermissionLocale(activity: Activity) {

        if (EasyPermissions.hasPermissions(activity.applicationContext, Manifest.permission.ACCESS_FINE_LOCATION)){
            fetchLocation(activity)
        }else{
            EasyPermissions.requestPermissions(
                activity,
                "TESTE",
                123,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        }
    }

    @SuppressLint("MissingPermission")
    @AfterPermissionGranted(123)
    private fun fetchLocation(activity: Activity) {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity)
        fusedLocationClient.lastLocation.addOnSuccessListener {
            if (it != null){
                val latitude = it.latitude
                val longitude = it.longitude
                val str = "$latitude, $longitude"

                Toast.makeText(activity.applicationContext, str, Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(activity.applicationContext, "ERRO", Toast.LENGTH_SHORT).show()
            }
        }
    }

}


