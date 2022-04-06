package com.chathurangashan.mvvmerrorhandling.network

import android.content.Context
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
    private lateinit var connectivityInterceptor: ConnectivityInterceptor

    companion object {
        fun getInstance(context: Context): NetworkService {
            return NetworkService(context)
        }

        fun getTestInstance(context: Context,testUrl: HttpUrl): NetworkService {
            return NetworkService(context, testUrl)
        }
    }

    constructor(context: Context){

        connectivityInterceptor = ConnectivityInterceptor(context)
        baseURL = when(ThisApplication.buildType){
            BuildType.RELEASE -> Config.LIVE_BASE_URL
            BuildType.DEVELOPMENT -> Config.DEV_BASE_URL
            BuildType.TESTING -> ""
        }

    }

    constructor(context: Context, testUrl: HttpUrl) : this(context) {

        connectivityInterceptor = ConnectivityInterceptor(context)
        baseURL = testUrl.toString()

    }

    fun <S> getService(serviceClass: Class<S>): S {

        val httpClient = OkHttpClient.Builder()
                .addNetworkInterceptor(StethoInterceptor())
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(connectivityInterceptor)
                .addInterceptor(MockInterceptor())

        val builder = Retrofit.Builder()
                .addConverterFactory(MoshiConverterFactory.create())
                .baseUrl(baseURL)
                .client(httpClient.build())

        val retrofit = builder.build()

        return retrofit.create(serviceClass)
    }
}