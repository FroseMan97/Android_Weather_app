package com.iazarevsergey.lessons.di.module

import com.iazarevsergey.lessons.AppConstants.Companion.WEATHER_BASE_URL
import com.iazarevsergey.lessons.data.network.IWeatherEndpoint
import com.iazarevsergey.lessons.data.network.WeatherApi
import com.iazarevsergey.lessons.data.network.RequestInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkModule {

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
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRequestInterceptor():RequestInterceptor{
        return RequestInterceptor()
    }
}