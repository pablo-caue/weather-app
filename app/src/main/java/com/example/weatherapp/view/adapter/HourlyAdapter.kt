package com.example.weatherapp.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.databinding.ViewholderHourlyBinding
import com.example.weatherapp.service.listener.CityListener
import com.example.weatherapp.service.model.CityModel
import com.example.weatherapp.service.model.HourlyModel
import com.example.weatherapp.service.model.TwelveHoursModel
import com.example.weatherapp.view.viewholder.HourlyViewHolder

class HourlyAdapter : RecyclerView.Adapter<HourlyViewHolder>() {

    private var listHourly: List<TwelveHoursModel> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourlyViewHolder {
        val inflater = ViewholderHourlyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HourlyViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: HourlyViewHolder, position: Int) {
        holder.bind(listHourly[position])
    }

    override fun getItemCount(): Int {
        return listHourly.count()
    }

    fun updateHourly(list: List<TwelveHoursModel>) {
        listHourly = list
        notifyDataSetChanged()
    }

}