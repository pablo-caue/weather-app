package com.example.weatherapp.service.listener

interface APIListener<T> {
    fun onSuccess(result: T)

    fun onFailure(message: String)
}