package com.chathurangashan.mvvmerrorhandling.di

import android.content.Context
import android.content.SharedPreferences
import com.chathurangashan.mvvmerrorhandling.network.ApiService
import com.chathurangashan.mvvmerrorhandling.network.ConnectivityInterceptor
import com.chathurangashan.mvvmerrorhandling.network.MockInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {

    @Provides
    fun provideConnectivityInterceptor(@ApplicationContext context: Context) =
        ConnectivityInterceptor(context)

    @Provides
    fun provideMockInterceptor(sharedPreferences: SharedPreferences) =
        MockInterceptor(sharedPreferences)

    @Provides
    fun provideHttpClient(connectivityInterceptor: ConnectivityInterceptor,
                          mockInterceptor: MockInterceptor): OkHttpClient.Builder {

        return OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(connectivityInterceptor)
            .addInterceptor(mockInterceptor)
    }

    @Provides
    fun baseURL() =  "https://dummyurl.com"


    @Provides
    fun provideRetrofit(baseURL: String, httpClient: OkHttpClient.Builder): ApiService {

        val retrofit =  Retrofit.Builder()
            .baseUrl(baseURL)
            .client(httpClient.build())
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

        return retrofit.create(ApiService::class.java)

    }
}