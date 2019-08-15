package com.iazarevsergey.lessons.domain.repository

import com.iazarevsergey.lessons.domain.model.Search
import com.iazarevsergey.lessons.domain.model.Weather
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

interface IWeatherRepository {

    fun getWeather(lat: Double, lon: Double): Observable<Weather>
    fun getSearches(location: String): Single<List<Search>>
    fun getUserWeatherList(): Observable<List<Weather>>
    fun addWeather(location: String): Completable
    fun deleteWeather(item: Weather): Completable
    fun updateWeather(item: Weather): Completable
    fun updateAllWeather(items: List<Weather>): Completable
}