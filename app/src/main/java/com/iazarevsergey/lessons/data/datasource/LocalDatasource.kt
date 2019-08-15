package com.iazarevsergey.lessons.data.datasource

import com.iazarevsergey.lessons.data.db.WeatherDatabase
import com.iazarevsergey.lessons.data.model.response.WeatherResponse
import com.iazarevsergey.lessons.domain.datasource.ILocalDatasource
import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject

class LocalDatasource @Inject constructor(
    private val weatherDatabase: WeatherDatabase
) : ILocalDatasource {
    override fun saveAllWeather(items: List<WeatherResponse>) {
        return dao.insertAllWeathers(items)
    }

    override fun deleteAllWeather(): Completable {
        return dao.deleteAllWeathers()
    }

    override fun updateWeather(weatherResponse: WeatherResponse): Completable {
        return dao.updateWeather(weatherResponse)
    }

    override fun updateAllWeather(items: List<WeatherResponse>) {
        dao.updateAllWeather(items)
    }

    override fun getAllWeather(): Observable<List<WeatherResponse>> {
        return dao.getAllWeathers()
    }

    private val dao = weatherDatabase.getWeatherDao()


    override fun deleteWeather(weatherResponse: WeatherResponse): Completable {
        return dao.deleteWeather(weatherResponse)
    }

    override fun getWeather(lat: Double, lon: Double): Observable<WeatherResponse> {
        return dao.getWeatherByCoordinates(lat, lon)
    }

    override fun saveWeather(weatherResponse: WeatherResponse): Completable {
        return dao.insertWeather(weatherResponse)
    }

}