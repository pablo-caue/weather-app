package com.example.weatherapp.view

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapp.R
import com.example.weatherapp.databinding.ActivitySearchBinding
import com.example.weatherapp.service.constants.WeatherConstants
import com.example.weatherapp.service.listener.CityListener
import com.example.weatherapp.view.adapter.CityAdapter
import com.example.weatherapp.viewmodel.SearchViewModel
import java.util.*

class SearchActivity : AppCompatActivity(), SearchView.OnQueryTextListener, View.OnClickListener {

    private lateinit var binding: ActivitySearchBinding
    private lateinit var viewModel: SearchViewModel
    private lateinit var adapter: CityAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivitySearchBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)

        // Layout
        setContentView(binding.root)

        // Variaveis da classe
        viewModel = ViewModelProvider(this)[SearchViewModel::class.java]
        adapter = CityAdapter()

        // Esconder ActionBar
        supportActionBar?.hide()

        // Eventos de click
        binding.imageBack.setOnClickListener(this)
        binding.searchView.setOnQueryTextListener(this)
        binding.imageLocale.setOnClickListener(this)
        binding.textLocation.setOnClickListener(this)
        binding.textLocationDescription.setOnClickListener(this)

        // Listener click recyclerView
        val listener = object : CityListener {
            override fun onClick(city: String, latitude: String, longitude: String) {
                val key = intent.getStringExtra(WeatherConstants.EXTRA.API_KEY)
                viewModel.getKeyByPosition(city, latitude, longitude, key!!)
            }

        }

        // Lançar listener recyclerView
        adapter.attachAdapter(listener)

        // Iniciar recyclerView
        initRecyclerView()

        //Observer
        observer()

    }

    // Eventos de click
    override fun onClick(view: View) {
        when (view.id) {
            R.id.image_back -> finish()

            R.id.image_locale, R.id.text_location, R.id.text_location_description -> viewModel.getLocale()
        }
    }

    // Usuario apertou no enviar
    override fun onQueryTextSubmit(string: String?): Boolean {

        //Fecha o teclado
        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(binding.root.windowToken, 0)
        return true
    }

    // Usuario digitou
    override fun onQueryTextChange(string: String?): Boolean {
        if (string != null && string != "") {
            val lang = getLanguege()
            binding.recyclerView.visibility = View.VISIBLE
            viewModel.search(string)
        } else {
            binding.recyclerView.visibility = View.GONE
        }
        return true
    }

    private fun getLanguege(): String {
        return Locale.getDefault().language
    }

    private fun observer() {
        viewModel.listSearch.observe(this) {
            adapter.updateCities(it)
        }

        viewModel.test.observe(this) {
            val latitude = it.latitude
            val longitude = it.longitude
            val str = "${latitude}, ${longitude}"
            Toast.makeText(this, str, Toast.LENGTH_SHORT).show()
        }

        viewModel.isSaved.observe(this){
            if (it){
                finish()
            }
        }

    }

    // Inicia RecyclerView
    private fun initRecyclerView() {

        val vertical = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.layoutManager = vertical
        binding.recyclerView.adapter = adapter
    }


}