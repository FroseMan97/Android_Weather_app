package com.iazarevsergey.lessons.datasource

import com.iazarevsergey.lessons.data.db.WeatherDatabase
import com.iazarevsergey.lessons.data.datasource.ILocalDatasource
import com.iazarevsergey.lessons.domain.model.Weather
import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject

class LocalDatasource @Inject constructor(
    private val weatherDatabase: WeatherDatabase
) : ILocalDatasource {

    private val dao = weatherDatabase.getWeatherDao()

    override fun saveAllWeather(items: List<Weather>) {
        return dao.insertAllWeathers(items)
    }

    override fun deleteAllWeather(): Completable {
        return dao.deleteAllWeathers()
    }

    override fun updateWeather(weather: Weather): Completable {
        return dao.updateWeather(weather)
    }

    override fun updateAllWeather(items: List<Weather>) {
        dao.updateAllWeather(items)
    }

    override fun getAllWeather(): Observable<List<Weather>> {
        return dao.getAllWeathers()
    }

    override fun deleteWeather(weather: Weather): Completable {
        return dao.deleteWeather(weather)
    }

    override fun getWeather(lat: Double, lon: Double): Observable<Weather> {
        return dao.getWeatherByCoordinates(lat, lon)
    }

    override fun saveWeather(weather: Weather): Completable {
        return dao.insertWeather(weather)
    }

}