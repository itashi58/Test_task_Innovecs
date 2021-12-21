package com.example.test_task_innovecs.network.model

import com.example.test_task_innovecs.db.WeatherEntity

data class WeatherForecast(
    var weather: ArrayList<Weather>? = null,
    var main: MainWeatherInfo? = null
) {
    fun convertToWeatherEntity(city: String): WeatherEntity {
        return WeatherEntity(
            city, this.main?.temperature, this.main?.feelsLike,
            this.main?.humidity, this.weather?.first()?.weather
        )
    }
}