package com.iazarevsergey.lessons.domain.usecase

import com.iazarevsergey.lessons.data.repository.WeatherRepository
import com.iazarevsergey.lessons.domain.model.Weather
import io.reactivex.Completable
import javax.inject.Inject

class UpdateAllWeatherUsecase @Inject constructor(
    private val weatherRepository: WeatherRepository
) {
    fun execute(items: List<Weather>): Completable {
        return weatherRepository.updateAllWeather(items)
    }
}