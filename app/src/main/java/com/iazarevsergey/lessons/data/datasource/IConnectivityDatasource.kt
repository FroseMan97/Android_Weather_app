package com.iazarevsergey.lessons.data.datasource

interface IConnectivityDatasource {
    fun isNetworkOn(): Boolean
}