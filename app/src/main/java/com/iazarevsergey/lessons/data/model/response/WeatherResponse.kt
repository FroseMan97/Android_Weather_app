package com.iazarevsergey.lessons.data.model.response

import androidx.room.Embedded
import androidx.room.Entity
import com.google.gson.annotations.SerializedName
import com.iazarevsergey.lessons.data.model.WeatherModel
import com.iazarevsergey.lessons.data.model.LocationModel
import com.iazarevsergey.lessons.domain.model.Weather

@Entity(tableName = "weathers", primaryKeys = ["lat", "lon"])
data class WeatherResponse (

    @Embedded val location : LocationModel,
    @Embedded val current : WeatherModel

)
