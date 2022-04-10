package com.chathurangashan.mvvmerrorhandling.di.modules

import android.content.Context
import android.content.SharedPreferences
import com.chathurangashan.mvvmerrorhandling.network.ApiService
import com.chathurangashan.mvvmerrorhandling.network.MockInterceptor
import com.chathurangashan.mvvmerrorhandling.network.ConnectivityInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module (includes = [AppModule::class])
class NetworkModule {

    @Singleton
    @Provides
    fun provideMockInterceptor(sharedPreferences: SharedPreferences) =
        MockInterceptor(sharedPreferences)

    @Singleton
    @Provides
    fun provideConnectivityInterceptor(context: Context) = ConnectivityInterceptor(context)

    @Singleton
    @Provides
    fun provideHttpClient(connectivityInterceptor: ConnectivityInterceptor,
                          mockInterceptor: MockInterceptor): OkHttpClient.Builder {

        return OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(connectivityInterceptor)
            .addInterceptor(mockInterceptor)
    }

    @Singleton
    @Provides
    fun baseURL() =  "https://dummyurl.com"

    @Singleton
    @Provides
    fun provideRetrofit(baseURL: String, httpClient: OkHttpClient.Builder): ApiService {

        val retrofit = Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(httpClient.build())
            .build()

        return retrofit.create(ApiService::class.java)
    }
}