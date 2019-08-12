package com.iazarevsergey.lessons.domain.usecase

import com.iazarevsergey.lessons.data.model.response.WeatherMapper
import com.iazarevsergey.lessons.domain.model.Weather
import com.iazarevsergey.lessons.domain.repository.IWeatherRepository
import com.iazarevsergey.lessons.util.Result
import io.reactivex.Observable
import javax.inject.Inject

class AddWeatherUsecase @Inject constructor(private val weatherRepository: IWeatherRepository) {
    fun execute(location: String): Observable<Result<Weather>> {
        return weatherRepository.addWeather(location)
            .toObservable()
            .map { Result.success(WeatherMapper.mapTo(it)) }
            .onErrorReturn { Result.error(it.message!!) }
            .startWith(Result.loading())
    }
}