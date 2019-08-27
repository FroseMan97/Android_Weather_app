package com.iazarevsergey.lessons.data.datasource

import com.iazarevsergey.lessons.domain.model.Weather
import io.reactivex.Completable
import io.reactivex.Observable

interface ILocalDatasource {

    fun deleteWeather(weather: Weather): Completable

    fun deleteAllWeather(): Completable

    fun getWeather(lat: Double, lon: Double): Observable<Weather>

    fun getAllWeather(): Observable<List<Weather>>

    fun saveWeather(weather: Weather): Completable

    fun saveAllWeather(items: List<Weather>)

    fun updateWeather(weather: Weather): Completable

    fun updateAllWeather(items: List<Weather>)
}