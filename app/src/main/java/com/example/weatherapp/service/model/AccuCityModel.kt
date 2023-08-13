package com.example.weatherapp.service.model

import com.google.gson.annotations.SerializedName

class AccuCityModel {
    @SerializedName("Key")
    var key : String = ""

    @SerializedName("LocalizedName")
    var city: String = ""

    @SerializedName("Country")
    lateinit var state: State

    @SerializedName("AdministrativeArea")
    lateinit var country: Country
}

class Country{
    @SerializedName("LocalizedName")
    var name: String = ""
}

class State{
    @SerializedName("LocalizedName")
    var name: String = ""
}