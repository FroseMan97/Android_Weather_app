package com.iazarevsergey.lessons.data.model.response

import com.iazarevsergey.lessons.domain.model.Search
import com.iazarevsergey.lessons.util.BaseMapper

object SearchResponseMapper:BaseMapper<SearchResponse,Search> {
    override fun mapTo(searchResponse: SearchResponse): Search {
        return Search(
            searchResponse.name,
            "${searchResponse.lat},${searchResponse.lon}"
        )
    }
}