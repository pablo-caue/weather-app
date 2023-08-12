package com.example.weatherapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapp.R
import com.example.weatherapp.databinding.ActivityNextDaysBinding
import com.example.weatherapp.service.constants.WeatherConstants
import com.example.weatherapp.service.model.DailyForecasts
import com.example.weatherapp.service.model.HourlyModel
import com.example.weatherapp.view.adapter.TomorrowAdapter
import com.example.weatherapp.viewmodel.MainViewModel
import com.example.weatherapp.viewmodel.NextDaysViewModel
import com.google.gson.Gson

class NextDaysActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityNextDaysBinding
    private lateinit var adapter: TomorrowAdapter
    private lateinit var viewModel: NextDaysViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityNextDaysBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this)[NextDaysViewModel::class.java]
        setContentView(binding.root)

        supportActionBar?.hide()

        initRecyclerView()

        binding.imageBack.setOnClickListener(this)

        // pegar json passado pela intent
        val hourlyModelJson = intent.getStringExtra(WeatherConstants.EXTRA.LIST_OF_DAYS)

        // converter json para objeto
        val hourlyModel = viewModel.fromJsonToObject(hourlyModelJson!!, HourlyModel::class.java)

        // colar dados do objeto para os elementos de interface de amanha
        handleDataTomorrow(hourlyModel.dailyForecasts[1])


        val newList = hourlyModel.dailyForecasts.subList(2, hourlyModel.dailyForecasts.size)
        adapter.updateTomorrow(newList)
    }

    override fun onClick(view: View) {
        when (view.id){
            R.id.image_back ->{
                finish()
            }
        }
    }

    private fun handleDataTomorrow(data: DailyForecasts) {
        when (data.day.iconWeather) {
            "1", "2", "30" -> binding.imageWeather.setImageResource(R.drawable.sun_p)

            "3", "4", "20" -> binding.imageWeather.setImageResource(R.drawable.cloudy_sunny_p)

            "5", "6", "21" -> binding.imageWeather.setImageResource(R.drawable.cloudy_p)

            "7", "8", "11", "19", "31", "32", "33", "34", "35", "36", "37", "38" -> binding.imageWeather.setImageResource(
                R.drawable.cloudy_3_p
            )

            "12", "13", "14", "18", "25", "26", "29", "39", "40", "43" -> binding.imageWeather.setImageResource(
                R.drawable.rainy_p
            )

            "15", "16", "17", "41", "42" -> binding.imageWeather.setImageResource(R.drawable.storm_p)

            "22", "23", "24", "44" -> binding.imageWeather.setImageResource(R.drawable.snowy_p)


        }

        val temp = data.temperature.maximum.value.toInt().toString()
        val unit = data.temperature.maximum.unit
        binding.textTemp.text = temp + "º" + unit


        binding.textWeather.text = handlePhraseWeather(data.day.phraseWeather)



        val rainChance = data.day.rainProbability
        binding.textDataChanceRain.text = "$rainChance%"

        val windSpeed = data.day.wind.speed.value.toInt().toString()
        val unitSpeed = data.day.wind.speed.unit
        binding.textDataWindSpeed.text = windSpeed + "" + unitSpeed
    }

    private fun initRecyclerView() {
        adapter = TomorrowAdapter()

        val vertical = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = vertical
        binding.recyclerView.adapter = adapter
    }

    private fun handlePhraseWeather(phrase: String): String {
        val phraseWeatherLowercase = phrase.lowercase()
        val phraseWeatherWithout = phraseWeatherLowercase.replace("predominantemente", "").trim()
        return phraseWeatherWithout.replaceFirstChar { it.uppercase() }
    }
}