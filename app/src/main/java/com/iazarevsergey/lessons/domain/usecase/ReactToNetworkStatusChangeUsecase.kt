package com.iazarevsergey.lessons.domain.usecase

import com.iazarevsergey.lessons.data.datasource.ConnectivityDatasource
import io.reactivex.Observable
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ReactToNetworkStatusChangeUsecase @Inject constructor(
    private val connectivityDatasource: ConnectivityDatasource
) {
    fun execute(): Observable<Boolean> {
        return Observable.interval(5, TimeUnit.SECONDS)
            .doOnNext { }.map { connectivityDatasource.isNetworkOn() }.distinctUntilChanged()
    }


}