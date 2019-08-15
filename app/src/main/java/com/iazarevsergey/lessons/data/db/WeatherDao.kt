package com.iazarevsergey.lessons.data.db

import androidx.room.*
import com.iazarevsergey.lessons.data.model.response.WeatherResponse
import io.reactivex.Completable
import io.reactivex.Observable

@Dao
interface WeatherDao {

    @Insert
    fun insertWeather(item: WeatherResponse): Completable

    @Insert
    fun insertAllWeathers(items: List<WeatherResponse>)

    @Query("delete from weathers")
    fun deleteAllWeathers(): Completable

    @Delete
    fun deleteWeather(item: WeatherResponse): Completable

    @Query("select * from weathers")
    fun getAllWeathers(): Observable<List<WeatherResponse>>

    @Query("select * from weathers where lon=:lon and lat=:lat")
    fun getWeatherByCoordinates(lat: Double, lon: Double): Observable<WeatherResponse>

    @Update
    fun updateWeather(item: WeatherResponse): Completable

    @Update
    fun updateAllWeather(items: List<WeatherResponse>)

}