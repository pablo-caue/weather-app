package com.example.weatherapp.service.model

import com.google.gson.annotations.SerializedName

class CityModel {

    @SerializedName("name")
    var name: String = ""

    @SerializedName("lat")
    var latitude: String = ""

    @SerializedName("lon")
    var longitude: String = ""

    @SerializedName("country")
    var country: String = ""

    @SerializedName("state")
    var state: String = ""
}

