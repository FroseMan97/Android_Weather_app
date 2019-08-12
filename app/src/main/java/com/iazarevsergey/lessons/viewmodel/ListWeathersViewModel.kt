package com.iazarevsergey.lessons.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.iazarevsergey.lessons.data.model.response.*
import com.iazarevsergey.lessons.domain.model.Weather
import com.iazarevsergey.lessons.domain.model.Search
import com.iazarevsergey.lessons.domain.usecase.*
import com.iazarevsergey.lessons.util.Result
import com.iazarevsergey.lessons.util.ResultType
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ListWeathersViewModel @Inject constructor(
    private val getSearchUsecase: GetSearchUsecase,
    private val getUserWeatherUsecase: GetUserWeatherListUsecase,
    private val addWeatherUsecase: AddWeatherUsecase,
    private val deleteWeatherUsecase: DeleteWeatherUsecase
) : ViewModel() {

    private val weathers = MutableLiveData<List<Weather>>()
    private var onError = MutableLiveData<String>()
    private var searches = MutableLiveData<List<Search>>()

    private val compositeDisposable = CompositeDisposable()

    init {
        weathers.value = ArrayList<Weather>()
        getUserWeatherList()
    }

    fun getSearches(): LiveData<List<Search>> = searches
    fun getWeathers(): LiveData<List<Weather>> = weathers
    fun getOnError(): LiveData<String> = onError

    fun addWeather(location:String) {
            compositeDisposable.add(
                addWeatherUsecase.execute(location)
                    .subscribeOn(Schedulers.io())
                    .subscribe { handleAddWeatherResult(it) }
            )
    }

    fun getUserWeatherList(){
        compositeDisposable.add(
            getUserWeatherUsecase.execute()
                .subscribeOn(Schedulers.io())
                .subscribe { handleGetUserWeatherListResult(it) }
        )
    }

    private fun handleGetUserWeatherListResult(result:Result<List<Weather>>){

        when(result.resultType){
            ResultType.SUCCESS ->{
                weathers.postValue(result.data)
            }
            ResultType.ERROR ->{
                onError.postValue(result.message)
            }
        }
    }

    private fun handleAddWeatherResult(result: Result<Weather>) {
        when (result.resultType) {
            ResultType.SUCCESS -> {
                if(!weathers.value!!.contains(result.data!!)) {
                    val arrayList = ArrayList(weathers.value!!)
                    arrayList.add(result.data)
                    weathers.postValue(arrayList)
                }else{
                    onError.postValue("Такой элемент уже существует")
                }
            }
            ResultType.ERROR -> {
                onError.postValue(result.message)
            }
        }
    }

    fun deleteWeather(item: Weather){
        compositeDisposable.add(
            deleteWeatherUsecase.execute(WeatherResponseMapper.mapTo(item))
                .subscribeOn(Schedulers.io())
                .subscribe()
        )
    }

    fun onTextChanged(searchLocation: String) {
        if (!searchLocation.isNullOrEmpty()) {
            compositeDisposable.add(
                getSearchUsecase.execute(searchLocation)
                    .subscribeOn(Schedulers.io())
                    .subscribe { handleGetSearchesResult(it) }
            )
        }
    }

    fun getWeatherCoordinates(item: Weather): String? {
        when (weathers.value!!.contains(item)) {
            true -> return "${item.location_lat},${item.location_lon}"
        }
        onError.postValue("Элемент не найден")
        return null
    }

    private fun handleGetSearchesResult(result: Result<List<Search>>) {
        when (result.resultType) {
            ResultType.SUCCESS -> {
                searches.postValue(result.data!!)
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