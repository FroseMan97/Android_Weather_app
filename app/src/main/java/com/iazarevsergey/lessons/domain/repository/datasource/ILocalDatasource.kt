package com.iazarevsergey.lessons.domain.repository.datasource

import com.iazarevsergey.lessons.data.model.response.WeatherResponse
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

interface ILocalDatasource {
    fun addWeather(weatherResponse: WeatherResponse)

    fun deleteWeather(weatherResponse: WeatherResponse)

    fun getWeather(lat:Double,lon:Double): Single<WeatherResponse>

    fun getAllWeather():Observable<List<WeatherResponse>>

    fun saveWeather(weatherResponse: WeatherResponse)
}