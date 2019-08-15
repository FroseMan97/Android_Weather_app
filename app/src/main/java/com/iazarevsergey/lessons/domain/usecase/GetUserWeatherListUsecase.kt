package com.iazarevsergey.lessons.domain.usecase

import com.iazarevsergey.lessons.domain.model.Weather
import com.iazarevsergey.lessons.domain.repository.IWeatherRepository
import com.iazarevsergey.lessons.util.Result
import io.reactivex.Observable
import javax.inject.Inject

class GetUserWeatherListUsecase @Inject constructor(
    private val weatherRepository: IWeatherRepository
) {

    fun execute(): Observable<Result<List<Weather>>> {
        return weatherRepository.getUserWeatherList()
            .map { Result.success(it) }
            .onErrorReturn { Result.error(it.message!!) }
    }
}
