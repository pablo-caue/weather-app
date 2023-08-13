package com.example.weatherapp.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapp.R
import com.example.weatherapp.databinding.ActivityMainBinding
import com.example.weatherapp.service.constants.WeatherConstants
import com.example.weatherapp.service.model.*
import com.example.weatherapp.view.adapter.HourlyAdapter
import com.example.weatherapp.viewmodel.MainViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var hourlyAdapter: HourlyAdapter
    private lateinit var viewModel: MainViewModel
    private lateinit var currentLocale: Locale
    private lateinit var listOfDays: String
    private lateinit var intentSearchActivity: Intent



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        // Layout
        setContentView(binding.root)

        // Variaveis da classe
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        currentLocale = Locale.getDefault()
        listOfDays = ""


        // Esconder ActionBar
        supportActionBar?.hide()

        // Iniciar RecyclerView
        initRecyclerView()

        // Eventos
        binding.textNextDays.setOnClickListener(this)
        binding.imageSearch.setOnClickListener(this)

        // Observer
        observe()

        // Inicia o app com a chave 1
        viewModel.setKeyAPI(WeatherConstants.APIWeather.KEY_1)

        intentSearchActivity = Intent(this, SearchActivity::class.java)
        intentSearchActivity.putExtra(WeatherConstants.EXTRA.API_KEY, viewModel.getKeyAPI())
    }

    override fun onResume() {
        listWeather()
        handleOnResume()
        super.onResume()
    }

    // Eventos de click
    override fun onClick(view: View) {
        when (view.id) {
            R.id.text_next_days -> {
                val intent = Intent(applicationContext, NextDaysActivity::class.java)
                intent.putExtra(WeatherConstants.EXTRA.LIST_OF_DAYS, listOfDays)
                startActivity(intent)
            }

            R.id.image_search -> {
                startActivity(intentSearchActivity)
            }
        }
    }

    // Listar dados API
    private fun listWeather() {

        val keyCity = viewModel.geyKeyCity()
        val keyApi = viewModel.getKeyAPI()

        if (keyCity == "") startActivity(intentSearchActivity)

        val language = currentLocale.language
        val country = currentLocale.country
        val lang = "$language-$country"

        val details = true
        val metric = true

        viewModel.listOneHour(keyCity, keyApi, lang, details, metric)
        viewModel.listDay(keyCity, keyApi, lang, details, metric)
        viewModel.listTwelveHours(keyCity, keyApi, lang, details, metric)
    }

    // Observadores
    private fun observe() {

        // Erros API
        viewModel.error.observe(this) {
            val code = it.subSequence(5, 8)

            when (code) {
                // Erro limite chamadas
                WeatherConstants.HTTP.ERROR_EXCEEDED -> chanceKeyAPI(code as String)

                // Erro chave desconhecida
                WeatherConstants.HTTP.ERROR_KEY -> alertDialogError(code as String)
            }
        }

        // OneHour API
        viewModel.oneHour.observe(this) {
            handleDataOneHour(it)
        }

        // OneDay API
        viewModel.listDay.observe(this) {

            // Pegar o dia de hoje
            handleDataOneDay(it.dailyForecasts[0])

            // Converter objeto para string
            listOfDays = viewModel.fromObjectToJson(it)
        }

        // TwelveHours API
        viewModel.twelveHours.observe(this) {
            hourlyAdapter.updateHourly(it)
        }

    }

    // Alterar chave API
    private fun chanceKeyAPI(code: String) {
        when (viewModel.getKeyAPI()) {
            WeatherConstants.APIWeather.KEY_1 -> {
                viewModel.setKeyAPI(WeatherConstants.APIWeather.KEY_2)
                listWeather()
                handleOnResume()
            }
            WeatherConstants.APIWeather.KEY_2 -> {
                viewModel.setKeyAPI(WeatherConstants.APIWeather.KEY_3)
                listWeather()
                handleOnResume()
            }
            WeatherConstants.APIWeather.KEY_3 -> {
                alertDialogError(code)
            }
        }
    }

    // Atribuir valores OnResume
    private fun handleOnResume() {
        // Data
        val dateFormat = SimpleDateFormat("EEEE", Locale.getDefault())
        val monthFormat = SimpleDateFormat("MMMM", Locale.getDefault())
        var dayOfWeekText = dateFormat.format(Calendar.getInstance().time)
        dayOfWeekText = dayOfWeekText.replaceFirstChar { it.uppercase() }
        dayOfWeekText = dayOfWeekText.substring(0, 3)
        val monthText = monthFormat.format(Calendar.getInstance().time)
        val dayOfMonth = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        binding.textDate.text = "$dayOfWeekText, $monthText $dayOfMonth"

        // Cidade
        binding.textCity.text = viewModel.getCity()

    }

    // Atribuir valores OneHour
    private fun handleDataOneHour(data: OneHourModel) {

        // imagem tempo
        when (data.weatherIcon) {
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

        // frase tempo
        binding.textWeather.text = data.iconPhrase

        // temperatura
        var temp = data.temperature.value.toInt().toString()
        var unitTemp = data.temperature.unit
        binding.textTemp.text = temp + "º" + unitTemp

        // velocidade vento
        val windSpeedValue = data.wind.speed.value.toInt().toString()
        val unit = data.wind.speed.unit
        val windSpeed = "$windSpeedValue $unit"
        binding.textDataWindSpeed.text = windSpeed

        // humidade
        binding.textDataHumidity.text = data.humidity + "%"
    }

    // Atribuir valores OneDay
    private fun handleDataOneDay(data: DailyForecasts) {

        // Temperatura maxima e minima
        val valueHigh = data.temperature.maximum.value.toInt().toString()
        val valueLow = data.temperature.minimum.value.toInt().toString()

        val unit = data.temperature.maximum.unit

        val tempHigh = "Max:" + valueHigh + "º" + unit
        val templow = "Min:" + valueLow + "º" + unit
        binding.textTempHigh.text = tempHigh
        binding.textTempLow.text = templow


        // chance de chuva
        val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        val chanceDay = data.day.rainProbability
        val chanceNight = data.night.rainProbability

        if (hour < 18) {
            binding.textDataChanceRain.text = chanceDay + "%"
        } else {
            binding.textDataChanceRain.text = chanceNight + "%"
        }
    }

    // Iniciar recycler
    private fun initRecyclerView() {
        hourlyAdapter = HourlyAdapter()

        val horizontal = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerHouraly.layoutManager = horizontal
        binding.recyclerHouraly.adapter = hourlyAdapter
    }

    // Alert dialog de erro
    private fun alertDialogError(code: String) {
        val builder = MaterialAlertDialogBuilder(this)
        builder.setTitle("Erro $code")

        when (code) {
            "503" -> { builder.setMessage("Numero de chamadas a API excedida, deseja tentar novamente?") }
        }
        builder.setMessage("Erro desconhecido, deseja tentar novamente?")

        builder.setOnCancelListener { recreate() }

        builder.setPositiveButton("Sim") { view, value -> recreate() }

        builder.setNegativeButton("Fechar o app") { view, value -> finishAffinity() }


        builder.create()
        builder.show()
    }

}