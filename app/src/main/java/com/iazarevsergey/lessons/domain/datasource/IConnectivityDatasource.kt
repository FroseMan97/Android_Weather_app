package com.iazarevsergey.lessons.domain.datasource

interface IConnectivityDatasource {
    fun isNetworkOn(): Boolean
}