package com.iazarevsergey.lessons.data.repository

import com.iazarevsergey.lessons.data.model.response.SearchResponse
import com.iazarevsergey.lessons.data.model.response.WeatherResponse
import com.iazarevsergey.lessons.data.repository.datasource.ConnectivityDatasource
import com.iazarevsergey.lessons.domain.repository.IWeatherRepository
import com.iazarevsergey.lessons.domain.repository.datasource.IConnectivityDatasource
import com.iazarevsergey.lessons.domain.repository.datasource.ILocalDatasource
import com.iazarevsergey.lessons.domain.repository.datasource.IRemoteDatasource
import io.reactivex.Observable
import io.reactivex.Single
import java.io.IOException
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    private val localDatasource: ILocalDatasource,
    private val remoteDatasource: IRemoteDatasource,
    private val connectivityDatasource: IConnectivityDatasource
) : IWeatherRepository {

    override fun getUserWeatherList(): Observable<List<WeatherResponse>> {
        return localDatasource.getAllWeather()
    }

    override fun getWeather(location: String): Single<WeatherResponse> {
        val coordinates = location.split(",")
        if(connectivityDatasource.isNetworkOn()) {
            return remoteDatasource.getWeather(location)
                .doOnSuccess { localDatasource.saveWeather(it) }
        }
        else{
            return localDatasource.getWeather(coordinates[0].toDouble(), coordinates[1].toDouble())
        }
    }


    override fun addWeather(location: String): Single<WeatherResponse> {
        return remoteDatasource.getWeather(location)
            .doOnSuccess { localDatasource.saveWeather(it) }
    }


    override fun deleteWeather(item: WeatherResponse) {
        localDatasource.deleteWeather(item)
    }


    override fun getSearches(location: String): Single<List<SearchResponse>> =
        remoteDatasource.getSearches(location)


}