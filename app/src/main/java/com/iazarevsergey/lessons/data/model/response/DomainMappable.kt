package com.iazarevsergey.lessons.data.model.response

interface DomainMappable<R> {
    fun asDomain():R
}