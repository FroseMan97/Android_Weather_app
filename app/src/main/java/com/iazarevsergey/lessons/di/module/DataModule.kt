package com.iazarevsergey.lessons.di.module

import com.iazarevsergey.lessons.AppConstants.Companion.WEATHER_BASE_URL
import com.iazarevsergey.lessons.data.network.IWeatherEndpoint
import com.iazarevsergey.lessons.data.network.WeatherApi
import com.iazarevsergey.lessons.data.network.RequestInterceptor
import com.iazarevsergey.lessons.data.repository.WeatherRepository
import com.iazarevsergey.lessons.domain.repository.IWeatherRepository
import com.iazarevsergey.lessons.domain.usecase.GetSearchUsecase
import com.iazarevsergey.lessons.domain.usecase.GetWeatherUsecase
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class DataModule {


    @Provides
    @Singleton
    fun provdeGetWeatherUsecase(weatherRepository: WeatherRepository):GetWeatherUsecase{
        return GetWeatherUsecase(weatherRepository)
    }

    @Provides
    @Singleton
    fun provdeGetSearchUsecase(weatherRepository: WeatherRepository): GetSearchUsecase {
        return GetSearchUsecase(weatherRepository)
    }

    @Provides
    @Singleton
    fun provideWeatherRepository(weatherApi: WeatherApi): IWeatherRepository {
        return WeatherRepository(weatherApi)
    }

    @Provides
    @Singleton
    fun provideWeatherApi(weatherEndpoint: IWeatherEndpoint): WeatherApi{
        return WeatherApi(weatherEndpoint)
    }

    @Provides
    @Singleton
    fun provideWeatherEndpoint(retrofit: Retrofit): IWeatherEndpoint {
        return retrofit.create(IWeatherEndpoint::class.java)
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(WEATHER_BASE_URL)
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

    @Provides
    @Singleton
    fun provideRequestInterceptor():RequestInterceptor{
        return RequestInterceptor()
    }
}