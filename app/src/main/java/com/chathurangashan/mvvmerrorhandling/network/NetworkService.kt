package com.chathurangashan.mvvmerrorhandling.network

import com.chathurangashan.mvvmerrorhandling.Config
import com.chathurangashan.mvvmerrorhandling.ThisApplication
import com.chathurangashan.mvvmerrorhandling.data.enums.BuildType
import java.util.concurrent.TimeUnit
import com.facebook.stetho.okhttp3.StethoInterceptor
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class NetworkService {

    private var baseURL: String = ""

    companion object {
        fun getInstance(): NetworkService {
            return NetworkService()
        }

        fun getTestInstance(testUrl: HttpUrl): NetworkService {
            return NetworkService(testUrl)
        }
    }

    constructor(){
        baseURL = when(ThisApplication.buildType){
            BuildType.RELEASE -> Config.LIVE_BASE_URL
            BuildType.DEVELOPMENT -> Config.DEV_BASE_URL
            BuildType.TESTING -> ""
        }
    }

    constructor(testUrl: HttpUrl) : this() {
        baseURL = testUrl.toString()
    }

    fun <S> getService(serviceClass: Class<S>): S {

        val httpClient = OkHttpClient.Builder()
                .addNetworkInterceptor(StethoInterceptor())
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(AccessTokenInterceptor())

        val builder = Retrofit.Builder()
                .addConverterFactory(MoshiConverterFactory.create())
                .baseUrl(baseURL)
                .client(httpClient.build())

        val retrofit = builder.build()

        return retrofit.create(serviceClass)
    }
}