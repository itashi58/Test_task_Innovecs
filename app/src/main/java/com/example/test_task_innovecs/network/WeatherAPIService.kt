package com.example.test_task_innovecs.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient
import okhttp3.Request


object WeatherAPIService {
    private var retrofit: Retrofit? = null
    private const val APP_ID = "de40735200986b2b179818c9ae2e3188"
    private const val DEFAULT_UNITS = "metric"
    private const val BASE_URL = "https://api.openweathermap.org/data/2.5/"

    val weatherAPIInterface: WeatherInterface =
        getRetrofit().create(WeatherInterface::class.java)

    private fun getRetrofit(): Retrofit {

        val httpClient = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val original: Request = chain.request()
                val url = original.url().newBuilder()
                    .addQueryParameter("appid", APP_ID)
                    .addQueryParameter("units", DEFAULT_UNITS)
                    .build()

                val requestBuilder: Request.Builder = original.newBuilder()
                    .url(url)
                val request: Request = requestBuilder.build()
                return@addInterceptor chain.proceed(request)
            }

        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(httpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit!!
    }

}