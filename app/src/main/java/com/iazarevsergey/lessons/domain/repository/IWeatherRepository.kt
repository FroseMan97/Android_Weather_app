package com.iazarevsergey.lessons.domain.repository

import com.iazarevsergey.lessons.domain.model.CurrentWeather
import com.iazarevsergey.lessons.domain.model.Search
import io.reactivex.Single

interface IWeatherRepository {

    fun getCurrentWeather(location:String): Single<CurrentWeather>
    fun getSearches(location:String): Single<List<Search>>

}