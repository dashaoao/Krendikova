package com.example.krendikova.data.api

import okhttp3.Interceptor
import okhttp3.Response

class AuthHeaderInterceptor(private val token: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain
            .request()
            .newBuilder()

        builder.addHeader("x-api-key", token)
        return chain.proceed(builder.build())
    }
}