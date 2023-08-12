package com.example.weatherapp.view.viewholder

import android.annotation.SuppressLint
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.databinding.ViewholderCitiesBinding
import com.example.weatherapp.service.listener.CityListener
import com.example.weatherapp.service.model.CityModel

class CityViewHolder(private val itemBinding: ViewholderCitiesBinding, private val listener: CityListener) : RecyclerView.ViewHolder(itemBinding.root) {

    private val city = itemBinding.textCity
    private val state_country = itemBinding.textStateCountry

    fun bind(cityModel: CityModel){
        city.text = cityModel.name
        state_country.text = "${cityModel.state}, ${cityModel.country}"

        itemBinding.card.setOnClickListener {
            listener.onClick(cityModel.name, cityModel.latitude, cityModel.longitude,)
        }

    }

}
