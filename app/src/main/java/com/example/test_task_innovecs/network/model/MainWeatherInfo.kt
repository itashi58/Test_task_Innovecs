package com.example.test_task_innovecs.network.model

import com.google.gson.annotations.SerializedName

data class MainWeatherInfo(
    @SerializedName("temp")
    val temperature: Double? = null,
    @SerializedName("feels_like")
    val feelsLike: Double? = null,
    val humidity: Int? = null
)