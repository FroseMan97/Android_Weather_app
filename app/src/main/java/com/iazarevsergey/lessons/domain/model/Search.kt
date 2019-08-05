package com.iazarevsergey.lessons.domain.model

data class Search(
    val name: String,
    val coordinates: String
){
    fun toSearchFormat():String{
        return if (!coordinates.trim().isNullOrEmpty()) coordinates else name
    }
}