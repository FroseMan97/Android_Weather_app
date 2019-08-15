package com.iazarevsergey.lessons.data.datasource

import com.iazarevsergey.lessons.data.model.response.SearchResponse
import com.iazarevsergey.lessons.data.model.response.WeatherResponse
import com.iazarevsergey.lessons.data.network.WeatherApi
import com.iazarevsergey.lessons.domain.datasource.IRemoteDatasource
import io.reactivex.Single
import java.util.*
import javax.inject.Inject

class RemoteDatasource @Inject constructor(
    private val weatherApi: WeatherApi
) : IRemoteDatasource {

    override fun getWeather(location: String): Single<WeatherResponse> {
        return weatherApi.getCurrentWeather(location, Locale.getDefault().language)

    }

    override fun addWeather(location: String): Single<WeatherResponse> {
        return weatherApi.getCurrentWeather(location, Locale.getDefault().language)

    }

    override fun getSearches(location: String): Single<List<SearchResponse>> {
        return weatherApi.getSearches(location, Locale.getDefault().language)

    }
}