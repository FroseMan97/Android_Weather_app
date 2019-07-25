package com.iazarevsergey.lessons.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.iazarevsergey.lessons.domain.model.CurrentWeather
import com.iazarevsergey.lessons.domain.usecase.GetSearchUsecase
import com.iazarevsergey.lessons.domain.usecase.GetWeatherUsecase
import io.reactivex.android.schedulers.AndroidSchedulers

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class WeatherViewModel @Inject constructor(
    private val getWeatherUsecase: GetWeatherUsecase,
    private val getSearchUsecase: GetSearchUsecase
) : ViewModel() {

    private var currentWeather = MutableLiveData<CurrentWeather>()
    private var isLoading = MutableLiveData<Boolean>()
    private var onError = MutableLiveData<String>()
    private var searches = MutableLiveData<List<String>>()
    private val compositeDisposable = CompositeDisposable()

    fun getWeather(): LiveData<CurrentWeather> = currentWeather
    fun getIsLoading(): LiveData<Boolean> = isLoading
    fun getOnError(): LiveData<String> = onError
    fun getSearches(): LiveData<List<String>> = searches


    fun onSearchConfirmed(weatherLocation: String) {
        if (!weatherLocation.isNullOrEmpty()) {
            compositeDisposable.add(
                getWeatherUsecase.execute(weatherLocation)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe { handleGetWeatherResult(it) }
            )
        }
    }

    fun onTextChanged(searchLocation: String) {
        if (!searchLocation.isNullOrEmpty()) {
            compositeDisposable.add(
                getSearchUsecase.execute(searchLocation)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe{ handleGetSearchesResult(it)}
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

    private fun handleGetSearchesResult(result: GetSearchUsecase.Result) {
        when (result) {
            is GetSearchUsecase.Result.Success -> {
                searches.postValue(result.searches.map{it.name})
            }
            is GetSearchUsecase.Result.Failure -> {
                onError.postValue(result.throwable.message)
            }
        }
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }
}