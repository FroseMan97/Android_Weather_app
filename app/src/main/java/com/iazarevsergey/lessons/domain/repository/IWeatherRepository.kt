package com.iazarevsergey.lessons.domain.repository

import com.iazarevsergey.lessons.data.model.response.SearchResponse
import com.iazarevsergey.lessons.data.model.response.WeatherResponse
import com.iazarevsergey.lessons.domain.model.Search
import com.iazarevsergey.lessons.domain.model.Weather
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

interface IWeatherRepository {

    fun getWeather(location: String): Single<WeatherResponse>
    fun getSearches(location: String): Single<List<SearchResponse>>
    fun getUserWeatherList(): Observable<List<WeatherResponse>>
    fun addWeather(location: String): Single<WeatherResponse>
    fun deleteWeather(item:WeatherResponse)
}