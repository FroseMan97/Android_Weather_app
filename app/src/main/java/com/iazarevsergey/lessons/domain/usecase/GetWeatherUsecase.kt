package com.iazarevsergey.lessons.domain.usecase

import com.iazarevsergey.lessons.domain.model.CurrentWeather
import com.iazarevsergey.lessons.domain.repository.IWeatherRepository
import com.iazarevsergey.lessons.domain.usecase.GetWeatherUsecase.Result.Success
import com.iazarevsergey.lessons.domain.usecase.GetWeatherUsecase.Result.Failure
import com.iazarevsergey.lessons.domain.usecase.GetWeatherUsecase.Result.Loading
import io.reactivex.Observable
import javax.inject.Inject

class GetWeatherUsecase @Inject constructor(private val weatherRepository: IWeatherRepository) {
    sealed class Result {
        object Loading : Result()
        data class Success(val weather: CurrentWeather): Result()
        data class Failure(val throwable: Throwable) : Result()
    }

    // сюда Locale.get...?
    fun execute(location: String): Observable<Result> {
        return weatherRepository.getCurrentWeather(location)
            .toObservable()
            .map { Success(it) as Result }
            .onErrorReturn { Failure(it) }
            .startWith(Loading)
    }
}