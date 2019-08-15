package com.iazarevsergey.lessons.domain.usecase

import com.iazarevsergey.lessons.domain.model.Search
import com.iazarevsergey.lessons.domain.repository.IWeatherRepository
import com.iazarevsergey.lessons.util.Result
import io.reactivex.Observable
import javax.inject.Inject

class GetSearchesUsecase @Inject constructor(private val weatherRepository: IWeatherRepository) {


    fun execute(location: String): Observable<Result<List<Search>>> {
        return weatherRepository.getSearches(location)
            .toObservable()
            .map { Result.success(it) }
            .onErrorReturn { Result.error(it.message!!) }
            .startWith(Result.loading())
    }
}