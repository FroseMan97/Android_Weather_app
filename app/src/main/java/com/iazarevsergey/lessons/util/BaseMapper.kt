package com.iazarevsergey.lessons.util

interface BaseMapper<in A, out B> {
    fun mapTo(type: A): B
}