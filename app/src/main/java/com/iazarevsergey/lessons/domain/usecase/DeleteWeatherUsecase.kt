package com.iazarevsergey.lessons.domain.usecase

import com.iazarevsergey.lessons.domain.model.Weather
import com.iazarevsergey.lessons.domain.repository.IWeatherRepository
import io.reactivex.Completable
import javax.inject.Inject

class DeleteWeatherUsecase @Inject constructor(private val weatherRepository: IWeatherRepository) {
    fun execute(item: Weather): Completable {
        return weatherRepository.deleteWeather(item)

    }
}