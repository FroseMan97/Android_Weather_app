package com.iazarevsergey.lessons.data.repository

import com.iazarevsergey.lessons.data.network.WeatherApi
import com.iazarevsergey.lessons.domain.model.CurrentWeather
import com.iazarevsergey.lessons.domain.model.Search
import com.iazarevsergey.lessons.domain.repository.IWeatherRepository
import io.reactivex.Single
import java.util.*
import javax.inject.Inject

class WeatherRepository @Inject constructor(private val weatherApi: WeatherApi) : IWeatherRepository {

    override fun getCurrentWeather(location: String): Single<CurrentWeather> {
        return weatherApi.getCurrentWeather(location, Locale.getDefault().language).map { it.asDomain() }
    }

    override fun getSearches(location: String): Single<List<Search>> {
        return weatherApi.getSearches(location, Locale.getDefault().language).map { it.map { it.asDomain() } }
    }

}