package com.iazarevsergey.lessons.di.module

import com.iazarevsergey.lessons.data.repository.WeatherRepository
import com.iazarevsergey.lessons.domain.repository.IWeatherRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface RepositoryModule {

    @Binds
    @Singleton
    fun bindWeatherRepository(weatherRepository: WeatherRepository): IWeatherRepository

}