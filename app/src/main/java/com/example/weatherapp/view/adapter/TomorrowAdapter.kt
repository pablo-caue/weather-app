package com.example.weatherapp.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.databinding.ViewholderTomorrowBinding
import com.example.weatherapp.service.model.DailyForecasts
import com.example.weatherapp.service.model.HourlyModel
import com.example.weatherapp.service.model.TomorrowModel
import com.example.weatherapp.view.viewholder.TomorrowViewHolder

class TomorrowAdapter : RecyclerView.Adapter<TomorrowViewHolder>() {

    private var listTomorrow: List<DailyForecasts> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TomorrowViewHolder {
        return TomorrowViewHolder(
            ViewholderTomorrowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: TomorrowViewHolder, position: Int) {
        holder.bind(listTomorrow[position])
    }

    override fun getItemCount(): Int {
        return listTomorrow.count()
    }

    fun updateTomorrow(tomorrow: List<DailyForecasts>) {
        listTomorrow = tomorrow
        notifyDataSetChanged()
    }
}
