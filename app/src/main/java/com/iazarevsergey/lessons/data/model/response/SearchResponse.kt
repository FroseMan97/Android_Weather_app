package com.iazarevsergey.lessons.data.model.response

import com.google.gson.annotations.SerializedName
import com.iazarevsergey.lessons.domain.model.Search

data class SearchResponse (

    @SerializedName("id") val id : Int,
    @SerializedName("name") val name : String,
    @SerializedName("region") val region : String,
    @SerializedName("country") val country : String,
    @SerializedName("lat") val lat : Double,
    @SerializedName("lon") val lon : Double,
    @SerializedName("url") val url : String
):DomainMappable<Search>{
    override fun asDomain(): Search = Search(
        name,
        "$lat,$lon"
    )

}