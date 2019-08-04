package com.iazarevsergey.lessons.data.network

import com.iazarevsergey.lessons.data.model.response.CurrentWeatherResponse
import com.iazarevsergey.lessons.data.model.response.SearchResponse
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

// http://api.apixu.com/v1/current.json?key=89e8bd89085b41b7a4b142029180210&q=Moscow&lang=ru

interface IWeatherEndpoint {
    @GET("current.json")
    fun getCurrentWeather(
        @Query("q") location: String,
        @Query("lang") language: String = "en"
    ): Single<CurrentWeatherResponse>


    @GET("search.json")
    fun getSearches(
        @Query("q") location: String,
        @Query("lang") language: String = "en"
    ): Single<List<SearchResponse>>
}