package com.iazarevsergey.lessons.data.repository.datasource

import com.iazarevsergey.lessons.data.db.WeatherDatabase
import com.iazarevsergey.lessons.data.model.Condition
import com.iazarevsergey.lessons.data.model.LocationModel
import com.iazarevsergey.lessons.data.model.WeatherModel
import com.iazarevsergey.lessons.data.model.response.WeatherResponse
import com.iazarevsergey.lessons.domain.repository.datasource.ILocalDatasource
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class LocalDatasource @Inject constructor(
    private val weatherDatabase: WeatherDatabase
) : ILocalDatasource {
    override fun getAllWeather(): Observable<List<WeatherResponse>> {
        return dao.getAllWeathers()
    }

    private val dao = weatherDatabase.getWeatherDao()

    override fun addWeather(weatherResponse: WeatherResponse) {
         dao.insertWeather(weatherResponse)
    }

    override fun deleteWeather(weatherResponse: WeatherResponse) {
         dao.deleteWeather(weatherResponse)
    }

    override fun getWeather(lat: Double, lon: Double): Single<WeatherResponse> {
        return dao.getWeatherByCoordinates(lat, lon)
    }

    override fun saveWeather(weatherResponse: WeatherResponse) {
         dao.insertWeather(weatherResponse)
    }
}