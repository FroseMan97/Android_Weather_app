package com.iazarevsergey.lessons.domain.repository.datasource

interface IConnectivityDatasource {
    fun isNetworkOn(): Boolean
}