package com.iazarevsergey.lessons.domain.datasource

import com.iazarevsergey.lessons.data.model.response.WeatherResponse
import io.reactivex.Completable
import io.reactivex.Observable

interface ILocalDatasource {

    fun deleteWeather(weatherResponse: WeatherResponse): Completable

    fun deleteAllWeather(): Completable

    fun getWeather(lat: Double, lon: Double): Observable<WeatherResponse>

    fun getAllWeather(): Observable<List<WeatherResponse>>

    fun saveWeather(weatherResponse: WeatherResponse): Completable

    fun saveAllWeather(items: List<WeatherResponse>)

    fun updateWeather(weatherResponse: WeatherResponse): Completable

    fun updateAllWeather(items: List<WeatherResponse>)
}