package com.chathurangashan.mvvmerrorhandling.di.modules

import android.content.Context
import com.chathurangashan.mvvmerrorhandling.network.ApiService
import com.chathurangashan.mvvmerrorhandling.network.NetworkService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Singleton
    @Provides
    fun provideRetrofit(context: Context) =
        NetworkService.getInstance(context).getService(ApiService::class.java)
}