package com.iazarevsergey.lessons.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.iazarevsergey.lessons.domain.model.CurrentWeather
import com.iazarevsergey.lessons.domain.usecase.GetWeatherUsecase
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class DetailWeatherViewModel @Inject constructor(
    private val getWeatherUsecase: GetWeatherUsecase
) : ViewModel() {
    private var currentWeather = MutableLiveData<CurrentWeather>()
    private var isLoading = MutableLiveData<Boolean>()
    private var onError = MutableLiveData<String>()
    private val compositeDisposable = CompositeDisposable()

    fun getWeather(): LiveData<CurrentWeather> = currentWeather
    fun getIsLoading(): LiveData<Boolean> = isLoading
    fun getOnError(): LiveData<String> = onError



    fun getDetailWeather(weatherLocation: String) {
        if (!weatherLocation.isNullOrEmpty()) {
            compositeDisposable.add(
                getWeatherUsecase.execute(weatherLocation)
                    .subscribeOn(Schedulers.io())
                    .subscribe { handleGetWeatherResult(it) }
            )
        }
    }

    private fun handleGetWeatherResult(result: GetWeatherUsecase.Result) {
        isLoading.postValue(result == GetWeatherUsecase.Result.Loading)
        when (result) {
            is GetWeatherUsecase.Result.Success -> {

                currentWeather.postValue(result.weather)
            }
            is GetWeatherUsecase.Result.Failure -> {
                onError.postValue(result.throwable.message)
            }
        }
    }

    override fun onCleared() {

        compositeDisposable.dispose()
        super.onCleared()
    }

}