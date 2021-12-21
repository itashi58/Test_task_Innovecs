package com.example.test_task_innovecs

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(app: Application) : AndroidViewModel(app) {

    private val repository = Repository(this.getApplication())

    lateinit var location: Location

    private val _temperature = MutableLiveData<Double>()
    val temperature: LiveData<Double>
        get() = _temperature

    private val _feelsLike = MutableLiveData<Double>()
    val feelsLike: LiveData<Double>
        get() = _feelsLike

    private val _humidity = MutableLiveData<Int>()
    val humidity: LiveData<Int>
        get() = _humidity

    private val _weather = MutableLiveData<String>()
    val weather: LiveData<String>
        get() = _weather

    fun getWeather(currentLocation: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val weatherForecast =
                if (currentLocation == getApplication<Application>().resources.getString(R.string.current_location)) {
                    repository.getWeatherInCurrentLocation(location, currentLocation)
                } else {
                    repository.getWeatherInCity(currentLocation)
                }

            weatherForecast?.main?.temperature?.let {
                _temperature.postValue(it)
            }

            weatherForecast?.main?.feelsLike?.let {
                _feelsLike.postValue(it)
            }

            weatherForecast?.main?.humidity?.let {
                _humidity.postValue(it)
            }

            weatherForecast?.weather?.first()?.weather?.let {
                _weather.postValue(it)
            }
        }
    }
}