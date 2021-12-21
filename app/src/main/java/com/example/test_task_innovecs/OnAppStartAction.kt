package com.example.test_task_innovecs

import android.content.Context
import com.example.test_task_innovecs.db.WeatherDB

object OnAppStartAction {

    private var applicationLaunched = false

    // clears DB only when app was launched
    fun clearDB(context: Context) {
        if (!applicationLaunched) {
            val weatherDao = WeatherDB.getDatabase(context).weatherDao()
            weatherDao.deleteAll()
            applicationLaunched = true
        }
    }
}