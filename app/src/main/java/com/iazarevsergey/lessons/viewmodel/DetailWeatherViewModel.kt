package com.iazarevsergey.lessons.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.iazarevsergey.lessons.domain.model.Weather
import com.iazarevsergey.lessons.domain.usecase.GetWeatherUsecase
import com.iazarevsergey.lessons.util.Result
import com.iazarevsergey.lessons.util.ResultType
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class DetailWeatherViewModel @Inject constructor(
    private val getWeatherUsecase: GetWeatherUsecase
) : ViewModel() {
    private var currentWeather = MutableLiveData<Weather>()
    private var isLoading = MutableLiveData<Boolean>()
    private var onError = MutableLiveData<String>()
    private val compositeDisposable = CompositeDisposable()

    fun getWeather(): LiveData<Weather> = currentWeather
    fun getIsLoading(): LiveData<Boolean> = isLoading
    fun getOnError(): LiveData<String> = onError


    fun getDetailWeather(weatherLocation: String) {

            compositeDisposable.add(
                getWeatherUsecase.execute(weatherLocation)
                    .subscribeOn(Schedulers.io())
                    .subscribe { handleGetWeatherResult(it) }
            )
        
    }

    private fun handleGetWeatherResult(result: Result<Weather>) {
        isLoading.postValue(result.resultType == ResultType.LOADING)
        when (result.resultType) {
            ResultType.SUCCESS -> {

                currentWeather.postValue(result.data!!)
            }
            ResultType.ERROR -> {
                onError.postValue(result.message)
            }
        }
    }

    override fun onCleared() {

        compositeDisposable.dispose()
        super.onCleared()
    }

}