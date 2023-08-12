package com.example.weatherapp.view.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.databinding.ViewholderHourlyBinding
import com.example.weatherapp.service.model.HourlyModel
import com.example.weatherapp.service.model.TwelveHoursModel
import java.text.SimpleDateFormat
import java.util.*

class HourlyViewHolder(private val itemBinding: ViewholderHourlyBinding): RecyclerView.ViewHolder(itemBinding.root) {
    private val time = itemBinding.textTime
    private val temp = itemBinding.textTemp
    private val picPath = itemBinding.imageWeather

    fun bind(twelveHoursModel: TwelveHoursModel){

        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX", Locale.getDefault())
        val outputFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        val date = inputFormat.parse(twelveHoursModel.time)
        val formatedDate = outputFormat.format(date)

        time.text = formatedDate

        val temperature = twelveHoursModel.temperature.value.toInt().toString()
        val unit = twelveHoursModel.temperature.unit
        val tempData = temperature + "º" + unit
        temp.text = tempData

        when (twelveHoursModel.weatherIcon) {
            "1", "2", "30" -> picPath.setImageResource(R.drawable.sun)

            "3", "4", "20" -> picPath.setImageResource(R.drawable.cloudy_sunny)

            "5", "6", "21" -> picPath.setImageResource(R.drawable.cloudy)

            "7", "8", "11", "19", "31", "32", "33", "34", "35", "36", "37", "38" -> picPath.setImageResource(
                R.drawable.cloudy_3
            )

            "12", "13", "14", "18", "25", "26", "29", "39", "40", "43" -> picPath.setImageResource(
                R.drawable.rainy
            )

            "15", "16", "17", "41", "42" -> picPath.setImageResource(R.drawable.storm)

            "22", "23", "24", "44" -> picPath.setImageResource(R.drawable.snowy)


        }
    }
}