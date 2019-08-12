package com.iazarevsergey.lessons.di.module

import com.iazarevsergey.lessons.data.repository.WeatherRepository
import com.iazarevsergey.lessons.data.repository.datasource.ConnectivityDatasource
import com.iazarevsergey.lessons.data.repository.datasource.LocalDatasource
import com.iazarevsergey.lessons.data.repository.datasource.RemoteDatasource
import com.iazarevsergey.lessons.domain.repository.IWeatherRepository
import com.iazarevsergey.lessons.domain.repository.datasource.IConnectivityDatasource
import com.iazarevsergey.lessons.domain.repository.datasource.ILocalDatasource
import com.iazarevsergey.lessons.domain.repository.datasource.IRemoteDatasource
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface RepositoryModule {

    @Binds
    @Singleton
    fun bindWeatherRepository(weatherRepository: WeatherRepository): IWeatherRepository

    @Binds
    @Singleton
    fun bindLocalDatasource(localDatasource: LocalDatasource): ILocalDatasource

    @Binds
    @Singleton
    fun bindRemoteDatasource(remoteDatasource: RemoteDatasource): IRemoteDatasource

    @Binds
    @Singleton
    fun bindConnectivityDatasource(connectivityDatasource: ConnectivityDatasource):IConnectivityDatasource
}