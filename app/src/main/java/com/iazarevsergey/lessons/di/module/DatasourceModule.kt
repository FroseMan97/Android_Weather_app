package com.iazarevsergey.lessons.di.module

import com.iazarevsergey.lessons.datasource.ConnectivityDatasource
import com.iazarevsergey.lessons.datasource.LocalDatasource
import com.iazarevsergey.lessons.datasource.RemoteDatasource
import com.iazarevsergey.lessons.data.datasource.IConnectivityDatasource
import com.iazarevsergey.lessons.data.datasource.ILocalDatasource
import com.iazarevsergey.lessons.data.datasource.IRemoteDatasource
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface DatasourceModule {

    @Binds
    @Singleton
    fun bindLocalDatasource(localDatasource: LocalDatasource): ILocalDatasource

    @Binds
    @Singleton
    fun bindRemoteDatasource(remoteDatasource: RemoteDatasource): IRemoteDatasource

    @Binds
    @Singleton
    fun bindConnectivityDatasource(connectivityDatasource: ConnectivityDatasource): IConnectivityDatasource

}