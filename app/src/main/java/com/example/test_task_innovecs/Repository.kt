package com.example.test_task_innovecs

import android.content.Context
import com.example.test_task_innovecs.db.WeatherDB
import com.example.test_task_innovecs.network.WeatherAPIService
import com.example.test_task_innovecs.network.model.WeatherForecast

class Repository(context: Context) {
    private val restService = WeatherAPIService.weatherAPIInterface
    private val weatherDao = WeatherDB.getDatabase(context).weatherDao()

    suspend fun getWeatherInCity(city: String): WeatherForecast? {
        val dataFromDB = weatherDao.getWeather(city)
        if (dataFromDB == null) {
            val weatherForecastResponse = restService.getWeatherInCity(city)

            if (weatherForecastResponse.code() == SUCCESSFUL_RESPONSE_CODE) {
                val weatherForecast = weatherForecastResponse.body()
                if (weatherForecast != null) {
                    weatherDao.insert(weatherForecast.convertToWeatherEntity(city))
                }
                return weatherForecast
            }
            return null
        } else {
            return dataFromDB.convertToWeatherForecast()
        }
    }

    suspend fun getWeatherInCurrentLocation(location: Location, currentLocationDbId: String): WeatherForecast? {
        val dataFromDB = weatherDao.getWeather(currentLocationDbId)
        if (dataFromDB == null) {
            val weatherForecastResponse = restService.getWeatherInLocation(location.lat, location.lon)
            if (weatherForecastResponse.code() == SUCCESSFUL_RESPONSE_CODE) {
                val weatherForecast = weatherForecastResponse.body()
                if (weatherForecast != null) {
                    weatherDao.insert(weatherForecast.convertToWeatherEntity(currentLocationDbId))
                }
                return weatherForecast
            }
            return null
        } else {
            return dataFromDB.convertToWeatherForecast()
        }
    }

    companion object {
        const val SUCCESSFUL_RESPONSE_CODE = 200
    }
}