package com.example.test_task_innovecs.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.test_task_innovecs.db.WeatherEntity.Companion.TABLE
import com.example.test_task_innovecs.network.model.MainWeatherInfo
import com.example.test_task_innovecs.network.model.Weather
import com.example.test_task_innovecs.network.model.WeatherForecast

@Entity(tableName = TABLE)
data class WeatherEntity(
    @PrimaryKey
    val id: String,
    val temperature: Double?,
    val feelsLike: Double?,
    val humidity: Int?,
    val weather: String?
) {
    fun convertToWeatherForecast(): WeatherForecast {
        return WeatherForecast(
            arrayListOf(Weather(this.weather)),
            MainWeatherInfo(temperature, feelsLike, humidity)
        )
    }

    companion object {
        const val TABLE = "weather_data_table"
    }
}