package com.iazarevsergey.lessons.data.db

import androidx.room.*
import com.iazarevsergey.lessons.data.model.response.WeatherResponse
import com.iazarevsergey.lessons.domain.model.Weather
import io.reactivex.Completable
import io.reactivex.Observable

@Dao
interface WeatherDao {

    @Insert
    fun insertWeather(item: Weather): Completable

    @Insert
    fun insertAllWeathers(items: List<Weather>)

    @Query("delete from weathers")
    fun deleteAllWeathers(): Completable

    @Delete
    fun deleteWeather(item: Weather): Completable

    @Query("select * from weathers")
    fun getAllWeathers(): Observable<List<Weather>>

    @Query("select * from weathers where lon=:lon and lat=:lat")
    fun getWeatherByCoordinates(lat: Double, lon: Double): Observable<Weather>

    @Update
    fun updateWeather(item: Weather): Completable

    @Update
    fun updateAllWeather(items: List<Weather>)

}