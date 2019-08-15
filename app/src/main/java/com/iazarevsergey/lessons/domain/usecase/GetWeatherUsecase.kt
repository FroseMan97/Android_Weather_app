package com.iazarevsergey.lessons.domain.usecase

import com.iazarevsergey.lessons.domain.model.Weather
import com.iazarevsergey.lessons.domain.repository.IWeatherRepository
import com.iazarevsergey.lessons.util.Result
import io.reactivex.Observable
import javax.inject.Inject

class GetWeatherUsecase @Inject constructor(private val weatherRepository: IWeatherRepository) {

    fun execute(lat: Double, lon: Double): Observable<Result<Weather>> {
        return weatherRepository.getWeather(lat, lon)
            .map {
                Result.success(it)
            }
            .onErrorReturn {
                Result.error(it.message!!)
            }
            .startWith(Result.loading())
    }
}