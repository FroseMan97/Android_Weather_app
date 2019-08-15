package com.iazarevsergey.lessons.data.repository

import com.iazarevsergey.lessons.data.model.response.SearchResponseMapper
import com.iazarevsergey.lessons.data.model.response.WeatherMapper
import com.iazarevsergey.lessons.data.model.response.WeatherResponseMapper
import com.iazarevsergey.lessons.domain.model.Search
import com.iazarevsergey.lessons.domain.model.Weather
import com.iazarevsergey.lessons.domain.repository.IWeatherRepository
import com.iazarevsergey.lessons.domain.repository.datasource.IConnectivityDatasource
import com.iazarevsergey.lessons.domain.repository.datasource.ILocalDatasource
import com.iazarevsergey.lessons.domain.repository.datasource.IRemoteDatasource
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    private val localDatasource: ILocalDatasource,
    private val remoteDatasource: IRemoteDatasource,
    private val connectivityDatasource: IConnectivityDatasource //TODO вроде больше не нужен
) : IWeatherRepository {

    override fun updateWeather(item: Weather): Completable {
        val location = "${item.location_lat},${item.location_lon}"
        return remoteDatasource.getWeather(location)
            .flatMapCompletable { localDatasource.updateWeather(it) }

    }

    override fun updateAllWeather(items: List<Weather>): Completable { //TODO не трекаются ошибки
        return Single.just(items).toObservable()
            .flatMapIterable { items -> items }
            .flatMapCompletable { updateWeather(it) }
    }

    override fun getUserWeatherList(): Observable<List<Weather>> {
        return localDatasource.getAllWeather().map { it.map { WeatherMapper.mapTo(it) } }
    }

    override fun getWeather(lat: Double, lon: Double): Observable<Weather> {
        return localDatasource.getWeather(lat, lon)
            .map { WeatherMapper.mapTo(it) }

    }

    override fun addWeather(location: String): Completable {
        return remoteDatasource.getWeather(location)
            .flatMapCompletable { localDatasource.saveWeather(it) }
    }

    override fun deleteWeather(item: Weather): Completable {
        return localDatasource.deleteWeather(WeatherResponseMapper.mapTo(item))
    }


    override fun getSearches(location: String): Single<List<Search>> {
        return remoteDatasource.getSearches(location).map { it.map { SearchResponseMapper.mapTo(it) } }
    }
}