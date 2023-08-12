package com.example.weatherapp.view.viewholder

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.databinding.ViewholderTomorrowBinding
import com.example.weatherapp.service.constants.WeatherConstants
import com.example.weatherapp.service.model.DailyForecasts
import java.text.DateFormatSymbols
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class TomorrowViewHolder(private val itemBinding: ViewholderTomorrowBinding) :
    RecyclerView.ViewHolder(itemBinding.root) {
    private val dayOfWeek = itemBinding.textDay
    private val imgWeather = itemBinding.imageWeather
    private val weather = itemBinding.textWeather
    private val max = itemBinding.textTempMax
    private val min = itemBinding.textTempMin

    @RequiresApi(Build.VERSION_CODES.O)
    fun bind(tomorrowModel: DailyForecasts) {

        dayOfWeek.text = handleDayOfWeek(tomorrowModel.date)

        handleImageWeather(tomorrowModel.day.iconWeather)

        weather.text = handlePhraseWeather(tomorrowModel.day.phraseWeather)

        val tempMax = tomorrowModel.temperature.maximum.value.toInt().toString()
        val tempMin = tomorrowModel.temperature.minimum.value.toInt().toString()
        val unit = tomorrowModel.temperature.maximum.unit
        max.text = tempMax + "º" + unit

        min.text = tempMin + "º" + unit


    }



    @RequiresApi(Build.VERSION_CODES.O)
    private fun handleDayOfWeek(date: String): String {

        val dateTimeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME
        val apiDateTime = LocalDateTime.parse(date, dateTimeFormatter)
        val dataDayOfWeek = apiDateTime.dayOfWeek

        val symbols = DateFormatSymbols.getInstance(Locale.getDefault())
        val dayOfWeekNames = symbols.weekdays

        val dayOfWeekIndex = dataDayOfWeek.value % 7 + 1

        val dayOfWeek = dayOfWeekNames[dayOfWeekIndex]
        return dayOfWeek.substring(0, 3)
    }

    private fun handleImageWeather(icon: String) {
        when (icon) {
            "1", "2", "30" -> imgWeather.setImageResource(R.drawable.sun)

            "3", "4", "20" -> imgWeather.setImageResource(R.drawable.cloudy_sunny)

            "5", "6", "21" -> imgWeather.setImageResource(R.drawable.cloudy)

            "7", "8", "11", "19", "31", "32", "33", "34", "35", "36", "37", "38" -> imgWeather.setImageResource(
                R.drawable.cloudy_3
            )

            "12", "13", "14", "18", "25", "26", "29", "39", "40", "43" -> imgWeather.setImageResource(
                R.drawable.rainy
            )

            "15", "16", "17", "41", "42" -> imgWeather.setImageResource(R.drawable.storm)

            "22", "23", "24", "44" -> imgWeather.setImageResource(R.drawable.snowy)


        }
    }

    private fun handlePhraseWeather(phrase: String): String {
        val phraseWeatherLowercase = phrase.lowercase()
        val phraseWeatherWithout = phraseWeatherLowercase.replace("predominantemente", "").trim()
        return phraseWeatherWithout.replaceFirstChar { it.uppercase() }
    }
}