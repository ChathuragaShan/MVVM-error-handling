package com.chathurangashan.mvvmerrorhandling.di.modules

import com.chathurangashan.mvvmerrorhandling.network.ApiService
import com.chathurangashan.mvvmerrorhandling.network.NetworkService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Singleton
    @Provides
    fun provideRetrofit() = NetworkService.getInstance().getService(ApiService::class.java)
}