package com.iazarevsergey.lessons.domain.usecase

import com.iazarevsergey.lessons.domain.repository.IWeatherRepository
import io.reactivex.Completable
import javax.inject.Inject

class AddWeatherUsecase @Inject constructor(private val weatherRepository: IWeatherRepository) {
    fun execute(location: String): Completable {
        return weatherRepository.addWeather(location)
    }
}