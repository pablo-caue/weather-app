package com.example.weatherapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.example.weatherapp.R
import com.example.weatherapp.databinding.ActivitySettingsBinding
import com.example.weatherapp.view.adapter.CustomSpinnerAdapter

class SettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val spinnerData = listOf("LISTA1", "LISTA2", "lISTA3")

        val adapter = CustomSpinnerAdapter(this, android.R.layout.simple_spinner_item, spinnerData)

        adapter.setDropDownViewResource(R.layout.viewholder_spinner)
        binding.sppinerTimeFormat.adapter = adapter

    }
}