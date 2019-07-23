package com.iazarevsergey.lessons.data.repository

import com.iazarevsergey.lessons.data.network.ApiService
import com.iazarevsergey.lessons.domain.model.CurrentWeather
import com.iazarevsergey.lessons.domain.model.Search
import com.iazarevsergey.lessons.domain.repository.IWeatherRepository
import io.reactivex.Single
import java.util.*
import javax.inject.Inject

class WeatherRepository @Inject constructor(private val api: ApiService) : IWeatherRepository {

    override fun getCurrentWeather(location: String): Single<CurrentWeather> {
        return api.getCurrentWeather(location, Locale.getDefault().language).map { it.asDomain() }
    }

    override fun getSearches(location: String): Single<List<Search>> {
        return api.getSearches(location, Locale.getDefault().language).map { it.map { it.asDomain() } }
    }

}