package com.iazarevsergey.lessons.data.datasource

import com.iazarevsergey.lessons.data.model.response.SearchResponse
import com.iazarevsergey.lessons.data.model.response.WeatherResponse
import io.reactivex.Single

interface IRemoteDatasource {
    fun getWeather(location: String): Single<WeatherResponse>
    fun addWeather(location: String): Single<WeatherResponse>
    fun getSearches(location: String): Single<List<SearchResponse>>
}