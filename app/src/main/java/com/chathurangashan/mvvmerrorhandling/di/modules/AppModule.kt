package com.chathurangashan.mvvmerrorhandling.di.modules

import android.content.Context
import android.content.SharedPreferences
import android.content.Context.MODE_PRIVATE
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Singleton
    @Provides
    fun provideSharedPreferences(context: Context): SharedPreferences =
        context.getSharedPreferences("com.chathurangashan.mvvmerrorhandling", MODE_PRIVATE)
}