package com.iazarevsergey.lessons.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.iazarevsergey.lessons.data.repository.WeatherRepository
import com.iazarevsergey.lessons.domain.model.CurrentWeather
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class WeatherViewModel @Inject constructor(private val weatherRepository: WeatherRepository) : ViewModel() {

    private var currentWeather = MutableLiveData<CurrentWeather>()
    private var isLoading = MutableLiveData<Boolean>()
    private var onError = MutableLiveData<String>()
    private var searches = MutableLiveData<List<String>>()
    private val compositeDisposable = CompositeDisposable()

    fun getWeather(): LiveData<CurrentWeather> = currentWeather
    fun getIsLoading(): LiveData<Boolean> = isLoading
    fun getOnError(): LiveData<String> = onError
    fun getSearches(): LiveData<List<String>> = searches


    fun onSearchConfirmed(location: String) {
        if (!location.isNullOrEmpty()) {
            compositeDisposable.add(
                weatherRepository.getCurrentWeather(location)
                    .doOnSubscribe { isLoading.postValue(true) }
                    .subscribeOn(Schedulers.io())
                    .subscribe(
                        { currentWeather.postValue(it);isLoading.postValue(false) },
                        { onError.postValue(it.message);isLoading.postValue(false) })
            )
        }
    }

    fun onTextChanged(location: String) {
        if (!location.isNullOrEmpty()) {
            compositeDisposable.add(
                weatherRepository.getSearches(location)
                    .subscribeOn(Schedulers.io())
                    .subscribe({ searches.postValue(it.map { it.name }) }, { onError.postValue(it.message) })
            )
        }
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }
}