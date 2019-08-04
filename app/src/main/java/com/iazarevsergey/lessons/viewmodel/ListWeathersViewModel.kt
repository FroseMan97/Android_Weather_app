package com.iazarevsergey.lessons.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.iazarevsergey.lessons.domain.model.CurrentWeather
import com.iazarevsergey.lessons.domain.usecase.GetSearchUsecase
import com.iazarevsergey.lessons.domain.usecase.GetWeatherUsecase
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ListWeathersViewModel @Inject constructor(
    private val getWeatherUsecase: GetWeatherUsecase,
    private val getSearchUsecase: GetSearchUsecase
) : ViewModel(){

    private val weathers = MutableLiveData<List<CurrentWeather>>()
    private var onError = MutableLiveData<String>()
    private var searches = MutableLiveData<List<String>>()
    private val compositeDisposable = CompositeDisposable()

    init {
        weathers.value=ArrayList()
    }

    fun getWeathers(): LiveData<List<CurrentWeather>> = weathers
    fun getOnError(): LiveData<String> = onError

    fun addNewLocation(weatherLocation:String){
        if (!weatherLocation.isNullOrEmpty()) {
            compositeDisposable.add(
                getWeatherUsecase.execute(weatherLocation)
                    .subscribeOn(Schedulers.io())
                    .subscribe { handleGetWeatherResult(it) }
            )
        }
    }


    private fun handleGetWeatherResult(result: GetWeatherUsecase.Result) {
        when (result) {
            is GetWeatherUsecase.Result.Success -> {
                val arrayList = ArrayList(weathers.value!!)
                arrayList.add(result.weather)
                weathers.postValue(arrayList)
            }
            is GetWeatherUsecase.Result.Failure -> {
                onError.postValue(result.throwable.message)
            }
        }
    }

    fun getSearches(): LiveData<List<String>> = searches

    fun onTextChanged(searchLocation: String) {
        if (!searchLocation.isNullOrEmpty()) {
            compositeDisposable.add(
                getSearchUsecase.execute(searchLocation)
                    .subscribeOn(Schedulers.io())
                    .subscribe{ handleGetSearchesResult(it)}
            )
        }
    }

    fun getWeatherByPosition(position:Int): CurrentWeather{
        return weathers.value?.get(position)!!
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