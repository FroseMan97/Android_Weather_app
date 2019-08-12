package com.iazarevsergey.lessons.data.db

import androidx.room.*
import com.iazarevsergey.lessons.data.model.response.WeatherResponse
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

@Dao
interface WeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWeather(item:WeatherResponse)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllWeathers(items:List<WeatherResponse>)

    @Query("delete from weathers")
    fun deleteAllWeathers()

    @Delete
    fun deleteWeather(item: WeatherResponse)

    @Query("select * from weathers")
    fun getAllWeathers(): Observable<List<WeatherResponse>>

    @Query("select * from weathers where lon=:lon and lat=:lat")
    fun getWeatherByCoordinates(lat:Double,lon:Double):Single<WeatherResponse>
}