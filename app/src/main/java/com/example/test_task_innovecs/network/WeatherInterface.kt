package com.example.test_task_innovecs.network

import com.example.test_task_innovecs.network.model.WeatherForecast
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherInterface {
    @GET(Endpoints.CURRENT_WEATHER)
    suspend fun getWeatherInCity(@Query("q") currentCity: String): Response<WeatherForecast?>

    @GET(Endpoints.CURRENT_WEATHER)
    suspend fun getWeatherInLocation(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double
    ): Response<WeatherForecast>


}