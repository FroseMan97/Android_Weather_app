package com.iazarevsergey.lessons.data.network

import com.iazarevsergey.lessons.data.model.response.SearchResponse
import com.iazarevsergey.lessons.data.model.response.CurrentWeatherResponse
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Inject

class  WeatherApi @Inject constructor(private val weatherEndpoint: IWeatherEndpoint) {

    fun getCurrentWeather(location:String, language:String): Single<CurrentWeatherResponse>{
        return weatherEndpoint.getCurrentWeather(location,language)
    }


    fun getSearches(location: String,language: String): Single<List<SearchResponse>>{
        return weatherEndpoint.getSearches(location,language)
    }

}