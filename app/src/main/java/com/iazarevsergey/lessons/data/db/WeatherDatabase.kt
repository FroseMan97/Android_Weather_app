package com.iazarevsergey.lessons.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.iazarevsergey.lessons.data.model.response.WeatherResponse

@Database(entities = [WeatherResponse::class],version = 1)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun getWeatherDao():WeatherDao
}