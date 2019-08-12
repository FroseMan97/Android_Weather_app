package com.iazarevsergey.lessons.data.network

import com.iazarevsergey.lessons.data.model.response.WeatherResponse
import com.iazarevsergey.lessons.data.model.response.SearchResponse
import io.reactivex.Single
import javax.inject.Inject

class WeatherApi @Inject constructor(private val weatherEndpoint: IWeatherEndpoint) {

    fun getCurrentWeather(location: String, language: String): Single<WeatherResponse> {
        return weatherEndpoint.getCurrentWeather(location, language)
    }


    fun getSearches(location: String, language: String): Single<List<SearchResponse>> {
        return weatherEndpoint.getSearches(location, language)
    }

}