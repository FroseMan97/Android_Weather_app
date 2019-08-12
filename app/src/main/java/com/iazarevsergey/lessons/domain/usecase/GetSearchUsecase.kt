package com.iazarevsergey.lessons.domain.usecase

import com.iazarevsergey.lessons.data.model.response.SearchResponse
import com.iazarevsergey.lessons.data.model.response.SearchResponseMapper
import com.iazarevsergey.lessons.domain.model.Search
import com.iazarevsergey.lessons.domain.repository.IWeatherRepository
import com.iazarevsergey.lessons.util.Result
import io.reactivex.Observable
import javax.inject.Inject

class GetSearchUsecase @Inject constructor(private val weatherRepository: IWeatherRepository) {


    fun execute(location: String): Observable<Result<List<Search>>> {
        return weatherRepository.getSearches(location)
            .toObservable()
            .map { Result.success(it.map { SearchResponseMapper.mapTo(it) }) }
            .onErrorReturn { Result.error(it.message!!) }
            .startWith(Result.loading())
    }
}