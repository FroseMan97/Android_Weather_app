package com.iazarevsergey.lessons.domain.usecase

import com.iazarevsergey.lessons.domain.model.Search
import com.iazarevsergey.lessons.domain.repository.IWeatherRepository
import com.iazarevsergey.lessons.domain.usecase.GetSearchUsecase.Result.Success
import com.iazarevsergey.lessons.domain.usecase.GetSearchUsecase.Result.Failure
import com.iazarevsergey.lessons.domain.usecase.GetSearchUsecase.Result.Loading
import io.reactivex.Observable
import javax.inject.Inject

class GetSearchUsecase @Inject constructor(private val weatherRepository: IWeatherRepository) {
    sealed class Result {
        object Loading : Result()
        data class Success(val searches: List<Search>): Result()
        data class Failure(val throwable: Throwable) : Result()
    }

    // сюда Locale.get...?
    fun execute(location: String): Observable<Result> {
        return weatherRepository.getSearches(location)
            .toObservable()
            .map { Success(it) as Result }
            .onErrorReturn { Failure(it) }
            .startWith(Loading)
    }
}