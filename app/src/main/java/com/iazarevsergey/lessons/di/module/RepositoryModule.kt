package com.iazarevsergey.lessons.di.module

import com.iazarevsergey.lessons.data.repository.WeatherRepository
import com.iazarevsergey.lessons.data.datasource.ConnectivityDatasource
import com.iazarevsergey.lessons.data.datasource.LocalDatasource
import com.iazarevsergey.lessons.data.datasource.RemoteDatasource
import com.iazarevsergey.lessons.domain.repository.IWeatherRepository
import com.iazarevsergey.lessons.domain.datasource.IConnectivityDatasource
import com.iazarevsergey.lessons.domain.datasource.ILocalDatasource
import com.iazarevsergey.lessons.domain.datasource.IRemoteDatasource
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface RepositoryModule {

    @Binds
    @Singleton
    fun bindWeatherRepository(weatherRepository: WeatherRepository): IWeatherRepository

}