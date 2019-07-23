package com.iazarevsergey.lessons.data.model

import com.google.gson.annotations.SerializedName


data class LocationModel(

    @SerializedName("name") val name: String,
    @SerializedName("region") val region: String,
    @SerializedName("country") val country: String,
    @SerializedName("localtime") val localtime: String
)