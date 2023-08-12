package com.example.weatherapp.service.listener

interface CityListener {
    fun onClick(city: String, latitude: String, longitude: String)
}