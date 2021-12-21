package com.example.test_task_innovecs.network.model

import com.google.gson.annotations.SerializedName

data class Weather(
    @SerializedName("main")
    val weather: String? = null
)