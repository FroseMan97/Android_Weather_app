package com.iazarevsergey.lessons.domain.repository.datasource

import io.reactivex.Observable

interface IConnectivityDatasource {
    fun isNetworkOn():Boolean
}