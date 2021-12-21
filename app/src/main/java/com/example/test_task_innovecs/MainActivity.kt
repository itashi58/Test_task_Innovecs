package com.example.test_task_innovecs

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.test_task_innovecs.databinding.ActivityMainBinding
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.CancellationTokenSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import android.location.LocationManager
import androidx.core.location.LocationRequestCompat


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel =
            ViewModelProvider.AndroidViewModelFactory(application).create(MainViewModel::class.java)

        val items = resources.getStringArray(R.array.cities_without_location)
        val adapter = ArrayAdapter(baseContext, R.layout.city_list_item, items)
        binding.cityChooserAuto.setAdapter(adapter)

        clearDbOnLaunch()
        getLocation()
        setObservers()

        binding.getForecastBtn.setOnClickListener {
            val currentLocation = binding.cityChooserAuto.text.toString()
            if (currentLocation.isNotEmpty()) {
                viewModel.getWeather(currentLocation)
            }
        }
    }


    private fun setObservers() {
        viewModel.temperature.observe(this, {
            binding.temperatureValueTv.text = String.format(getString(R.string.temp_celsius), it.toString())
        })

        viewModel.feelsLike.observe(this, {
            binding.feelsLikeValueTv.text = String.format(getString(R.string.temp_celsius), it.toString())
        })

        viewModel.weather.observe(this, {
            binding.weatherValueTv.text = it
        })

        viewModel.humidity.observe(this, {
            binding.humidityValueTv.text = String.format(getString(R.string.humidity_percent), it.toString())
        })
    }

    private fun clearDbOnLaunch() {
        lifecycleScope.launch(Dispatchers.IO) {
            OnAppStartAction.clearDB(applicationContext)
        }
    }

    @SuppressLint("MissingPermission")
    private fun getLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED
        ) {
            val fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
            val lm = application.getSystemService(Context.LOCATION_SERVICE) as LocationManager

            if (lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                fusedLocationClient.lastLocation.addOnCompleteListener { lastLocationTask ->
                    if (lastLocationTask.isSuccessful && lastLocationTask.result != null) {
                        val lat = lastLocationTask.result.latitude
                        val lon = lastLocationTask.result.longitude
                        viewModel.location = Location(lat, lon)
                        showLocationData(lat, lon)
                    } else {
                        val currentLocation =
                            fusedLocationClient.getCurrentLocation(
                                LocationRequestCompat.QUALITY_HIGH_ACCURACY,
                                CancellationTokenSource().token
                            )
                        currentLocation.addOnSuccessListener {
                            val lat = it.latitude
                            val lon = it.longitude
                            viewModel.location = Location(lat, lon)
                            showLocationData(lat, lon)
                        }
                    }
                }
            }
        }
    }

    private fun showLocationData(lat: Double, lon: Double) {
        val locationTextView = binding.locationTv
        locationTextView.text = String.format(getString(R.string.location_lat_lon), lat, lon)
        locationTextView.visibility = View.VISIBLE

        val items = resources.getStringArray(R.array.cities_with_location)
        val adapter = ArrayAdapter(baseContext, R.layout.city_list_item, items)
        binding.cityChooserAuto.setAdapter(adapter)
    }
}