package com.iazarevsergey.lessons.di.module

import com.iazarevsergey.lessons.data.network.ApiService
import com.iazarevsergey.lessons.data.network.RequestInterceptor
import com.iazarevsergey.lessons.data.repository.WeatherRepository
import com.iazarevsergey.lessons.domain.repository.IWeatherRepository
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class DataModule {

    val BASE_URL = "http://api.apixu.com/v1/"

    @Provides
    @Singleton
    fun provideRequestInterceptor():RequestInterceptor{
        return RequestInterceptor()
    }

    @Provides
    @Singleton
    fun provideWeatherRepository(apiService: ApiService): IWeatherRepository {
        return WeatherRepository(apiService)
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(requestInterceptor:RequestInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(requestInterceptor)
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(1, TimeUnit.MINUTES)
            .build()
    }
}