package com.iazarevsergey.lessons.domain.usecase

import com.iazarevsergey.lessons.data.model.response.WeatherMapper
import com.iazarevsergey.lessons.data.model.response.WeatherResponse
import com.iazarevsergey.lessons.data.model.response.WeatherResponseMapper
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
            .map { Result.success(it.map { WeatherMapper.mapTo(it) }) }
            .onErrorReturn { Result.error(it.message!!) }
            .startWith(Result.loading())
    }
}
