package com.iazarevsergey.lessons.data.network

import okhttp3.Interceptor
import okhttp3.Response

val API_KEY = "89e8bd89085b41b7a4b142029180210"

class RequestInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val url = chain.request()
            .url()
            .newBuilder()
            .addQueryParameter("key", API_KEY)
            .build()
        val request = chain.request()
            .newBuilder()
            .url(url)
            .build()
        return chain.proceed(request)
    }
}