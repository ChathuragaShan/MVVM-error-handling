package com.chathurangashan.mvvmerrorhandling.di

import com.chathurangashan.mvvmerrorhandling.di.navigation.ActivityNavigator
import com.chathurangashan.mvvmerrorhandling.di.navigation.ActivityNavigatorImplementation
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@InstallIn(ActivityComponent::class)
@Module
abstract class ActivityNavigationModule {

    @Binds
    abstract fun bindNavigator(impl: ActivityNavigatorImplementation): ActivityNavigator

}