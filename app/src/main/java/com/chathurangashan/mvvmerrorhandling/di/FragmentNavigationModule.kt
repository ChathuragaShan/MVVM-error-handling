package com.chathurangashan.mvvmerrorhandling.di

import com.chathurangashan.mvvmerrorhandling.di.navigation.FragmentNavigator
import com.chathurangashan.mvvmerrorhandling.di.navigation.FragmentNavigatorImplementation
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@InstallIn(FragmentComponent::class)
@Module
abstract class FragmentNavigationModule {

    @Binds
    abstract fun bindNavigator(impl: FragmentNavigatorImplementation): FragmentNavigator

}