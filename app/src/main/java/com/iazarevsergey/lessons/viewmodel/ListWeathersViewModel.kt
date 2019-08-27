package com.iazarevsergey.lessons.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.iazarevsergey.lessons.domain.model.Search
import com.iazarevsergey.lessons.domain.model.Weather
import com.iazarevsergey.lessons.domain.usecase.*
import com.iazarevsergey.lessons.util.Result
import com.iazarevsergey.lessons.util.ResultType
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ListWeathersViewModel @Inject constructor(
    private val getSearchUsecase: GetSearchesUsecase,
    private val getUserWeatherUsecase: GetUserWeatherListUsecase,
    private val addWeatherUsecase: AddWeatherUsecase,
    private val deleteWeatherUsecase: DeleteWeatherUsecase,
    private val updateAllWeatherUsecase: UpdateAllWeatherUsecase
) : ViewModel() {

    private val weathers = MutableLiveData<List<Weather>>()
    private var info = MutableLiveData<String>()
    private var searches = MutableLiveData<List<Search>>()
    private var isRefreshing = MutableLiveData<Boolean>()
    private val compositeDisposable = CompositeDisposable()
    private val networkStatusChanged = MutableLiveData<Boolean>()

    init {
        weathers.value = ArrayList()
        getUserWeatherList()
    }

    fun getSearches(): LiveData<List<Search>> = searches
    fun getWeathers(): LiveData<List<Weather>> = weathers
    fun getInfo(): LiveData<String> = info
    fun getIsRefreshing(): LiveData<Boolean> = isRefreshing
    fun getNetworkStatusChanged(): LiveData<Boolean> = networkStatusChanged


    fun addWeather(location: String) {
        compositeDisposable.add(
            addWeatherUsecase.execute(location)
                .timeout(5, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .subscribe({ info.postValue("Элемент добавлен") }, { e -> info.postValue(e.message) })
        )
    }

    fun deleteWeather(item: Weather) {
        compositeDisposable.add(
            deleteWeatherUsecase.execute(item)
                .subscribeOn(Schedulers.io())
                .subscribe({ info.postValue("Элемент удален") }, { e -> info.postValue(e.message) })
        )
    }

    fun updateAllWeather() {
        if (!weathers.value.isNullOrEmpty()) {
            isRefreshing.postValue(true)
            compositeDisposable.add(
                updateAllWeatherUsecase.execute(weathers.value!!)
                    .timeout(5, TimeUnit.SECONDS)
                    .subscribeOn(Schedulers.io())
                    .subscribe(
                        {
                            info.postValue("Вся погода обновлена")
                            isRefreshing.postValue(false)
                        },
                        { error ->
                            info.postValue(error.toString())
                            isRefreshing.postValue(false)
                        }
                    )
            )
        }
    }


    private fun getUserWeatherList() {
        compositeDisposable.add(
            getUserWeatherUsecase.execute()
                .subscribeOn(Schedulers.io())
                .subscribe { handleGetUserWeatherListResult(it) }
        )
    }

    private fun handleGetUserWeatherListResult(result: Result<List<Weather>>) {

        when (result.resultType) {
            ResultType.SUCCESS -> {
                weathers.postValue(result.data)
            }
            ResultType.ERROR -> {
                info.postValue(result.message)
            }
        }
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

    fun isContainsWeatherCoordinates(item: Weather): String? {
        if (weathers.value!!.contains(item)) {
            return "${item.location_lat},${item.location_lon}"
        }
        info.postValue("Элемент не найден")
        return null
    }

    private fun handleGetSearchesResult(result: Result<List<Search>>) {
        when (result.resultType) {
            ResultType.SUCCESS -> {
                searches.postValue(result.data!!)
            }
            ResultType.ERROR -> {
                info.postValue(result.message)
                info.postValue(null)
            }
        }
    }


    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }
}