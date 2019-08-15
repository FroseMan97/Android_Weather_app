package com.iazarevsergey.lessons.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.iazarevsergey.lessons.domain.model.Weather
import com.iazarevsergey.lessons.domain.usecase.GetWeatherUsecase
import com.iazarevsergey.lessons.domain.usecase.UpdateWeatherUsecase
import com.iazarevsergey.lessons.util.Result
import com.iazarevsergey.lessons.util.ResultType
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class DetailWeatherViewModel @Inject constructor(
    private val getWeatherUsecase: GetWeatherUsecase,
    private val updateWeatherUsecase: UpdateWeatherUsecase
) : ViewModel() {
    private var currentWeather = MutableLiveData<Weather>()
    private var isLoading = MutableLiveData<Boolean>()
    private var info = MutableLiveData<String>()
    private val compositeDisposable = CompositeDisposable()

    fun getWeather(): LiveData<Weather> = currentWeather
    fun getIsLoading(): LiveData<Boolean> = isLoading
    fun getInfo(): LiveData<String> = info


    fun getDetailWeather(weatherLocation: String?) {
        if (!weatherLocation.isNullOrEmpty()) {
            val coordinates = weatherLocation.split(",")
            compositeDisposable.add(
                getWeatherUsecase.execute(coordinates[0].toDouble(), coordinates[1].toDouble())
                    .subscribeOn(Schedulers.io())
                    .subscribe { handleGetWeatherResult(it) }
            )
        } else {
            info.postValue("Такой погоды нет")
        }

    }

    fun updateWeather() {
        isLoading.postValue(true)
        compositeDisposable.add(
            updateWeatherUsecase.execute(currentWeather.value!!)
                .timeout(5, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        isLoading.postValue(false)
                        info.postValue("Погода обновлена")
                    },
                    { error ->
                        isLoading.postValue(false)
                        info.postValue(error.message)
                    }
                )
        )

    }

    private fun handleGetWeatherResult(result: Result<Weather>) {
        isLoading.postValue(result.resultType == ResultType.LOADING)
        when (result.resultType) {
            ResultType.SUCCESS -> {
                currentWeather.postValue(result.data!!)
            }
            ResultType.ERROR -> {
                info.postValue(result.message)
            }
        }
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }

}