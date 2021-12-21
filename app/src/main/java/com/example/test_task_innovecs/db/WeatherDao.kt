package com.example.test_task_innovecs.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface WeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(weatherEntity: WeatherEntity)

    @Query("SELECT * FROM ${WeatherEntity.TABLE} WHERE id = :id")
    fun getWeather(id: String): WeatherEntity?

    @Query("DELETE FROM ${WeatherEntity.TABLE}")
    fun deleteAll()

}