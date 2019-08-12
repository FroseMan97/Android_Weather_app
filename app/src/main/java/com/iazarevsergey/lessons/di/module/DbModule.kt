package com.iazarevsergey.lessons.di.module

import android.content.Context
import androidx.room.Room
import com.iazarevsergey.lessons.data.db.WeatherDao
import com.iazarevsergey.lessons.data.db.WeatherDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DbModule {

    @Singleton
    @Provides
    fun provideWeatherDatabase(context:Context):WeatherDatabase{
        return Room.databaseBuilder(
            context,
            WeatherDatabase::class.java,
            "weathers"
        ).build()
    }
}