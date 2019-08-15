package com.iazarevsergey.lessons.domain.usecase

import com.iazarevsergey.lessons.data.repository.WeatherRepository
import com.iazarevsergey.lessons.domain.model.Weather
import io.reactivex.Completable
import javax.inject.Inject

class UpdateWeatherUsecase @Inject constructor(
    private val weatherRepository: WeatherRepository
) {
    fun execute(item: Weather): Completable {
        return weatherRepository.updateWeather(item)
    }
}