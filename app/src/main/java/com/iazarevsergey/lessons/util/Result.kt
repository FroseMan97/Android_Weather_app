package com.iazarevsergey.lessons.util

data class Result<out T>(
    var resultType: ResultType,
    val data: T? = null,
    val message: String? = null
) {

    companion object {
        fun <T> success(data: T?): Result<T> {
            return Result(ResultType.SUCCESS, data)
        }

        fun <T> error(message: String): Result<T> {
            return Result(ResultType.ERROR,  message = message)
        }

        fun <T> loading():Result<T>{
            return Result(ResultType.LOADING)
        }
    }
}

enum class ResultType {
    ERROR,
    LOADING,
    SUCCESS,
    EMPTY_DATA
}
