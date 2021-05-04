package com.chathurangashan.mvvmerrorhandling.network

import com.chathurangashan.mvvmerrorhandling.Config
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class AccessTokenInterceptor: Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val request: Request = chain.request()

        val authenticatedRequest = request.newBuilder()
            .addHeader("Content-Type", "application/json")
            .addHeader("Accept", "application/json")
            .addHeader("app-id", Config.API_ID)
            .build()

        return chain.proceed(authenticatedRequest)
    }
}