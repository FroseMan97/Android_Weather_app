package com.iazarevsergey.lessons.data.model.response

import com.iazarevsergey.lessons.data.model.Condition
import com.iazarevsergey.lessons.data.model.LocationModel
import com.iazarevsergey.lessons.data.model.WeatherModel
import com.iazarevsergey.lessons.domain.model.Weather
import com.iazarevsergey.lessons.util.BaseMapper

object WeatherMapper:BaseMapper<WeatherResponse, Weather> {

    override fun mapTo(weatherResponse: WeatherResponse): Weather {
        return Weather(
            weatherResponse.current.last_updated,
            weatherResponse.current.temp_c,
            weatherResponse.current.wind_kph,
            weatherResponse.current.feelslike_c,
            weatherResponse.current.condition.text,
            weatherResponse.current.condition.icon,
            weatherResponse.location.name,
            weatherResponse.location.region,
            weatherResponse.location.country,
            weatherResponse.location.lat,
            weatherResponse.location.lon,
            weatherResponse.location.localtime,
            weatherResponse.current.is_day,
            weatherResponse.current.condition.code
        )
    }
}

object WeatherResponseMapper:BaseMapper<Weather,WeatherResponse>{
    override fun mapTo(weather: Weather): WeatherResponse {
        val location = LocationModel(
            weather.location_name,
            weather.location_region,
            weather.location_country,
            weather.location_localtime,
            weather.location_lon,
            weather.location_lat
        )
        val condition = Condition(
            weather.condition_text,
            weather.condition_icon,
            weather.condition_code
        )
        val weather = WeatherModel(
            weather.weather_last_updated,
            weather.weather_temp_c,
            weather.weather_isDay,
            condition,
            weather.weather_wind_kph,
            weather.weather_feelslike_c
        )
        return WeatherResponse(
            location,weather
        )
    }
}
