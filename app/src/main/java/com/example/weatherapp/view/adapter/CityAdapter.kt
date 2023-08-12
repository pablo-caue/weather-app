package com.example.weatherapp.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.databinding.ViewholderCitiesBinding
import com.example.weatherapp.service.listener.CityListener
import com.example.weatherapp.service.model.CityModel
import com.example.weatherapp.view.viewholder.CityViewHolder

class CityAdapter : RecyclerView.Adapter<CityViewHolder>() {

    private var listCity: List<CityModel> = listOf()
    private lateinit var listener: CityListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        val inflater =
            ViewholderCitiesBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return CityViewHolder(inflater, listener)

    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        holder.bind(listCity[position])
    }

    override fun getItemCount(): Int {
        return listCity.count()
    }

    fun updateCities(list: List<CityModel>) {
        listCity = list
        notifyDataSetChanged()
    }

    fun attachAdapter(cityListener: CityListener){
        listener = cityListener
    }
}