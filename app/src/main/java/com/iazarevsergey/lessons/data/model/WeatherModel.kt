package com.iazarevsergey.lessons.data.model

import com.google.gson.annotations.SerializedName


data class WeatherModel(

    @SerializedName("last_updated") val last_updated: String,
    @SerializedName("temp_c") val temp_c: Double,
    @SerializedName("is_day") val is_day: Int,
    @SerializedName("condition") val condition: Condition,
    @SerializedName("wind_kph") val wind_kph: Double,
    @SerializedName("feelslike_c") val feelslike_c: Double
)