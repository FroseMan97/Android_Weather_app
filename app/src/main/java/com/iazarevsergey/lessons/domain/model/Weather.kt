package com.iazarevsergey.lessons.domain.model

data class Weather(
    val weather_last_updated: String,
    val weather_temp_c: Double,
    val weather_wind_kph: Double,
    val weather_feelslike_c: Double,
    val condition_text: String,
    val condition_icon: String,
    val location_name: String,
    val location_region: String,
    val location_country: String,
    val location_lat: Double,
    val location_lon: Double,
    val location_localtime:String,
    val weather_isDay:Int,
    val condition_code:Int
)