package com.iazarevsergey.lessons.domain.usecase

import com.iazarevsergey.lessons.data.model.response.WeatherResponse
import com.iazarevsergey.lessons.data.model.response.WeatherResponseMapper
import com.iazarevsergey.lessons.domain.model.Weather
import com.iazarevsergey.lessons.domain.repository.IWeatherRepository
import io.reactivex.Completable
import javax.inject.Inject

class DeleteWeatherUsecase @Inject constructor(private val weatherRepository: IWeatherRepository) {
    fun execute(item: WeatherResponse): Completable {
        return Completable.fromAction {
            weatherRepository.deleteWeather(item)
        }
    }
}