package com.iazarevsergey.lessons.data.model.response

import com.google.gson.annotations.SerializedName
import com.iazarevsergey.lessons.data.model.WeatherModel
import com.iazarevsergey.lessons.data.model.LocationModel
import com.iazarevsergey.lessons.domain.model.CurrentWeather


data class CurrentWeatherResponse (

    @SerializedName("location") val location : LocationModel,
    @SerializedName("current") val current : WeatherModel

): DomainMappable<CurrentWeather>{
    override fun asDomain(): CurrentWeather = CurrentWeather(
        current.last_updated,
        current.temp_c,
        current.wind_kph,
        current.feelslike_c,
        current.condition.text,
        current.condition.icon,
        location.name,
        location.region,
        location.country
    )

}
